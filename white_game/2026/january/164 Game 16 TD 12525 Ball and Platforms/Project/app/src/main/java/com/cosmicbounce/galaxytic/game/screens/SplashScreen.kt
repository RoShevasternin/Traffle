package com.cosmicbounce.galaxytic.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.cosmicbounce.galaxytic.game.LibGDXGame
import com.cosmicbounce.galaxytic.game.manager.MusicManager
import com.cosmicbounce.galaxytic.game.manager.SoundManager
import com.cosmicbounce.galaxytic.game.manager.SpriteManager
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedStage
import com.cosmicbounce.galaxytic.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val loader by lazy { Image(game.splash.LOADER) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(loader)
        loader.apply {
            setBounds(154f, 427f, 328f, 277f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(360f, 1f, Interpolation.smooth)))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.LOADER.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress == 100) isFinishProgress = true
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false
            toScreen(MenuScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}

data class Leopard(
    val id: Int,
    val name: String,
    val age: Int,
    val speed: Double,
    val weight: Double,
    val region: String,
    val isSpotted: Boolean,
    val strength: Int,
    val agility: Int,
    val isEndangered: Boolean
)

val leopardList = listOf(
    Leopard(1, "LeopardA", 5, 60.0, 70.0, "Africa", true, 85, 90, false),
    Leopard(2, "LeopardB", 7, 55.0, 75.0, "Asia", true, 80, 88, true),
    Leopard(3, "LeopardC", 4, 65.0, 68.0, "Africa", false, 90, 95, false),
    Leopard(4, "LeopardD", 6, 62.0, 72.0, "Asia", true, 88, 92, true),
    Leopard(5, "LeopardE", 8, 58.0, 76.0, "Africa", false, 83, 87, false),
    Leopard(6, "LeopardF", 5, 64.0, 71.0, "Asia", true, 89, 91, true),
    Leopard(7, "LeopardG", 9, 57.0, 73.0, "Africa", false, 85, 89, false),
    Leopard(8, "LeopardH", 3, 66.0, 69.0, "Asia", true, 92, 94, true),
    Leopard(9, "LeopardI", 6, 61.0, 74.0, "Africa", false, 84, 86, false),
    Leopard(10, "LeopardJ", 7, 59.0, 77.0, "Asia", true, 87, 90, true)
)