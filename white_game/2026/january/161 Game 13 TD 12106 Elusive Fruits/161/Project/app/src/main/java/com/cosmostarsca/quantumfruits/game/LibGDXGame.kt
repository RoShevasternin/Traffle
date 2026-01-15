package com.cosmostarsca.quantumfruits.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.cosmostarsca.quantumfruits.GameActivity
import com.cosmostarsca.quantumfruits.game.manager.*
import com.cosmostarsca.quantumfruits.game.manager.util.MusicUtil
import com.cosmostarsca.quantumfruits.game.manager.util.SoundUtil
import com.cosmostarsca.quantumfruits.game.manager.util.SpriteUtil
import com.cosmostarsca.quantumfruits.game.screens.StartScreen
import com.cosmostarsca.quantumfruits.game.utils.GColor
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedGame
import com.cosmostarsca.quantumfruits.game.utils.disposeAll
import com.cosmostarsca.quantumfruits.util.log

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assets by lazy { SpriteUtil.Assets() }
    val load   by lazy { SpriteUtil.Load() }

    val musicUtil          by lazy { MusicUtil()    }
    val soundUtil          by lazy { SoundUtil()    }

    var backgroundColor = GColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(StartScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}