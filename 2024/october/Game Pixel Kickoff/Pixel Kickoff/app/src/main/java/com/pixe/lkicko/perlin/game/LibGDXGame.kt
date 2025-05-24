package com.pixe.lkicko.perlin.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.pixe.lkicko.perlin.GameActivity
import com.pixe.lkicko.perlin.game.dataStore.DataStore
import com.pixe.lkicko.perlin.game.manager.MusicManager
import com.pixe.lkicko.perlin.game.manager.NavigationManager
import com.pixe.lkicko.perlin.game.manager.SoundManager
import com.pixe.lkicko.perlin.game.manager.SpriteManager
import com.pixe.lkicko.perlin.game.manager.util.MusicUtil
import com.pixe.lkicko.perlin.game.manager.util.SoundUtil
import com.pixe.lkicko.perlin.game.manager.util.SpriteUtil
import com.pixe.lkicko.perlin.game.screens.LoaderScreen
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGame
import com.pixe.lkicko.perlin.game.utils.disposeAll
import com.pixe.lkicko.perlin.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val loading by lazy { SpriteUtil.Loader() }
    val all    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.background
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

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("SUPER_PUZZLE", MODE_PRIVATE)

    val dataStore = DataStore(sharedPreferences)

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