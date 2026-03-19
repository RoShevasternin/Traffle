package com.rivonexgame.total.casino.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.rivonexgame.total.casino.game.LibGDXGame
import com.rivonexgame.total.casino.game.actors.progress.ASlotCityProgress
import com.rivonexgame.total.casino.game.manager.MusicManager
import com.rivonexgame.total.casino.game.manager.SoundManager
import com.rivonexgame.total.casino.game.manager.SpriteManager
import com.rivonexgame.total.casino.game.utils.TIME_ANIM_ALPHA
import com.rivonexgame.total.casino.game.utils.actor.animHide
import com.rivonexgame.total.casino.game.utils.actor.setBounds
import com.rivonexgame.total.casino.game.utils.advanced.AdvancedScreen
import com.rivonexgame.total.casino.game.utils.advanced.AdvancedStage
import com.rivonexgame.total.casino.game.utils.runGDX
import com.rivonexgame.total.casino.util.log
import com.rivonexgame.total.casino.game.utils.Layout.Splash as LS

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val logoImg     by lazy { Image(game.loaderAssets.logo) }
    private val backImg     by lazy { Image(game.loaderAssets.prog_back) }
    private val progressBar by lazy { ASlotCityProgress(this) }

    override fun show() {
        loadSplashAssets()
        setBackBackground(game.loaderAssets.background_loader)
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
        addActor(logoImg)
        logoImg.setBounds(415f, 301f, 1091f, 563f)
        addActor(backImg)
        backImg.setBounds(436f, 94f, 1077f, 56f)
        addProgress()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addProgress() {
        addActor(progressBar)
        progressBar.setBounds(LS.progress)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.background_loader.data,
                SpriteManager.EnumTexture.logo.data,
                SpriteManager.EnumTexture.mask.data,
                SpriteManager.EnumTexture.prog_back.data,
                SpriteManager.EnumTexture.progress.data,
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

        // Carnaval Cat
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.CarnavalCatAtlas.entries.map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.CarnavalCatTexture.entries.map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.CarnavalCatMusic.entries.map { it.data }.toMutableList())
            load()
        }

        // Treasure Snipes
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.TreasureSnipesAtlas.entries.map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.TreasureSnipesTexture.entries.map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.TreasureSnipesMusic.entries.map { it.data }.toMutableList())
            load()
        }

        // Sweet Bonanza
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.SweetBonanzaAtlas.entries.map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.SweetBonanzaTexture.entries.map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.SweetBonanzaMusic.entries.map { it.data }.toMutableList())
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
//                        progressLabel.setText("$progress%")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }


}