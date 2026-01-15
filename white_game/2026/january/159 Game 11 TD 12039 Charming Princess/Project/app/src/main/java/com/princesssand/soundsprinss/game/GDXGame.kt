package com.princesssand.soundsprinss.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.princesssand.soundsprinss.MainActivity
import com.princesssand.soundsprinss.game.manager.MusicManager
import com.princesssand.soundsprinss.game.manager.NavigationManager
import com.princesssand.soundsprinss.game.manager.ParticleEffectManager
import com.princesssand.soundsprinss.game.manager.SoundManager
import com.princesssand.soundsprinss.game.manager.SpriteManager
import com.princesssand.soundsprinss.game.manager.util.MusicUtil
import com.princesssand.soundsprinss.game.manager.util.ParticleEffectUtil
import com.princesssand.soundsprinss.game.manager.util.SoundUtil
import com.princesssand.soundsprinss.game.manager.util.SpriteUtil
import com.princesssand.soundsprinss.game.screens.LoaderScreen
import com.princesssand.soundsprinss.game.utils.GameColor
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGame
import com.princesssand.soundsprinss.game.utils.dataStoreUtil.IsTutorialsUtil
import com.princesssand.soundsprinss.game.utils.dataStoreUtil.LevelUtil
import com.princesssand.soundsprinss.game.utils.dataStoreUtil.ResultUtil
import com.princesssand.soundsprinss.game.utils.disposeAll
import com.princesssand.soundsprinss.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager private set
    lateinit var musicManager     : MusicManager private set
    lateinit var soundManager     : SoundManager private set
    lateinit var particleEffectManager: ParticleEffectManager private set

    val assetsAll    by lazy { SpriteUtil.All() }
    val assetsLoader by lazy { SpriteUtil.Loader() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    val particleEffectUtil by lazy { ParticleEffectUtil() }


    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val resultUtil = ResultUtil(coroutine)
    val levelUtil  = LevelUtil(coroutine)
    val isTutorialsUtil = IsTutorialsUtil(coroutine)

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        particleEffectManager = ParticleEffectManager(assetManager)

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