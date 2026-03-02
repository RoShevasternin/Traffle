package com.babun.flutterdash.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.babun.flutterdash.MainActivity
import com.babun.flutterdash.game.manager.MusicManager
import com.babun.flutterdash.game.manager.NavigationManager
import com.babun.flutterdash.game.manager.SoundManager
import com.babun.flutterdash.game.manager.SpriteManager
import com.babun.flutterdash.game.manager.util.MusicUtil
import com.babun.flutterdash.game.manager.util.SoundUtil
import com.babun.flutterdash.game.manager.util.SpriteUtil
import com.babun.flutterdash.game.screens.LoaderScreen
import com.babun.flutterdash.game.utils.GameColor
import com.babun.flutterdash.game.utils.advanced.AdvancedGame
import com.babun.flutterdash.game.utils.disposeAll
import com.babun.flutterdash.util.currentClassName
import com.babun.flutterdash.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

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

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)

            log("dispose $currentClassName")
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}