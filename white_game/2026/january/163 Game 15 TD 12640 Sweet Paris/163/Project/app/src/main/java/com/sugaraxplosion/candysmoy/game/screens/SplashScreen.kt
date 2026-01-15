package com.sugaraxplosion.candysmoy.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sugaraxplosion.candysmoy.game.LibGDXGame
import com.sugaraxplosion.candysmoy.game.manager.MusicManager
import com.sugaraxplosion.candysmoy.game.manager.SoundManager
import com.sugaraxplosion.candysmoy.game.manager.SpriteManager
import com.sugaraxplosion.candysmoy.game.utils.TIME_ANIM
import com.sugaraxplosion.candysmoy.game.utils.actor.animHide
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedStage
import com.sugaraxplosion.candysmoy.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val imgLogo by lazy { Image(game.splash.logo) }
    private val imgLoader by lazy { Image(game.splash.loader) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.LOAD.region)
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
        //addActor(imgLogo)
        //imgLogo.setBounds(47f, 405f, 446f, 446f)

        addActor(imgLoader)
        imgLoader.apply {
            setBounds(173f, 115f, 194f, 174f)
            setOrigin(Align.center)

            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-0.2f, -0.2f, 0.5f, Interpolation.pow2Out),
                        Actions.scaleBy(0.2f, 0.2f, 0.5f, Interpolation.pow2In),
                    )
                )
            )
            addAction(
                Actions.forever(
                    Actions.rotateBy(-360f, 2.5f, Interpolation.pow2InInverse),
                )
            )
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.LOAD.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
        game.spriteManager.initAtlas()
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
                    //delay((10..15L).random())
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