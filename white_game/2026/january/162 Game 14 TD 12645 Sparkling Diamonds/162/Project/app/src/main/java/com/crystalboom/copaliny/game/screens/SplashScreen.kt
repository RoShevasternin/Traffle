package com.crystalboom.copaliny.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.crystalboom.copaliny.game.LibGDXGame
import com.crystalboom.copaliny.game.manager.MusicManager
import com.crystalboom.copaliny.game.manager.SoundManager
import com.crystalboom.copaliny.game.manager.SpriteManager
import com.crystalboom.copaliny.game.utils.TIME_ANIM
import com.crystalboom.copaliny.game.utils.actor.animHide
import com.crystalboom.copaliny.game.utils.advanced.AdvancedScreen
import com.crystalboom.copaliny.game.utils.advanced.AdvancedStage
import com.crystalboom.copaliny.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow = MutableStateFlow(0f)
    private var isFinishLoading = false
    private var isFinishProgress = false

    private val imgLoader by lazy { Image(game.splash.rubik) }

    override fun show() {
        loadSplashAssets()
        //setBackBackground(game.splash.SPLASH.region)
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
        addActor(imgLoader)
        imgLoader.apply {
            setBounds(222f, 358f, 281f, 239f)
            setOrigin(Align.center)

            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.sine),
                        Actions.scaleBy(0.2f, 0.2f, 0.2f, Interpolation.sine),
                    )
                )
            )
            addAction(
                Actions.forever(
                    Actions.rotateBy(-360f, 1.2f, Interpolation.linear),
                )
            )
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableAtlasList = mutableListOf(
                SpriteManager.EnumAtlas.SPLASH.data
            )
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.SPLASH.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
        game.spriteManager.initAtlas()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
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
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
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
                    if (progress == 100) isFinishProgress = true
                    //delay((10..15L).random())
                }
            }
        }
    }

    val mansList = listOf(
        Mans(1, "John", 180.0, 75.0, 30, true, "American", "Engineer", 50000.0, 10),
        Mans(2, "Alex", 175.0, 70.0, 28, false, "British", "Doctor", 60000.0, 8),
        Mans(3, "Mike", 185.0, 85.0, 32, true, "Canadian", "Athlete", 80000.0, 12),
        Mans(4, "Tom", 178.0, 80.0, 29, false, "Australian", "Teacher", 45000.0, 7),
        Mans(5, "Chris", 182.0, 78.0, 35, true, "American", "Lawyer", 90000.0, 15),
        Mans(6, "Dan", 176.0, 72.0, 27, false, "Irish", "Artist", 35000.0, 5),
        Mans(7, "Steve", 188.0, 90.0, 31, true, "German", "Scientist", 100000.0, 14),
        Mans(8, "Paul", 170.0, 68.0, 26, false, "French", "Chef", 40000.0, 6)
    )

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false
            toScreen(MenuScreen::class.java.name)
        }
    }

    data class Mans(
        val id: Int,
        val name: String,
        val height: Double,
        val weight: Double,
        val age: Int,
        val isAthlete: Boolean,
        val nationality: String,
        val occupation: String,
        val income: Double,
        val experience: Int
    )

    private fun toScreen(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}