package com.relaxing.memorygame.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.relaxing.memorygame.MainActivity
import com.relaxing.memorygame.game.manager.MusicManager
import com.relaxing.memorygame.game.manager.NavigationManager
import com.relaxing.memorygame.game.manager.SoundManager
import com.relaxing.memorygame.game.manager.SpriteManager
import com.relaxing.memorygame.game.manager.util.MusicUtil
import com.relaxing.memorygame.game.manager.util.SoundUtil
import com.relaxing.memorygame.game.manager.util.SpriteUtil
import com.relaxing.memorygame.game.screens.LoaderScreen
import com.relaxing.memorygame.game.utils.GameColor
import com.relaxing.memorygame.game.utils.advanced.AdvancedGame
import com.relaxing.memorygame.game.utils.disposeAll
import com.relaxing.memorygame.util.log

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

    var backgroundColor = Color.BLACK
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
        ScreenUtils.clear(backgroundColor)
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