package com.samartachokitse.endelgase.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.samartachokitse.endelgase.game.LibGDXGame
import com.samartachokitse.endelgase.game.manager.MusicManager
import com.samartachokitse.endelgase.game.manager.SoundManager
import com.samartachokitse.endelgase.game.manager.SpriteManager
import com.samartachokitse.endelgase.game.utils.TIME_ANIM
import com.samartachokitse.endelgase.game.utils.actor.animHide
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedStage
import com.samartachokitse.endelgase.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val loader by lazy { Image(game.splash.BOLOTER) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.LOADING.region)
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
            setBounds(109f, 357f, 410f, 404f)
            setOrigin(Align.center)
            addAction(Actions.forever(Actions.rotateBy(360f, 1f, Interpolation.smooth)))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BOLOTER.data,
                SpriteManager.EnumTexture.LOADING.data,
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
//                    if (progress % 85 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((8..14).shuffled().first().toLong())
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