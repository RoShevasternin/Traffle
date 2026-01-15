package com.skycoin.flight.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.skycoin.flight.MainActivity
import com.skycoin.flight.game.manager.MusicManager
import com.skycoin.flight.game.manager.NavigationManager
import com.skycoin.flight.game.manager.SoundManager
import com.skycoin.flight.game.manager.SpriteManager
import com.skycoin.flight.game.manager.util.MusicUtil
import com.skycoin.flight.game.manager.util.SoundUtil
import com.skycoin.flight.game.manager.util.SpriteUtil
import com.skycoin.flight.game.screens.SplashcScreen
import com.skycoin.flight.game.utils.advanced.AdvancedGame
import com.skycoin.flight.game.utils.disposeAll
import com.skycoin.flight.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val splashAssets by lazy { SpriteUtil.SplashAssets() }
    val gameAssets   by lazy { SpriteUtil.GameAssets() }

    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(SplashcScreen::class.java.name)
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