package com.veldan.cosmolot.game.screens

import android.annotation.SuppressLint
import com.veldan.cosmolot.MainActivity
import com.veldan.cosmolot.game.game
import com.veldan.cosmolot.game.manager.FontTTFManager
import com.veldan.cosmolot.game.manager.MusicManager
import com.veldan.cosmolot.game.manager.NavigationManager
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.advanced.AdvancedScreen
import com.veldan.cosmolot.game.util.advanced.AdvancedStage
import com.veldan.cosmolot.game.util.runGDX
import com.veldan.cosmolot.util.Once
import com.veldan.cosmolot.util.log
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@SuppressLint("CustomSplashScreen")
class SplashScreen : AdvancedScreen(1280f, 727f) {

   // private val progressLabel by lazy { Label("0", ALabelStyle.akshar_white_25) }

    private val onceFinishLoadingAssets = Once()
    private val progressMutex = Mutex()
    private var progressValue = 0

    private val coroutineUpdateAssets = CoroutineScope(Dispatchers.Main)



    override fun show() {
        loadSplashAssets()
        setBackgrounds(SpriteManager.SplashRegion.BACKGROUND.region)
        super.show()
        loadAssets()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
    }


    override fun AdvancedStage.addActorsOnStageUI() {
//        addPanel()
//        addProgress()
    }


    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
//    private fun AdvancedStage.addPanel() {
//        addActor(panelImage)
//        panelImage.apply {
//            with(LS.panel) { setBounds(x, y, w, h) }
//        }
//    }
//
//    private fun AdvancedStage.addProgress() {
//        addActor(progressLabel)
//        progressLabel.apply {
//            with(LS.progress) { setBounds(x, y, w, h) }
//            setAlignment(Align.center)
//        }
//    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------
    private fun loadSplashAssets() {
        with(SpriteManager) {
            loadableTextureList = mutableListOf(SpriteManager.EnumTexture.BACKGROUND)
            loadTexture(game.assetManager)
        }

        game.assetManager.finishLoading()

        SpriteManager.initTexture(game.assetManager)
    }

    private fun loadAssets() {
        with(SpriteManager) {
            loadableAtlasList   = SpriteManager.EnumAtlas.values().toMutableList()
            loadableTextureList = SpriteManager.EnumTexture.values().toMutableList()
            loadAtlas(game.assetManager)
            loadTexture(game.assetManager)
        }
        with(FontTTFManager) {
            loadableListFont = FontTTFManager.BOWLERFont.values.toMutableList()
            load(game.assetManager)
        }
        with(MusicManager) {
            loadableMusicList = MusicManager.EnumMusic.values().toMutableList()
            load(game.assetManager)
        }
    }

    private fun loadingAssets() {
        if (coroutineUpdateAssets.isActive) {
            coroutineUpdateAssets.launch {
                progressMutex.withLock {
                    runGDX { game.assetManager.update() }
                    showProgress(game.assetManager.progress)
                }
            }
        }
    }

    private suspend fun showProgress(progress: Float) {
        while ((progressValue / 100f) < progress) {
            progressValue++
            log("$progressValue%")
          //  runGDX { progressLabel.setText("$progressValue%") }
            if (progressValue == 100) {
                onceFinishLoadingAssets.once {
                    coroutineUpdateAssets.cancel()
                    runGDX {
                        game.assetManager.finishLoading()
                        initAssets()
                        NavigationManager.navigate(MenuScreen())
                        MainActivity.lottie.hideLoader()
                    }
                }
            }
            //delay((10..30).shuffled().first().toLong())
        }
    }

    private fun initAssets() {
        SpriteManager.initAtlas(game.assetManager)
        SpriteManager.initTexture(game.assetManager)
        FontTTFManager.init(game.assetManager)
        MusicManager.init(game.assetManager)
    }

}