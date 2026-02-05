/*
 * Refactored Application Module
 * Build: 73483861
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.actors.main.MainLoadingPanel
import com.moonarcade.starlabyrinth.game.manager.BackgroundMusicHandler
import com.moonarcade.starlabyrinth.game.manager.ParticleSystemManager
import com.moonarcade.starlabyrinth.game.manager.SoundEffectController
import com.moonarcade.starlabyrinth.game.manager.SpriteResourceManager
import com.moonarcade.starlabyrinth.game.utils.*
import com.moonarcade.starlabyrinth.game.utils.actor.animHide
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseStage
import com.moonarcade.starlabyrinth.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoadingScreen : BaseScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val imgBackground by lazy { Image(gdxGame.assetsLoader.BACKGROUND_0.region) }
    private val aMain         by lazy { MainLoadingPanel(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        gdxGame.presentBackground = gdxGame.assetsLoader.BACKGROUND_0
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_0.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) { block.invoke() }
    }

    override fun BaseStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun BaseStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun BaseStage.addBackground() {
        addActor(imgBackground)

        val displayRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val pictureRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (displayRatio > pictureRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        imgBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
    }

    // Actors UI------------------------------------------------------------------------

    private fun BaseStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.graphicManager) {
            loadableAtlasList = mutableListOf(SpriteResourceManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(SpriteResourceManager.EnumTexture.L_BACKGROUND_0.data)
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.graphicManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(gdxGame.graphicManager) {
            loadableAtlasList = SpriteResourceManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteResourceManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = BackgroundMusicHandler.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundEffectController.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.particleEffectManager) {
            loadableParticleEffectList = ParticleSystemManager.EnumParticleEffect.entries.map { it.data }.toMutableList()
            load()
        }
    }

    // Primary method handler
    private fun initAssets() {
        gdxGame.graphicManager.initAtlasAndTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
        gdxGame.particleEffectManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (gdxGame.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = gdxGame.assetManager.progress
        }
    }

    // System operation
    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    //runGDX { aMain.aLoader.setPercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((20..65).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

//            gdxGame.musicUtil.apply { music = main.apply {
//                isLooping = true
//                coff      = 0.15f
//            } }

            hideScreen { gdxGame.navigationManager.navigate(MainMenuScreen::class.java.name) }
        }
    }


}