package com.swee.ttrio.comb.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.swee.ttrio.comb.GameActivity
import com.swee.ttrio.comb.game.manager.*
import com.swee.ttrio.comb.game.manager.util.MusicUtil
import com.swee.ttrio.comb.game.manager.util.SoundUtil
import com.swee.ttrio.comb.game.manager.util.SpriteUtil
import com.swee.ttrio.comb.game.screens.SplashScreen
import com.swee.ttrio.comb.game.utils.GColor
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGame
import com.swee.ttrio.comb.game.utils.disposeAll
import com.swee.ttrio.comb.util.log

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