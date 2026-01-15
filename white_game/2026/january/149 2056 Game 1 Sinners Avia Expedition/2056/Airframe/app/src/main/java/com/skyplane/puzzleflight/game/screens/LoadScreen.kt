package com.skyplane.puzzleflight.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.skyplane.puzzleflight.game.LibGDXGame
import com.skyplane.puzzleflight.game.actors.progress.ALoading
import com.skyplane.puzzleflight.game.manager.MusicManager
import com.skyplane.puzzleflight.game.manager.SoundManager.*
import com.skyplane.puzzleflight.game.manager.SpriteManager.*
import com.skyplane.puzzleflight.game.utils.actor.setBounds
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedScreen
import com.skyplane.puzzleflight.game.utils.advanced.AdvancedStage
import com.skyplane.puzzleflight.game.utils.region
import com.skyplane.puzzleflight.game.utils.runGDX
import com.skyplane.puzzleflight.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.skyplane.puzzleflight.game.utils.Layout.Splash as LS

class LoadScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val textImg      by lazy { Image(game.loadAssets.load) }
    private val loading      by lazy { ALoading(this) }

    override fun show() {
        loadSplashAssets()
        game.activity.lottie.hideLoader()
        setBackgrounds(game.loadAssets.background.region)
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
        addText()
        addLoading()

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addText() {
        val avia = Image(game.loadAssets.plane)
        addActor(avia)
        avia.setBounds(5f, 575f, 638f, 506f)

        addActor(textImg)
        textImg.setBounds(78f, 139f, 492f, 155f)
    }

    private fun AdvancedStage.addLoading() {
        addActor(loading)
        loading.setBounds(LS.loading)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(
                EnumTexture.background.data,
                EnumTexture.load.data,
                EnumTexture.mask.data,
                EnumTexture.plane.data,
                EnumTexture.sss.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = EnumSound.entries.map { it.data }.toMutableList()
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
                        loading.setProgressPercent(progress.toFloat())
//                        progressLabel.setText("$progress%")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((10..15).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = Techno_MUSIC.apply { isLooping = true } }
            animHideScreen { game.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}