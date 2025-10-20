package com.tigermaner.rowtiger.game.screens

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.tigermaner.rowtiger.game.LibGDXGame
import com.tigermaner.rowtiger.game.actors.TigerLoader
import com.tigermaner.rowtiger.game.actors.progress.ValLoaderProgress
import com.tigermaner.rowtiger.game.manager.MusicManager
import com.tigermaner.rowtiger.game.manager.SoundManager
import com.tigermaner.rowtiger.game.manager.SpriteManager
import com.tigermaner.rowtiger.game.utils.TIME_ANIM
import com.tigermaner.rowtiger.game.utils.actor.animShow
import com.tigermaner.rowtiger.game.utils.actor.setBounds
import com.tigermaner.rowtiger.game.utils.advanced.AdvancedScreen
import com.tigermaner.rowtiger.game.utils.advanced.AdvancedStage
import com.tigermaner.rowtiger.game.utils.region
import com.tigermaner.rowtiger.game.utils.runGDX
import com.tigermaner.rowtiger.util.log
import com.tigermaner.rowtiger.game.utils.Layout.Splash as LS

class ValLoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val valLoaderProgress by lazy { ValLoaderProgress(this) }
    //private val valTigerLoader    by lazy { TigerLoader(this) }

    override fun show() {
//        stageUI.root.color.a = 0f
        loadSplashAssets()
        game.activity.lottie.hideLoader()
        setBackgrounds(game.loadingAssets.ValBackground.region)
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
        addValProgress()
        addTigerLoader()

        //valTigerLoader.startAnim()

        isFinishAnim = true

    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addValProgress() {
        addActor(valLoaderProgress)
        valLoaderProgress.setBounds(LS.valProgress)
    }

    private fun AdvancedStage.addTigerLoader() {
        //addActor(valTigerLoader)
        //valTigerLoader.setBounds(LS.valTiger)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.valLoader.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.ValBackground.data,
                SpriteManager.EnumTexture.vmaskav.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initAtlasAndTexture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.values().map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.values().map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.values().map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.values().map { it.data }.toMutableList()
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
                    runGDX { valLoaderProgress.setProgressPercent(progress.toFloat()) }
                    if (progress % 33 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((5..10).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            //valTigerLoader.endAnim()

            game.musicUtil.apply { music = JAPAN_MUSIC.apply { isLooping = true } }
            game.navigationManager.navigate(ValMenuScreen::class.java.name)
        }
    }


}