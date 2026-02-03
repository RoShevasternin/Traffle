package com.dasholy.olympusdash.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.dasholy.olympusdash.game.LibGDXGame
import com.dasholy.olympusdash.game.actors.progress.AProgressBar
import com.dasholy.olympusdash.game.manager.MusicManager
import com.dasholy.olympusdash.game.manager.SoundManager
import com.dasholy.olympusdash.game.manager.SpriteManager
import com.dasholy.olympusdash.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.dasholy.olympusdash.game.utils.actor.animHide
import com.dasholy.olympusdash.game.utils.advanced.AdvancedScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedStage
import com.dasholy.olympusdash.game.utils.region
import com.dasholy.olympusdash.game.utils.runGDX
import com.dasholy.olympusdash.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashcScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val progressBar by lazy { AProgressBar(this) }

    override fun show() {
        loadSplashAssets()
        setBackgrounds(game.splashAssets.loadingB.region)
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
        addProgress()

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addProgress() {
        val im = Image(game.splashAssets.pan)
        addActor(im)
        im.setBounds(98f, 118f, 451f, 168f)
        addActor(progressBar)
        progressBar.setBounds(123f, 133f, 406f, 48f)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.loadingB.data,
                SpriteManager.EnumTexture.load.data,
                SpriteManager.EnumTexture.masks.data,
                SpriteManager.EnumTexture.pan.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
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
        game.spriteManager.initAtlasAndTexture()
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
                    runGDX {
                        progressBar.setProgressPercent(progress.toFloat())
//                        progressLbl.setText("$progress%")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((10..12).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = MUSIC.apply { isLooping = true } }

            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }


}