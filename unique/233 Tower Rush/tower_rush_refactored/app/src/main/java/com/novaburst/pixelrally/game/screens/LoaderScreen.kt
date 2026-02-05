/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.novaburst.pixelrally.game.actors.main.AMainLoader
import com.novaburst.pixelrally.game.manager.MusicController
import com.novaburst.pixelrally.game.manager.EffectController
import com.novaburst.pixelrally.game.manager.AudioController
import com.novaburst.pixelrally.game.manager.TextureController
import com.novaburst.pixelrally.game.utils.*
import com.novaburst.pixelrally.game.utils.actor.animHide
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.advanced.RenderStage
import com.novaburst.pixelrally.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : DisplayScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val imgBackground by lazy { Image(gdxGame.assetsLoader.BACKGROUND_0.region) }
    private val aMain         by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_0
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_0.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    // Core functionality
    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) { block.invoke() }
    }

    override fun RenderStage.addActorsOnStageBack() {
        addBackground()
    }

    override fun RenderStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors Back------------------------------------------------------------------------

    private fun RenderStage.addBackground() {
        addActor(imgBackground)

        val screenRatio = viewportBack.screenWidth / viewportBack.screenHeight
        val imageRatio = (WIDTH_UI / HEIGHT_UI)

        val scale = if (screenRatio > imageRatio) WIDTH_UI / viewportBack.screenWidth else HEIGHT_UI / viewportBack.screenHeight
        imgBackground.setSize(WIDTH_UI / scale, HEIGHT_UI / scale)
    }

    // Actors UI------------------------------------------------------------------------

    // Processing logic
    private fun RenderStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    // Core functionality
    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(TextureController.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(TextureController.EnumTexture.L_BACKGROUND_0.data)
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
    }

    // Core functionality
    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = TextureController.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = TextureController.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicController.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = AudioController.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.particleEffectManager) {
            loadableParticleEffectList = EffectController.EnumParticleEffect.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initAtlasAndTexture()
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

            hideScreen { gdxGame.navigationManager.navigate(MainMenu::class.java.name) }
        }
    }


}