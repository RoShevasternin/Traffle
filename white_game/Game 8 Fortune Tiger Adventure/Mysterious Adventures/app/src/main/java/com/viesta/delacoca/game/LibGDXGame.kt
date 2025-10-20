package com.viesta.delacoca.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.viesta.delacoca.MainActivity
import com.viesta.delacoca.game.manager.MusicManager
import com.viesta.delacoca.game.manager.NavigationManager
import com.viesta.delacoca.game.manager.SoundManager
import com.viesta.delacoca.game.manager.SpriteManager
import com.viesta.delacoca.game.manager.util.MusicUtil
import com.viesta.delacoca.game.manager.util.SoundUtil
import com.viesta.delacoca.game.manager.util.SpriteUtil
import com.viesta.delacoca.game.screens.IncasLoadingScreen
import com.viesta.delacoca.game.utils.advanced.AdvancedGame
import com.viesta.delacoca.game.utils.disposeAll
import com.viesta.delacoca.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil     by lazy { MusicUtil()    }
    val soundUtil     by lazy { SoundUtil()    }
    val loadingAssets by lazy { SpriteUtil.SplashAssets() }
    val allAssets     by lazy { SpriteUtil.GameAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(IncasLoadingScreen::class.java.name)
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