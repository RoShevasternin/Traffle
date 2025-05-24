package com.gorillaz.puzzlegame.game

import android.content.Context
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.gorillaz.puzzlegame.MainActivity
import com.gorillaz.puzzlegame.appContext
import com.gorillaz.puzzlegame.game.dataStore.DS_Achievement
import com.gorillaz.puzzlegame.game.dataStore.DS_Gems
import com.gorillaz.puzzlegame.game.dataStore.DS_Gold
import com.gorillaz.puzzlegame.game.dataStore.DS_Level
import com.gorillaz.puzzlegame.game.dataStore.DS_LevelJackpot
import com.gorillaz.puzzlegame.game.dataStore.DS_Puzzle
import com.gorillaz.puzzlegame.game.dataStore.DS_User
import com.gorillaz.puzzlegame.game.manager.*
import com.gorillaz.puzzlegame.game.manager.util.MusicUtil
import com.gorillaz.puzzlegame.game.manager.util.ParticleEffectUtil
import com.gorillaz.puzzlegame.game.manager.util.SoundUtil
import com.gorillaz.puzzlegame.game.manager.util.SpriteUtil
import com.gorillaz.puzzlegame.game.screens.LoaderScreen
import com.gorillaz.puzzlegame.game.utils.*
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGame
import com.gorillaz.puzzlegame.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

var GLOBAL_isPauseGame = false

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set
    lateinit var particleEffectManager: ParticleEffectManager private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    val particleEffectUtil by lazy { ParticleEffectUtil() }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val prefs = appContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    lateinit var currentBackground: Texture

    val ds_Gems         = DS_Gems(coroutine)
    val ds_Gold         = DS_Gold(coroutine)
    val ds_Level        = DS_Level(coroutine)
    val ds_User         = DS_User(coroutine)
    val ds_Puzzle       = DS_Puzzle(coroutine)
    val ds_LevelJeckpot = DS_LevelJackpot(coroutine)
    val ds_Achievement  = DS_Achievement(coroutine)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        calculateGoldPerHour()
    }

    override fun render() {
        if (GLOBAL_isPauseGame) return

        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }


    // Background Work ------------------------------------------------------------------------

    private fun calculateGoldPerHour() {
        val goldPerHour    = prefs.getInt(KEY_GOLD_PER_HOUR, 0)
        val lastUpdateTime = prefs.getLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis())

        // Обчислюємо час
        val elapsedTime = (System.currentTimeMillis() - lastUpdateTime).milliseconds.inWholeHours // У годинах

        if (elapsedTime > 0) {
            prefs.edit().putLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis()).apply()

            val generatedGold = (elapsedTime * goldPerHour).toInt()
            ds_Gold.update { it + generatedGold }
            log("calculateGoldPerHour: generateGoldPerHour = $generatedGold | elapsedTime = $elapsedTime | GoldPerHour = $goldPerHour")
        } else {
            val elapsedTimeMillis = (System.currentTimeMillis() - lastUpdateTime).milliseconds
            log("calculateGoldPerHour: elapsedTime = $elapsedTimeMillis | Ще не пройшло 1 години. | GoldPerHour = $goldPerHour")
        }
    }

    fun generateGoldPerHour(goldPerHour: Int) {
        log("start generateGoldPerHour = $goldPerHour")
        prefs.edit().apply {
            putInt(KEY_GOLD_PER_HOUR, goldPerHour)
            putLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis()) // Зберігаємо поточний час
            apply()
        }
    }

}