package com.bounceques.ternationaret.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.bounceques.ternationaret.MainActivity
import com.bounceques.ternationaret.game.manager.MusicManager
import com.bounceques.ternationaret.game.manager.NavigationManager
import com.bounceques.ternationaret.game.manager.SoundManager
import com.bounceques.ternationaret.game.manager.SpriteManager
import com.bounceques.ternationaret.game.manager.util.MusicUtil
import com.bounceques.ternationaret.game.manager.util.SoundUtil
import com.bounceques.ternationaret.game.manager.util.SpriteUtil
import com.bounceques.ternationaret.game.screens.PinkLoaderScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedGame
import com.bounceques.ternationaret.game.utils.disposeAll
import com.bounceques.ternationaret.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val assetsAll    by lazy { SpriteUtil.AllAssets() }
    val assetsLoader by lazy { SpriteUtil.StartAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(PinkLoaderScreen::class.java.name)
    }

    private val colorBackground = Color.BLACK

    override fun render() {
        ScreenUtils.clear(colorBackground)
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