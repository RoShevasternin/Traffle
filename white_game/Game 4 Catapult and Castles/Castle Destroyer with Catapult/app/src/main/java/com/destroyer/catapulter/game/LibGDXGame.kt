package com.destroyer.catapulter.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.destroyer.catapulter.MainActivity
import com.destroyer.catapulter.game.manager.MusicManager
import com.destroyer.catapulter.game.manager.NavigationManager
import com.destroyer.catapulter.game.manager.ParticleEffectManager
import com.destroyer.catapulter.game.manager.SoundManager
import com.destroyer.catapulter.game.manager.SpriteManager
import com.destroyer.catapulter.game.manager.util.MusicUtil
import com.destroyer.catapulter.game.manager.util.ParticleEffectUtil
import com.destroyer.catapulter.game.manager.util.SoundUtil
import com.destroyer.catapulter.game.manager.util.SpriteUtil
import com.destroyer.catapulter.game.screens.CatapultaLoaderScreen
import com.destroyer.catapulter.game.utils.advanced.AdvancedGame
import com.destroyer.catapulter.game.utils.disposeAll
import com.destroyer.catapulter.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set
    lateinit var particleEffectManager: ParticleEffectManager private set


    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val allAssets    by lazy { SpriteUtil.AllAssets() }
    val startAssets  by lazy { SpriteUtil.StartAssets() }
    val particleEffectUtil by lazy { ParticleEffectUtil() }


    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)
        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(CatapultaLoaderScreen::class.java.name)
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