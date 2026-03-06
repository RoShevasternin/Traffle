package com.parrotrun.skydrink.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.parrotrun.skydrink.MainActivity
import com.parrotrun.skydrink.game.manager.MusicManager
import com.parrotrun.skydrink.game.manager.NavigationManager
import com.parrotrun.skydrink.game.manager.SoundManager
import com.parrotrun.skydrink.game.manager.SpriteManager
import com.parrotrun.skydrink.game.manager.util.MusicUtil
import com.parrotrun.skydrink.game.manager.util.SoundUtil
import com.parrotrun.skydrink.game.manager.util.SpriteUtil
import com.parrotrun.skydrink.game.screens.LoaderScreen
import com.parrotrun.skydrink.game.utils.advanced.AdvancedGame
import com.parrotrun.skydrink.game.utils.disposeAll
import com.parrotrun.skydrink.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val assetsLoader by lazy { SpriteUtil.LoaderAssets() }
    val assetsAll    by lazy { SpriteUtil.AllAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(Color.BLACK)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}