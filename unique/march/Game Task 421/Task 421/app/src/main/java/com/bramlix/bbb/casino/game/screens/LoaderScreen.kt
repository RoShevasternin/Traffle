package com.bramlix.bbb.casino.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bramlix.bbb.casino.game.game
import com.bramlix.bbb.casino.game.manager.FontTTFManager
import com.bramlix.bbb.casino.game.manager.MusicManager
import com.bramlix.bbb.casino.game.manager.NavigationManager
import com.bramlix.bbb.casino.game.manager.SpriteManager
import com.bramlix.bbb.casino.game.util.advanced.AdvancedScreen
import com.bramlix.bbb.casino.game.util.advanced.AdvancedStage
import com.bramlix.bbb.casino.game.util.runGDX
import com.bramlix.bbb.casino.util.Once
import com.bramlix.bbb.casino.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LoaderScreen : AdvancedScreen(1280f, 727f) {

   // private val progressLabel by lazy { Label("0", ALabelStyle.akshar_white_25) }

    private val onceFinishLoadingAssets = Once()
    private val progressMutex = Mutex()
    private var progressValue = 0

    private val coroutineUpdateAssets = CoroutineScope(Dispatchers.Main)



    override fun show() {
        loadSplashAssets()
        setBackBackground(SpriteManager.SplashRegion.LOADER_BACKGROUND.region)
        super.show()
        loadAssets()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
    }


    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActor(Image(SpriteManager.SplashRegion.LOADER.region))
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
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.LOADER_BACKGROUND,
                SpriteManager.EnumTexture.LOADER,
            )
            loadTexture(game.assetManager)
        }

        game.assetManager.finishLoading()

        SpriteManager.initTexture(game.assetManager)
    }

    private fun loadAssets() {
        with(SpriteManager) {
            loadableAtlasList   = SpriteManager.EnumAtlas.entries.toMutableList()
            loadableTextureList = SpriteManager.EnumTexture.entries.toMutableList()
            loadAtlas(game.assetManager)
            loadTexture(game.assetManager)
        }
        with(FontTTFManager) {
            loadableListFont = FontTTFManager.BOWLERFont.values.toMutableList()
            load(game.assetManager)
        }
        with(MusicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.toMutableList()
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