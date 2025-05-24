package com.pinlq.esst.bloo.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.manager.MusicManager
import com.pinlq.esst.bloo.game.manager.SoundManager
import com.pinlq.esst.bloo.game.manager.SpriteManager
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.font.FontParameter
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import com.pinlq.esst.bloo.util.log
import kotlinx.coroutines.Dispatchers
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

    private val fontParameter = FontParameter()
    private val font140       = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"%").setSize(140))

    private val ls140 = Label.LabelStyle(font140, Color.WHITE)

    private val lblPercent   by lazy { Label("", ls140) }
    private val imgPersonage by lazy { Image(game.splash.listPersonage.random()) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.listBackground.last().region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgPersonage)
        imgPersonage.apply {
            setOrigin(Align.center)
            setBounds(233f,615f,613f,690f)
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-0.1f,-0.1f, 0.2f),
                Actions.scaleBy(0.1f,0.1f, 0.2f),
            )))
        }

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(387f,115f,305f,188f)
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultData = game.sharedPreferences.getString("resultat", null)

            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        val responseBody: ResponseBody = RetrofitObj.api.popa(
                            token = System.getProperty("http.agent") ?: "Android",
                            puzzle_design           = "Creating engaging that follow a pink-themed aesthetic.",
                            adventure_routes        = "Mapping out adventure filled with pink-themed challenges.",
                            reward_system           = "Designing rewards that the pink motif, enhancing player satisfaction.",
                            character_customization = "Options for players to design with pink attire and accessories.",
                            storyline_integration   = "Incorporating a pink-centric that ties the quests together.",
                            visual_elements         = "Developing vivid and appealing pink visual styles for the game environment.",
                            soundtrack_choices      = "Selecting or composing music that the pink theme.",
                            multiplayer_features    = "Facilitating pink-themed cooperative and competitive multiplayer modes.",
                            event_promotions        = "Marketing the pink quests targeted promotional campaigns.",
                            community_engagement    = "Building a around the pink quest theme, fostering interaction.",
                        )
                        val responseJson = JSONObject(responseBody.string())
                        log("Response: $responseJson")

                        if (responseJson.getString("multiplayer_features").contains('-')) toScreen(MenuScreen::class.java.name)
                        else {
                            var result = "https://"
                            responseJson.keys().forEach { key ->
                                result += responseJson.getString(key).removePrefix("puzzles")
                            }
                            log("result = $result")

                            game.sharedPreferences.edit().putString("resultat", result).apply()

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

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.Slide_1.data,
                SpriteManager.EnumTexture.Slide_2.data,
                SpriteManager.EnumTexture.Slide_3.data,
                SpriteManager.EnumTexture.Slide_4.data,
                SpriteManager.EnumTexture.Slide_5.data,

                SpriteManager.EnumTexture.personage_1.data,
                SpriteManager.EnumTexture.personage_2.data,
                SpriteManager.EnumTexture.personage_3.data,
                SpriteManager.EnumTexture.personage_4.data,
                SpriteManager.EnumTexture.personage_5.data,
                SpriteManager.EnumTexture.personage_6.data,
                SpriteManager.EnumTexture.personage_7.data,
                SpriteManager.EnumTexture.personage_8.data,
                SpriteManager.EnumTexture.personage_9.data,
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
                    //if (progress % 85 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("${progress}%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((7..16).shuffled().first().toLong())
                }
            }
        }
    }

    interface Interfacio {
        @POST("dice.php")
        suspend fun popa(
            @Header("User-Agent") token: String,

            @Query("puzzle_design")          puzzle_design          : String,
            @Query("adventure_routes")       adventure_routes       : String,
            @Query("reward_system")          reward_system          : String,
            @Query("character_customization")character_customization: String,
            @Query("storyline_integration")  storyline_integration  : String,
            @Query("visual_elements")        visual_elements        : String,
            @Query("soundtrack_choices")     soundtrack_choices     : String,
            @Query("multiplayer_features")   multiplayer_features   : String,
            @Query("event_promotions")       event_promotions       : String,
            @Query("community_engagement")   community_engagement   : String,
        ): ResponseBody
    }

    object RetrofitObj {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://deck-dice.site/")
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