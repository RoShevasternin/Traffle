/*
 * Refactored Application Module
 * Build: 127C0AD6
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game

import android.content.Context
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.moonarcade.starlabyrinth.MainActivity
import com.moonarcade.starlabyrinth.appContext
import com.moonarcade.starlabyrinth.game.dataStore.AchievementStore
import com.moonarcade.starlabyrinth.game.dataStore.GemsDataStore
import com.moonarcade.starlabyrinth.game.dataStore.GoldDataStore
import com.moonarcade.starlabyrinth.game.dataStore.LevelDataStore
import com.moonarcade.starlabyrinth.game.dataStore.JackpotLevelStore
import com.moonarcade.starlabyrinth.game.dataStore.PuzzleDataStore
import com.moonarcade.starlabyrinth.game.dataStore.UserDataStore
import com.moonarcade.starlabyrinth.game.manager.*
import com.moonarcade.starlabyrinth.game.manager.util.MusicResourceHelper
import com.moonarcade.starlabyrinth.game.manager.util.ParticleResourceHelper
import com.moonarcade.starlabyrinth.game.manager.util.SoundResourceHelper
import com.moonarcade.starlabyrinth.game.manager.util.SpriteResourceHelper
import com.moonarcade.starlabyrinth.game.screens.LoadingScreen
import com.moonarcade.starlabyrinth.game.utils.*
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGameCore
import com.moonarcade.starlabyrinth.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.time.Duration.Companion.milliseconds

class CoreGameEngine(val activity: MainActivity) : BaseGameCore() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: ScreenFlowController private set
    lateinit var graphicManager    : SpriteResourceManager     private set
    lateinit var musicManager     : BackgroundMusicHandler      private set
    lateinit var soundManager     : SoundEffectController      private set
    lateinit var particleEffectManager: ParticleSystemManager private set

    val assetsLoader by lazy { SpriteResourceHelper.Loader() }
    val assetsAll    by lazy { SpriteResourceHelper.All() }

    val musicUtil by lazy { MusicResourceHelper()    }
    val soundUtil by lazy { SoundResourceHelper()    }

    val particleEffectUtil by lazy { ParticleResourceHelper() }

    var backgroundColor = ColorScheme.background
    val disposableSet = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val prefs = appContext.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    lateinit var presentBackground: Texture

    val ds_Gems = GemsDataStore(coroutine)
    val ds_Gold = GoldDataStore(coroutine)
    val ds_Level = LevelDataStore(coroutine)
    val ds_User = UserDataStore(coroutine)
    val ds_Puzzle = PuzzleDataStore(coroutine)
    val ds_LevelJeckpot = JackpotLevelStore(coroutine)
    val ds_Achievement = AchievementStore(coroutine)

    override fun create() {
        navigationManager = ScreenFlowController()
        assetManager = AssetManager()
        graphicManager = SpriteResourceManager(assetManager)

        musicManager = BackgroundMusicHandler(assetManager)
        soundManager = SoundEffectController(assetManager)

        particleEffectManager = ParticleSystemManager(assetManager)

        navigationManager.navigate(LoadingScreen::class.java.name)

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
        val goldPerHour = prefs.getInt(KEY_GOLD_PER_HOUR, 0)
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