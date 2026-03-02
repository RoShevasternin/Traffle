package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.monkeystreet.roadracejungle.game.actors.AMainLoader
import com.monkeystreet.roadracejungle.game.manager.MusicManager
import com.monkeystreet.roadracejungle.game.manager.SoundManager
import com.monkeystreet.roadracejungle.game.manager.SpriteManager
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame
import com.monkeystreet.roadracejungle.game.utils.runGDX
import com.monkeystreet.roadracejungle.util.log
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

        animShow()

        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {
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
                //SpriteManager.EnumTexture.BACKGROUND.data,
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

            gdxGame.musicUtil.apply { currentMusic = trezdon.apply {
                isLooping = true
                coff      = 0.31f
            } }

            animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}