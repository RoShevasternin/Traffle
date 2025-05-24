package com.colderonetrains.battlesskates.game.screens

import com.colderonetrains.battlesskates.game.GDX_GLOBAL_isGame
import com.colderonetrains.battlesskates.game.GDX_GLOBAL_isLoadAssets
import com.colderonetrains.battlesskates.game.actors.ABackground
import com.colderonetrains.battlesskates.game.actors.main.AMainLoader
import com.colderonetrains.battlesskates.game.manager.MusicManager
import com.colderonetrains.battlesskates.game.manager.SoundManager
import com.colderonetrains.battlesskates.game.manager.SpriteManager
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedStage
import com.colderonetrains.battlesskates.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.div
import kotlin.text.compareTo

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aBackground by lazy { ABackground(this, gdxGame.assetsLoader.BACKGROUND_SPLASH) }
    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

    override fun AdvancedStage.addActorsOnStageBack() {
        addAndFillActor(aBackground)
        //aBackground.animToNewTexture(gdxGame.assetsLoader.BACKGROUND, TIME_ANIM_SCREEN)
        gdxGame.currentBackground = gdxGame.assetsLoader.BACKGROUND_SPLASH
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            //loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            //loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.L_BACKGROUND_SPLASH.data,
                SpriteManager.EnumTexture.L_LOADE.data,
            )
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initTexture()
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
                    runGDX { aMain.updatePercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        GDX_GLOBAL_isLoadAssets = true
        gdxGame.activity.hideWebView()

        gdxGame.musicUtil.apply { currentMusic = SPORT.apply {
            isLooping = true
            coff      = 0.15f
        } }

        hideScreen {
            if (gdxGame.ds_IsWelcome.flow.value) {
                gdxGame.navigationManager.navigate(WelcomeScreen::class.java.name)
            } else {
                gdxGame.navigationManager.navigate(HomeScreen::class.java.name)
            }
        }
    }


}