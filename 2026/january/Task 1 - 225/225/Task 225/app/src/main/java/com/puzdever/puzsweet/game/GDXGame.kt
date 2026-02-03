package com.puzdever.puzsweet.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.puzdever.puzsweet.MainActivity
import com.puzdever.puzsweet.appContext
import com.puzdever.puzsweet.game.manager.MusicManager
import com.puzdever.puzsweet.game.manager.NavigationManager
import com.puzdever.puzsweet.game.manager.SoundManager
import com.puzdever.puzsweet.game.manager.SpriteManager
import com.puzdever.puzsweet.game.manager.util.MusicUtil
import com.puzdever.puzsweet.game.manager.util.SoundUtil
import com.puzdever.puzsweet.game.manager.util.SpriteUtil
import com.puzdever.puzsweet.game.screens.LoaderScreen
import com.puzdever.puzsweet.game.utils.GameColor
import com.puzdever.puzsweet.game.utils.advanced.AdvancedGame
import com.puzdever.puzsweet.game.utils.disposeAll
import com.puzdever.puzsweet.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("DDD", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        GDX_GLOBAL_isGame = true
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

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

    override fun pause() {
        log("pause")
        super.pause()
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        log("resume")
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.play()
    }

}