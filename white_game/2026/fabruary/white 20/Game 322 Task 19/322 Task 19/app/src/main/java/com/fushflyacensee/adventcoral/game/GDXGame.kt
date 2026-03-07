package com.fushflyacensee.adventcoral.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.fushflyacensee.adventcoral.MainActivity
import com.fushflyacensee.adventcoral.game.manager.MusicManager
import com.fushflyacensee.adventcoral.game.manager.NavigationManager
import com.fushflyacensee.adventcoral.game.manager.SoundManager
import com.fushflyacensee.adventcoral.game.manager.SpriteManager
import com.fushflyacensee.adventcoral.game.manager.util.MusicUtil
import com.fushflyacensee.adventcoral.game.manager.util.SoundUtil
import com.fushflyacensee.adventcoral.game.manager.util.SpriteUtil
import com.fushflyacensee.adventcoral.game.screens.LoaderScreen
import com.fushflyacensee.adventcoral.game.utils.GameColor
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedGame
import com.fushflyacensee.adventcoral.game.utils.disposeAll
import com.fushflyacensee.adventcoral.util.currentClassName
import com.fushflyacensee.adventcoral.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

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
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)

            log("dispose $currentClassName")
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}