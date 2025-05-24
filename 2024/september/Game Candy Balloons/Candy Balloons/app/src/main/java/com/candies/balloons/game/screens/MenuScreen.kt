package com.candies.balloons.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.utils.*
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.isPrivacyPolicy
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true
    }

    private val candyImg = Image(game.splash.candy)

    private val btnHowToPlay   = AButton(this, AButton.Static.Type.HowToPlay)
    private val btnPlay        = AButton(this, AButton.Static.Type.Play)
    private val btnSettings    = AButton(this, AButton.Static.Type.Settings)
    private val btnLeaderboard = AButton(this, AButton.Static.Type.Leaderboard)
    private val btnAchievement = AButton(this, AButton.Static.Type.Achievement)
    private val btnPrivacy     = AButton(this, AButton.Static.Type.Privacy)
    private val btnExit        = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = CANDIS.apply {
                isLooping = true
                volumeLevelFlow.value = 25f
            } }
        }

        stageUI.root.animHide()
        setBackBackground(game.all.LIGHT.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgCandy()
                addBtns()
            }
        }
    }

    private fun AdvancedStage.addImgCandy() {
        addActor(candyImg)
        candyImg.setBounds(592f, 1343f, 479f, 577f)
        candyImg.addAction(
            Actions.forever(
            Actions.sequence(
                Actions.moveBy(0f, -60f, 0.4f, Interpolation.sineIn),
                Actions.moveBy(0f, 60f, 0.4f, Interpolation.sineOut),
            )
        ))
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnHowToPlay, btnPlay, btnSettings, btnLeaderboard, btnAchievement, btnPrivacy, btnExit)
        btnHowToPlay.apply {
            setBounds(38f, 1742f, 445f, 121f, )
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(HowToPlayScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnPlay.apply {
            setBounds(178f, 1178f, 725f,341f, )
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(PlayScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnSettings.apply {
            setBounds(178f, 890f, 725f,276f, )
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnLeaderboard.apply {
            setBounds(178f, 590f, 725f, 250f, )
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(LeaderboardScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnAchievement.apply {
            setBounds(178f, 300f, 725f, 250f, )
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(AchivmentsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnExit.apply {
            setBounds(731f, 34f, 293f, 150f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
        btnPrivacy.apply {
            setBounds(82f, 60f, 340f, 96f)
            setOnClickListener {
                isPrivacyPolicy = true
                game.activity.showUrl("https://sites.google.com/view/cb-policy/home")
            }
        }
    }

}