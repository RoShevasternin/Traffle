package com.sweetfruit.catchermandarin.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.sweetfruit.catchermandarin.game.manager.*
import com.sweetfruit.catchermandarin.game.manager.util.MusicUtil
import com.sweetfruit.catchermandarin.game.manager.util.ParticleEffectUtil
import com.sweetfruit.catchermandarin.game.manager.util.SoundUtil
import com.sweetfruit.catchermandarin.game.manager.util.SpriteUtil
import com.sweetfruit.catchermandarin.game.screens.LodrinkingScreen
import com.sweetfruit.catchermandarin.game.utils.PoromColor
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedGame
import com.sweetfruit.catchermandarin.game.utils.disposeAll
import com.sweetfruit.catchermandarin.util.log

class PoromGame(val activity: com.sweetfruit.catchermandarin.MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set
    lateinit var particleEffectManager: ParticleEffectManager private set

    val faradeo by lazy { SpriteUtil.Faradeo() }
    val guglas  by lazy { SpriteUtil.Guglas() }

    val musicUtil          by lazy { MusicUtil()    }
    val soundUtil          by lazy { SoundUtil()    }
    val particleEffectUtil by lazy { ParticleEffectUtil() }


    var backgroundColor = PoromColor.saburno
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        particleEffectManager = ParticleEffectManager(assetManager)

        navigationManager.navigate(LodrinkingScreen::class.java.name)
    }

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