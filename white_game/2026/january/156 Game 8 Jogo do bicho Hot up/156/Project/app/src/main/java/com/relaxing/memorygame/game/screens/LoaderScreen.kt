package com.relaxing.memorygame.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.relaxing.memorygame.game.LibGDXGame
import com.relaxing.memorygame.game.actors.ASplashAnimal
import com.relaxing.memorygame.game.actors.progress.ASplashLoader
import com.relaxing.memorygame.game.manager.MusicManager
import com.relaxing.memorygame.game.manager.SoundManager
import com.relaxing.memorygame.game.manager.SpriteManager
import com.relaxing.memorygame.game.utils.GameColor
import com.relaxing.memorygame.game.utils.actor.animHide
import com.relaxing.memorygame.game.utils.actor.setBounds
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.advanced.AdvancedStage
import com.relaxing.memorygame.game.utils.font.FontParameter
import com.relaxing.memorygame.game.utils.font.FontParameter.CharType
import com.relaxing.memorygame.game.utils.region
import com.relaxing.memorygame.game.utils.runGDX
import com.relaxing.memorygame.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.relaxing.memorygame.game.utils.Layout.Splash as LS

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val parameter = FontParameter()

    private val progressLabel  by lazy { Label("", Label.LabelStyle(fontRegular.generateFont(parameter.setCharacters(CharType.NUMBERS.chars+"LOADING%.").setSize(32)), GameColor.yellow)) }
    private val progressLoader by lazy { ASplashLoader(this@LoaderScreen) }
    private val animal         by lazy { ASplashAnimal(this@LoaderScreen) }

    override fun show() {
        loadSplashAssets()
        setBackgrounds(game.splashAssets.BACGROUND.region)
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
        addAnimal()
        addLoader()
        addProgress()

        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addLoader() {
        addActor(progressLoader)
        progressLoader.setBounds(LS.loader)
        progressLoader.setProgressPercent(0f)
    }

    private fun AdvancedStage.addProgress() {
        addActor(progressLabel)
        progressLabel.apply {
            setBounds(LS.progress)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addAnimal() {
        addAndFillActor(animal)
        animal.apply {
            animShowAnimal()
            animAnimateAnimal()
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.SPLASH.data)
            loadAtlas()
            loadableTextureList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND.data,
                SpriteManager.EnumTexture.LOAD_MASK.data,
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
                    runGDX {
                        progressLoader.setProgressPercent(progress.toFloat())
                        progressLabel.setText("LOADING $progress...")
                    }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    delay((15..30).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            game.musicUtil.apply { music = MUSIC.apply { isLooping = true } }

            progressLoader.animHide(0.3f)
            animal.animToStart {
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }

        }
    }


}