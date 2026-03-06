package com.wintergroup.cupcakejump.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.wintergroup.cupcakejump.game.actors.AMainLoader
import com.wintergroup.cupcakejump.game.manager.MusicManager
import com.wintergroup.cupcakejump.game.manager.SoundManager
import com.wintergroup.cupcakejump.game.manager.SpriteManager
import com.wintergroup.cupcakejump.game.utils.Block
import com.wintergroup.cupcakejump.game.utils.HEIGHT_UI
import com.wintergroup.cupcakejump.game.utils.TIME_ANIM_SCREEN
import com.wintergroup.cupcakejump.game.utils.WIDTH_UI
import com.wintergroup.cupcakejump.game.utils.actor.HAlign
import com.wintergroup.cupcakejump.game.utils.actor.VAlign
import com.wintergroup.cupcakejump.game.utils.actor.addActorAligned
import com.wintergroup.cupcakejump.game.utils.actor.animHide
import com.wintergroup.cupcakejump.game.utils.actor.animShow
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedScreen
import com.wintergroup.cupcakejump.game.utils.gdxGame
import com.wintergroup.cupcakejump.game.utils.runGDX
import com.wintergroup.cupcakejump.util.log
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
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND)
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
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                //SpriteManager.EnumTexture.BACKGROUND.data,
            )
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
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
        gdxGame.spriteManager.initAtlasAndTexture()
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


            gdxGame.musicUtil.apply { currentMusic = grand.apply {
                isLooping = true
                coff      = 0.25f
            } }


            animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }
        }
    }


}