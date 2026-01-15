package com.bounceroval.mazedackq.game.screens

import com.bounceroval.mazedackq.game.LibGDXGame
import com.bounceroval.mazedackq.game.manager.MusicManager
import com.bounceroval.mazedackq.game.manager.SoundManager
import com.bounceroval.mazedackq.game.manager.SpriteManager
import com.bounceroval.mazedackq.game.utils.TIME_ANIM
import com.bounceroval.mazedackq.game.utils.actor.animHide
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedStage
import com.bounceroval.mazedackq.game.utils.region
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.splash.loading.region)
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

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.bg1.data,
                SpriteManager.EnumTexture.bg2.data,
                SpriteManager.EnumTexture.bg3.data,
                SpriteManager.EnumTexture.loading.data,
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

                    delay((8..14).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            fun toScreen(screenName: String) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(screenName)
                }
            }

            toScreen(MenuScreen::class.java.name)
        }
    }

}