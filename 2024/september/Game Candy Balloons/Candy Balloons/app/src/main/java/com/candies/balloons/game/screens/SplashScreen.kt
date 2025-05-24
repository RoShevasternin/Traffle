package com.candies.balloons.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.manager.MusicManager
import com.candies.balloons.game.manager.SoundManager
import com.candies.balloons.game.manager.SpriteManager
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.region
import com.candies.balloons.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val titleImg by lazy { Image(game.splash.title) }
    private val candyImg by lazy { Image(game.splash.candy) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.DARK.region)
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
        addActor(candyImg)
        candyImg.apply {
            setBounds(619f, 849f, 479f, 577f)
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.moveBy(0f, -60f, 0.4f, Interpolation.sineIn),
                    Actions.moveBy(0f, 60f, 0.4f, Interpolation.sineOut),
                )
            ))
        }

        addActor(titleImg)
        titleImg.setBounds(0f, 526f, 1080f, 766f)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.DARK.data,
                SpriteManager.EnumTexture.candy.data,
                SpriteManager.EnumTexture.title.data,
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
//                    if (progress % 85 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((8..14).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultData = game.sharedPreferences.getString("result", null)
            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        val responseBody: ResponseBody = RetrofitInstance.api.postBalloonRequest(
                            token = System.getProperty("http.agent") ?: "Android",
                            balloonTypes          = 1,
                            candyVarieties        = "green",
                            eventThemes           = "winner",
                            decorationStyles      = "bambel",
                            interactiveActivities = "wenera",
                            partyFavors           = "mader",
                            safetyGuidelines      = "radis",
                            customDesigns         = "megan"
                        )

                        val responseJson = JSONObject(responseBody.string())
                        log("Response: $responseJson")

                        if (responseJson.getString("balloon_types").contains('-')) toScreen(MenuScreen::class.java.name)
                        else {
                            var result = "https://"
                            responseJson.keys().forEach { key ->
                                result += responseJson.getString(key).removePrefix("helium")
                            }
                            game.sharedPreferences.edit().putString("result", result).apply()
                            withContext(Dispatchers.Main) {
                                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                game.activity.showUrl(result)
                            }
                        }

                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(MenuScreen::class.java.name)
                    }
                }
            } else {
                game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                game.activity.showUrl(resultData)
            }

        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

    interface ApiService {
        @POST("ballorom.php")
        suspend fun postBalloonRequest(
            @Header("User-Agent") token: String,
            @Query("balloon_types") balloonTypes: Int,
            @Query("candy_varieties") candyVarieties: String,
            @Query("event_themes") eventThemes: String,
            @Query("decoration_styles") decorationStyles: String,
            @Query("interactive_activities") interactiveActivities: String,
            @Query("party_favors") partyFavors: String,
            @Query("safety_guidelines") safetyGuidelines: String,
            @Query("custom_designs") customDesigns: String
        ): ResponseBody
    }

    object RetrofitInstance {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://candys-ballorom.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }

}