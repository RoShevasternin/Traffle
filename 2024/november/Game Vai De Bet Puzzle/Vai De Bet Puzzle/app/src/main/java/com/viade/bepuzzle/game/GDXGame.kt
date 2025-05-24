package com.viade.bepuzzle.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.viade.bepuzzle.MainActivity
import com.viade.bepuzzle.game.manager.MusicManager
import com.viade.bepuzzle.game.manager.NavigationManager
import com.viade.bepuzzle.game.manager.SoundManager
import com.viade.bepuzzle.game.manager.SpriteManager
import com.viade.bepuzzle.game.manager.util.MusicUtil
import com.viade.bepuzzle.game.manager.util.SoundUtil
import com.viade.bepuzzle.game.manager.util.SpriteUtil
import com.viade.bepuzzle.game.screens.LoaderScreen
import com.viade.bepuzzle.game.utils.GameColor
import com.viade.bepuzzle.game.utils.advanced.AdvancedGame
import com.viade.bepuzzle.game.utils.dataStore.*
import com.viade.bepuzzle.game.utils.disposeAll
import com.viade.bepuzzle.util.log
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

    val dataStoreBalanceUtil      = DataStoreBalanceUtil(coroutine)
    val dataStoreLevelOpenedUtil  = DataStoreLevelOpenedUtil(coroutine)
    val dataStoreK1Util           = DataStoreK1Util(coroutine)
    val dataStoreK2Util           = DataStoreK2Util(coroutine)
    val dataStoreK5Util           = DataStoreK5Util(coroutine)
    val dataStoreGalleryStarsUtil = DataStoreGalleryStarsUtil(coroutine)
    val dataStoreAwordsUtil       = DataStoreAwordsUtil(coroutine)

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("library", MODE_PRIVATE)

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
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}