package com.royaltombsecrets.miniquizler.game

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.royaltombsecrets.miniquizler.GameActivity
import com.royaltombsecrets.miniquizler.game.manager.MusicManager
import com.royaltombsecrets.miniquizler.game.manager.NavigationManager
import com.royaltombsecrets.miniquizler.game.manager.SoundManager
import com.royaltombsecrets.miniquizler.game.manager.SpriteManager
import com.royaltombsecrets.miniquizler.game.manager.util.MusicUtil
import com.royaltombsecrets.miniquizler.game.manager.util.SoundUtil
import com.royaltombsecrets.miniquizler.game.manager.util.SpriteUtil
import com.royaltombsecrets.miniquizler.game.screens.SplashScreen
import com.royaltombsecrets.miniquizler.game.utils.GColor
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedGame
import com.royaltombsecrets.miniquizler.game.utils.disposeAll
import com.royaltombsecrets.miniquizler.util.log

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