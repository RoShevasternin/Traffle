package tiger.five.lines.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import tiger.five.lines.MainActivity
import tiger.five.lines.game.manager.MusicManager
import tiger.five.lines.game.manager.NavigationManager
import tiger.five.lines.game.manager.SoundManager
import tiger.five.lines.game.manager.SpriteManager
import tiger.five.lines.game.manager.util.MusicUtil
import tiger.five.lines.game.manager.util.SoundUtil
import tiger.five.lines.game.manager.util.SpriteUtil
import tiger.five.lines.game.screens.YellowLoadingScreen
import tiger.five.lines.game.utils.advanced.AdvancedGame
import tiger.five.lines.game.utils.disposeAll
import tiger.five.lines.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil     by lazy { MusicUtil()    }
    val soundUtil     by lazy { SoundUtil()    }
    val loadingAssets by lazy { SpriteUtil.SplashAssets() }
    val allAssets     by lazy { SpriteUtil.GameAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(YellowLoadingScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}