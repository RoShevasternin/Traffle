package com.quenloria615.beton.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.quenloria615.beton.MainActivity
import com.quenloria615.beton.game.dataStore.DS_PlayerData
import com.quenloria615.beton.game.manager.MusicManager
import com.quenloria615.beton.game.manager.NavigationManager
import com.quenloria615.beton.game.manager.SoundManager
import com.quenloria615.beton.game.manager.SpriteManager
import com.quenloria615.beton.game.manager.util.MusicUtil
import com.quenloria615.beton.game.manager.util.SoundUtil
import com.quenloria615.beton.game.manager.util.SpriteUtil
import com.quenloria615.beton.game.model.PlayerModel
import com.quenloria615.beton.game.screens.LoaderScreen
import com.quenloria615.beton.game.utils.GameColor
import com.quenloria615.beton.game.utils.advanced.AdvancedGame
import com.quenloria615.beton.game.utils.disposeAll
import com.quenloria615.beton.util.log
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