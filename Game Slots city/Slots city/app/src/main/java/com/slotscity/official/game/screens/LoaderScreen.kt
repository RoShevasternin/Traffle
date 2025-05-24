package com.slotscity.official.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.progress.ASlotCityProgress
import com.slotscity.official.game.manager.MusicManager
import com.slotscity.official.game.manager.SoundManager
import com.slotscity.official.game.manager.SpriteManager
import com.slotscity.official.game.utils.GameColor
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.setBounds
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.font.FontParameter
import com.slotscity.official.game.utils.font.FontParameter.CharType
import com.slotscity.official.game.utils.runGDX
import com.slotscity.official.util.log
import com.slotscity.official.game.utils.Layout.Splash as LS

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

//    private val parameter = FontParameter()
//    private val progressLabel by lazy { Label("", Label.LabelStyle(fontGeneratorArial_Black.generateFont(parameter.setCharacters(CharType.NUMBERS.chars+"%").setSize(100)), GameColor.text)) }

    private val progressBar by lazy { ASlotCityProgress(this) }

    override fun show() {
        game.activity.lottie.hideLoader()
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

    override fun AdvancedStage.addActorsOnStageUI() {
        addProgress()
        isFinishAnim = true
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
                SpriteManager.EnumTexture.logo.data,
                SpriteManager.EnumTexture.logo_mask.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTexture()
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

        // Carnaval Cat
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.CarnavalCatAtlas.values().map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.CarnavalCatTexture.values().map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.CarnavalCatMusic.values().map { it.data }.toMutableList())
            load()
        }

        // Treasure Snipes
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.TreasureSnipesAtlas.values().map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.TreasureSnipesTexture.values().map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.TreasureSnipesMusic.values().map { it.data }.toMutableList())
            load()
        }

        // Sweet Bonanza
        with(game.spriteManager) {
            loadableAtlasList.addAll(SpriteManager.SweetBonanzaAtlas.values().map { it.data }.toMutableList())
            loadAtlas()
            loadableTextureList.addAll(SpriteManager.SweetBonanzaTexture.values().map { it.data }.toMutableList())
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList.addAll(MusicManager.SweetBonanzaMusic.values().map { it.data }.toMutableList())
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
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }


}