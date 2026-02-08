package com.quantumplay.orbitcrasher.game

import android.content.Context
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.quantumplay.orbitcrasher.MainActivity
import com.quantumplay.orbitcrasher.appContext
import com.quantumplay.orbitcrasher.game.dataStore.DS_Achievement
import com.quantumplay.orbitcrasher.game.dataStore.DS_Gems
import com.quantumplay.orbitcrasher.game.dataStore.DS_Gold
import com.quantumplay.orbitcrasher.game.dataStore.DS_Level
import com.quantumplay.orbitcrasher.game.dataStore.DS_LevelJackpot
import com.quantumplay.orbitcrasher.game.dataStore.DS_Puzzle
import com.quantumplay.orbitcrasher.game.dataStore.DS_User
import com.quantumplay.orbitcrasher.game.manager.*
import com.quantumplay.orbitcrasher.game.manager.util.MusicUtil
import com.quantumplay.orbitcrasher.game.manager.util.ParticleEffectUtil
import com.quantumplay.orbitcrasher.game.manager.util.SoundUtil
import com.quantumplay.orbitcrasher.game.manager.util.SpriteUtil
import com.quantumplay.orbitcrasher.game.screens.LoaderScreen
import com.quantumplay.orbitcrasher.game.utils.*
import com.quantumplay.orbitcrasher.game.utils.advanced.AdvancedGame
import com.quantumplay.orbitcrasher.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.time.Duration.Companion.milliseconds

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