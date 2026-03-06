package com.bunnypanny.eggcatch.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.bunnypanny.eggcatch.MainActivity
import com.bunnypanny.eggcatch.game.manager.MusicManager
import com.bunnypanny.eggcatch.game.manager.NavigationManager
import com.bunnypanny.eggcatch.game.manager.SoundManager
import com.bunnypanny.eggcatch.game.manager.SpriteManager
import com.bunnypanny.eggcatch.game.manager.util.MusicUtil
import com.bunnypanny.eggcatch.game.manager.util.SoundUtil
import com.bunnypanny.eggcatch.game.manager.util.SpriteUtil
import com.bunnypanny.eggcatch.game.screens.LoaderScreen
import com.bunnypanny.eggcatch.game.utils.advanced.AdvancedGame
import com.bunnypanny.eggcatch.game.utils.disposeAll
import com.bunnypanny.eggcatch.util.log

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val assetsLoader by lazy { SpriteUtil.SplashAssets() }
    val assetsAll    by lazy { SpriteUtil.GameAssets() }

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