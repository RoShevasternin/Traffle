package com.sugaraxplosion.candysmoy.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.sugaraxplosion.candysmoy.GameActivity
import com.sugaraxplosion.candysmoy.game.manager.MusicManager
import com.sugaraxplosion.candysmoy.game.manager.NavigationManager
import com.sugaraxplosion.candysmoy.game.manager.SoundManager
import com.sugaraxplosion.candysmoy.game.manager.SpriteManager
import com.sugaraxplosion.candysmoy.game.manager.util.MusicUtil
import com.sugaraxplosion.candysmoy.game.manager.util.SoundUtil
import com.sugaraxplosion.candysmoy.game.manager.util.SpriteUtil
import com.sugaraxplosion.candysmoy.game.screens.SplashScreen
import com.sugaraxplosion.candysmoy.game.utils.GColor
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedGame
import com.sugaraxplosion.candysmoy.game.utils.disposeAll
import com.sugaraxplosion.candysmoy.util.log

class LibGDXGame(val activity: GameActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val splash by lazy { SpriteUtil.Splash() }
    val all    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(SplashScreen::class.java.name)
    }

    val sharedPreferences: SharedPreferences = activity.getSharedPreferences("GAME_DATA", MODE_PRIVATE)

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