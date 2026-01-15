package com.cosmostarsca.quantumfruits.game.screens

import com.cosmostarsca.quantumfruits.game.LibGDXGame
import com.cosmostarsca.quantumfruits.game.manager.MusicManager
import com.cosmostarsca.quantumfruits.game.manager.SoundManager
import com.cosmostarsca.quantumfruits.game.manager.SpriteManager
import com.cosmostarsca.quantumfruits.game.utils.TIME_ANIM
import com.cosmostarsca.quantumfruits.game.utils.actor.animHide
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedScreen
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedStage
import com.cosmostarsca.quantumfruits.game.utils.region
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StartScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.load.Loader.region)
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {}

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(SpriteManager.EnumTexture.Loader.data)
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

                    delay((10..12).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            game.musicUtil.apply { music = musicality.apply {
                isLooping = true
                volumeLevelFlow.value = 17f
            } }

            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }


}