package com.sweetfruit.catchermandarin.game.screens

import com.sweetfruit.catchermandarin.game.PoromGame
import com.sweetfruit.catchermandarin.game.manager.MusicManager
import com.sweetfruit.catchermandarin.game.manager.ParticleEffectManager
import com.sweetfruit.catchermandarin.game.manager.SoundManager
import com.sweetfruit.catchermandarin.game.manager.SpriteManager
import com.sweetfruit.catchermandarin.game.utils.Poshlo_VREMA
import com.sweetfruit.catchermandarin.game.utils.actor.animHide
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedScreen
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedStage
import com.sweetfruit.catchermandarin.game.utils.region
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LodrinkingScreen(override val game: PoromGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.guglas.lodrinking.region)
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
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.particleEffectManager) {
            loadableParticleEffectList = ParticleEffectManager.EnumParticleEffect.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.musicManager.init()
        game.soundManager.init()
        game.particleEffectManager.init()
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

            game.musicUtil.apply { music = Luke.apply {
                isLooping = true
                volumeLevelFlow.value = 18f
            } }

            stageUI.root.animHide(Poshlo_VREMA) {
                game.navigationManager.navigate(MedinaOleylaScreen::class.java.name)
            }
        }
    }


}