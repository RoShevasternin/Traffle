package com.flightcoll.bridgertons.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.flightcoll.bridgertons.game.manager.MusicManager
import com.flightcoll.bridgertons.game.manager.NavigationManager
import com.flightcoll.bridgertons.game.manager.SoundManager
import com.flightcoll.bridgertons.game.manager.SpriteManager
import com.flightcoll.bridgertons.game.manager.util.MusicUtil
import com.flightcoll.bridgertons.game.manager.util.SoundUtil
import com.flightcoll.bridgertons.game.manager.util.SpriteUtil
import com.flightcoll.bridgertons.game.screens.ZagruzonScreen
import com.flightcoll.bridgertons.game.utils.SuberColor
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedGame
import com.flightcoll.bridgertons.game.utils.disposeAll
import com.flightcoll.bridgertons.util.log

class SuberGame(val activity: com.flightcoll.bridgertons.MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assets by lazy { SpriteUtil.VseAssets() }
    val fisters by lazy { SpriteUtil.Start() }

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }

    var backgroundColor = SuberColor.gego
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(ZagruzonScreen::class.java.name)
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