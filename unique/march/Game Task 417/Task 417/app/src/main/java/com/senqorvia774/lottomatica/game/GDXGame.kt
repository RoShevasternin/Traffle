package com.senqorvia774.lottomatica.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.senqorvia774.lottomatica.MainActivity
import com.senqorvia774.lottomatica.game.dataStore.DS_PlayerData
import com.senqorvia774.lottomatica.game.manager.MusicManager
import com.senqorvia774.lottomatica.game.manager.NavigationManager
import com.senqorvia774.lottomatica.game.manager.SoundManager
import com.senqorvia774.lottomatica.game.manager.SpriteManager
import com.senqorvia774.lottomatica.game.manager.util.MusicUtil
import com.senqorvia774.lottomatica.game.manager.util.SoundUtil
import com.senqorvia774.lottomatica.game.manager.util.SpriteUtil
import com.senqorvia774.lottomatica.game.model.PlayerModel
import com.senqorvia774.lottomatica.game.screens.LoaderScreen
import com.senqorvia774.lottomatica.game.utils.GameColor
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGame
import com.senqorvia774.lottomatica.game.utils.disposeAll
import com.senqorvia774.lottomatica.util.log
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

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val ds_PlayerData = DS_PlayerData(coroutine)
    val modelPlayer   = PlayerModel(coroutine, ds_PlayerData)

    override fun create() {
        navigationManager = NavigationManager()
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
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}