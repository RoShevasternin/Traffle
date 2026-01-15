package com.skyplane.puzzleflight.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.skyplane.puzzleflight.MainActivity
import com.skyplane.puzzleflight.game.manager.MusicManager
import com.skyplane.puzzleflight.game.manager.NavigationManager
import com.skyplane.puzzleflight.game.manager.SoundManager
import com.skyplane.puzzleflight.game.manager.SpriteManager
import com.skyplane.puzzleflight.game.manager.util.MusicUtil
import com.skyplane.puzzleflight.game.manager.util.SoundUtil
import com.skyplane.puzzleflight.game.manager.util.SpriteUtil
import com.skyplane.puzzleflight.game.screens.LoadScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedGame
import com.skyplane.puzzleflight.game.utils.disposeAll
import com.skyplane.puzzleflight.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val loadAssets   by lazy { SpriteUtil.LoadAssets() }
    val allAssets    by lazy { SpriteUtil.AllAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoadScreen::class.java.name)
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