package com.royaltombsecrets.miniquizler.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.royaltombsecrets.miniquizler.game.LibGDXGame
import com.royaltombsecrets.miniquizler.game.manager.MusicManager
import com.royaltombsecrets.miniquizler.game.manager.SoundManager
import com.royaltombsecrets.miniquizler.game.manager.SpriteManager
import com.royaltombsecrets.miniquizler.game.utils.TIME_ANIM
import com.royaltombsecrets.miniquizler.game.utils.actor.animHide
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedScreen
import com.royaltombsecrets.miniquizler.game.utils.advanced.AdvancedStage
import com.royaltombsecrets.miniquizler.game.utils.region
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val princessa by lazy { Image(game.splash.princessa) }
    private val l_left by lazy { Image(game.splash.l_left) }
    private val l_right by lazy { Image(game.splash.l_right) }

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
        addActor(princessa)
        princessa.apply {
            setBounds(595f, 151f, 331f, 598f)
            setOrigin(Align.center)
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-0.2f, -0.2f, 1f, Interpolation.sine),
                        Actions.scaleBy(0.2f, 0.2f, 1f, Interpolation.sine),
                    )
                )
            )
        }
        addActor(l_left)
        l_left.apply {
            setBounds(187f, 342f, 193f, 217f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(360f, 1f, Interpolation.circle)))
        }
        addActor(l_right)
        l_right.apply {
            setBounds(1140f, 342f, 193f, 217f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(-360f, 1f, Interpolation.circle)))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data,
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.BACKGROUND_V.data,
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
                    delay((10..15L).random())
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