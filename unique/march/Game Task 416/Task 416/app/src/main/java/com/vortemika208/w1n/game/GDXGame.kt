package com.vortemika208.w1n.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.vortemika208.w1n.MainActivity
import com.vortemika208.w1n.game.dataStore.DS_PlayerData
import com.vortemika208.w1n.game.manager.MusicManager
import com.vortemika208.w1n.game.manager.NavigationManager
import com.vortemika208.w1n.game.manager.SoundManager
import com.vortemika208.w1n.game.manager.SpriteManager
import com.vortemika208.w1n.game.manager.util.MusicUtil
import com.vortemika208.w1n.game.manager.util.SoundUtil
import com.vortemika208.w1n.game.manager.util.SpriteUtil
import com.vortemika208.w1n.game.model.PlayerModel
import com.vortemika208.w1n.game.screens.LoaderScreen
import com.vortemika208.w1n.game.utils.GameColor
import com.vortemika208.w1n.game.utils.advanced.AdvancedGame
import com.vortemika208.w1n.game.utils.disposeAll
import com.vortemika208.w1n.util.log
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