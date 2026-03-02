package com.tictactoe.classic.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.tictactoe.classic.game.actors.AMainLoader
import com.tictactoe.classic.game.manager.MusicManager
import com.tictactoe.classic.game.manager.SoundManager
import com.tictactoe.classic.game.manager.SpriteManager
import com.tictactoe.classic.game.utils.Block
import com.tictactoe.classic.game.utils.HEIGHT_UI
import com.tictactoe.classic.game.utils.TIME_ANIM_SCREEN
import com.tictactoe.classic.game.utils.WIDTH_UI
import com.tictactoe.classic.game.utils.actor.HAlign
import com.tictactoe.classic.game.utils.actor.VAlign
import com.tictactoe.classic.game.utils.actor.addActorAligned
import com.tictactoe.classic.game.utils.actor.animHide
import com.tictactoe.classic.game.utils.advanced.AdvancedScreen
import com.tictactoe.classic.game.utils.gdxGame
import com.tictactoe.classic.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun Group.addActorsOnStageUI() {
        //aMain.debug()
        aMain.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aMain, HAlign.CENTER, VAlign.CENTER)
    }

    override fun animHideScreen(blockEnd: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {}

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableTexturesList = mutableListOf(SpriteManager.EnumTexture.LOADER.data)
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
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
                    //runGDX { aMain.progressLbl.setText("$progress%") }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((5..10).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            gdxGame.musicUtil.apply { currentMusic = ale.apply {
                isLooping = true
                coff      = 0.55f
            } }

            animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }

        }
    }


}