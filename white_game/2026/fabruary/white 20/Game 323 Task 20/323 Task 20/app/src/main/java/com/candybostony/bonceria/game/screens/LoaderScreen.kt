package com.candybostony.bonceria.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.candybostony.bonceria.game.actors.AMainLoader
import com.candybostony.bonceria.game.manager.MusicManager
import com.candybostony.bonceria.game.manager.SoundManager
import com.candybostony.bonceria.game.manager.SpriteManager
import com.candybostony.bonceria.game.utils.Block
import com.candybostony.bonceria.game.utils.HEIGHT_UI
import com.candybostony.bonceria.game.utils.TIME_ANIM_SCREEN
import com.candybostony.bonceria.game.utils.WIDTH_UI
import com.candybostony.bonceria.game.utils.actor.HAlign
import com.candybostony.bonceria.game.utils.actor.VAlign
import com.candybostony.bonceria.game.utils.actor.addActorAligned
import com.candybostony.bonceria.game.utils.actor.animHide
import com.candybostony.bonceria.game.utils.actor.animShow
import com.candybostony.bonceria.game.utils.advanced.AdvancedScreen
import com.candybostony.bonceria.game.utils.gdxGame
import com.candybostony.bonceria.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        stageUI.root.color.a = 0f

        loadSplashAssets()
        super.show()

        animShowScreen()

        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    override fun Group.addActorsOnStageUI() {
        aMain.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aMain, HAlign.CENTER, VAlign.CENTER)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.LOADER.data,
            )
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

                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((25..35).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            gdxGame.musicUtil.apply { currentMusic = YRAAAAAAA.apply {
                isLooping = true
                coff      = 0.333f
            } }

            animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}