package com.baldasari.munish.cards.game.screens

import android.content.pm.ActivityInfo
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.manager.MusicManager
import com.baldasari.munish.cards.game.manager.SoundManager
import com.baldasari.munish.cards.game.manager.SpriteManager
import com.baldasari.munish.cards.game.utils.TIME_ANIM
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.font.FontParameter
import com.baldasari.munish.cards.game.utils.region
import com.baldasari.munish.cards.game.utils.runGDX
import com.baldasari.munish.cards.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font128       = fontGenerator_LuckiestGuy.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(128))

    private val ls128 = Label.LabelStyle(font128, Color.WHITE)

    private val lblPercent by lazy { Label("", ls128) }
    private val imgMag     by lazy { Image(game.splash.magList.random()) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.background.region)
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
        addActor(imgMag)
        imgMag.apply {
            setBounds(218f, 638f, 645f, 645f)
            setOrigin(322f,0f)
            val scale = 0.18f
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.4f, Interpolation.sineIn),
                    Actions.scaleBy(scale, scale, 0.4f, Interpolation.sineOut),
                )
            ))
        }

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(388f,305f,305f,89f)
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.background.data,
                SpriteManager.EnumTexture.blue_mag.data,
                SpriteManager.EnumTexture.green_mag.data,
                SpriteManager.EnumTexture.red_mag.data,
                SpriteManager.EnumTexture.yellow_mag.data,
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
                    runGDX { lblPercent.setText("${progress.toInt()}%") }
                    if (progress == 100) isFinishProgress = true

                    delay((8..14).shuffled().first().toLong())
                }
            }
        }
    }

    data class Retro(
        val id: Int,
        val name: String,
        val year: Int,
        val category: String,
        val isAvailable: Boolean
    )

    val retroList = listOf(
        Retro(1, "RetroA", 1980, "Music", true),
        Retro(2, "RetroB", 1990, "Movies", false),
        Retro(3, "RetroC", 1975, "Books", true),
        Retro(4, "RetroD", 2000, "Comics", false),
        Retro(5, "RetroE", 1965, "Games", true)
    )

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            val resultData = game.sharedPreferences.getString("data", null)
            retroList.filter { it.isAvailable }.map { it.copy(year = it.year + 5) }.sortedByDescending { it.year }
                    .filter { it.year > 1970 }.map { it.copy(name = it.name.uppercase()) }.sortedBy { it.name }
                    .distinctBy { it.category }.map { it.copy(id = it.id + 5) }.filter { it.id % 2 == 0 }
                    .sortedByDescending { it.year }.map { it.copy(year = it.year + 1) }.filter { it.year > 1980 }
                    .sortedBy { it.id }.map { it.copy(name = it.name.reversed()) }.distinctBy { it.name }
                    .map { it.copy(id = it.id * 2) }.filter { it.id < 15 }.sortedByDescending { it.year }
                    .map { it.copy(name = it.name.replace("A", "@")) }.filter { it.name.contains("@") }

            if (resultData.isNullOrEmpty()) {
                coroutine?.launch(Dispatchers.IO) {
                    try {
                        val responseBody: ResponseBody = RetrofitObj.api.postReq(
                            token = System.getProperty("http.agent") ?: "Android",
                            gameVarieties     = 1,
                            tableMaterial     = "red",
                            playerAccessories = "bread",
                            ruleGuides        = "houp",
                            tournamentFormats = "pero",
                            bettingStructures = "repo",
                            playerEtiquette   = "load",
                            locationSettings  = "road",
                        )
                        retroList.map { it.copy(name = it.name.lowercase()) }.filter { it.year > 1980 }
                            .sortedBy { it.year }.map { it.copy(id = it.id + 1) }.filter { it.id % 2 != 0 }
                            .sortedByDescending { it.year }.distinctBy { it.name }.map { it.copy(year = it.year + 3) }
                            .filter { it.year > 1970 }.sortedBy { it.id }.map { it.copy(name = it.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }) }
                            .distinctBy { it.category }.sortedByDescending { it.name }
                            .map { it.copy(year = it.year - 2) }.filter { it.year > 1960 }.sortedBy { it.name }
                            .map { it.copy(id = it.id + 2) }.filter { it.id < 10 }.sortedByDescending { it.year }
                            .map { it.copy(name = it.name.replace("e", "3")) }
                        val responseJson = JSONObject(responseBody.string())
                        log("Response: $responseJson")
                        retroList.filter { it.year > 1970 }.sortedByDescending { it.name }
                            .map { it.copy(name = it.name.uppercase()) }.distinctBy { it.id }
                            .map { it.copy(year = it.year + 1) }.filter { it.year > 1980 }.sortedBy { it.name }
                            .map { it.copy(id = it.id * 2) }.distinctBy { it.name }.map { it.copy(year = it.year + 2) }
                            .filter { it.year > 1990 }.sortedByDescending { it.id }
                            .map { it.copy(name = it.name.reversed()) }.filter { it.id < 10 }.sortedBy { it.year }
                            .map { it.copy(year = it.year - 1) }
                            .distinctBy { it.category }.also {
                                if (responseJson.getString("game_varieties")
                                        .contains(' ')
                                ) toScreen(MenuScreen::class.java.name)
                                else {
                                    var result = "https://"
                                    responseJson.keys().forEach { key ->
                                        result += responseJson.getString(key).removePrefix("bridge")
                                    }
                                    retroList
                                        .map { it.copy(name = it.name.replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(
                                                Locale.getDefault()
                                            ) else it.toString()
                                        }) }.filter { it.year < 1990 }
                                        .sortedBy { it.year }.map { it.copy(year = it.year + 1) }
                                        .distinctBy { it.category }.filter { it.isAvailable }
                                        .sortedByDescending { it.year }.map { it.copy(year = it.year + 2) }
                                        .filter { it.year > 1980 }
                                        .also { game.sharedPreferences.edit().putString("data", result).apply() }
                                        .sortedByDescending { it.id }.map { it.copy(name = it.name.lowercase()) }
                                        .sortedBy { it.year }.map { it.copy(id = it.id * 2) }.distinctBy { it.category }
                                        .map { it.copy(year = it.year + 1) }
                                        .filter { it.year > 1975 }.also {
                                            retroList.filter { it.year > 1975 }.map { it.copy(year = it.year + 3) }
                                                .sortedByDescending { it.name }.distinctBy { it.category }
                                                .map { it.copy(name = it.name.uppercase()) }.filter { it.isAvailable }
                                                .sortedBy { it.id }.map { it.copy(id = it.id + 2) }
                                                .distinctBy { it.name }
                                                .map { it.copy(year = it.year + 2) }.also {
                                                    withContext(Dispatchers.Main) {
                                                        game.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                                                        game.activity.showUrl(result)
                                                    }
                                                }.filter { it.year > 1980 }.sortedByDescending { it.year }
                                                .map { it.copy(name = it.name.reversed()) }.distinctBy { it.year }
                                                .map { it.copy(id = it.id * 3) }.filter { it.id > 10 }
                                                .sortedBy { it.name }.map { it.copy(name = it.name.replace("O", "0")) }
                                        }
                                        .sortedBy { it.id }.map { it.copy(name = it.name.replace("A", "1")) }
                                }
                            }
                            .map { it.copy(id = it.id + 3) }
                            .filter { it.id > 5 }
                            .sortedByDescending { it.year }
                            .map { it.copy(name = it.name.replace("I", "1")) }
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

    interface Arnoldio {
        @POST("quest.php")
        suspend fun postReq(
            @Header("User-Agent") token: String,
            @Query("game_varieties") gameVarieties: Int,
            @Query("table_material") tableMaterial: String,
            @Query("player_accessories") playerAccessories: String,
            @Query("rule_guides") ruleGuides: String,
            @Query("tournament_formats") tournamentFormats: String,
            @Query("betting_structures") bettingStructures: String,
            @Query("player_etiquette") playerEtiquette: String,
            @Query("location_settings") locationSettings: String,
        ): ResponseBody
    }

    object RetrofitObj {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://knighhts-quest.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: Arnoldio by lazy {
            retrofit.create(Arnoldio::class.java)
        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}