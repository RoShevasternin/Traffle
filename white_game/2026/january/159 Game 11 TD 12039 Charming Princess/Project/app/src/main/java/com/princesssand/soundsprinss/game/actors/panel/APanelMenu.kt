package com.princesssand.soundsprinss.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.princesssand.soundsprinss.game.actors.ATutorials
import com.princesssand.soundsprinss.game.actors.button.AButton
import com.princesssand.soundsprinss.game.screens.GameScreen
import com.princesssand.soundsprinss.game.screens.LevelsScreen
import com.princesssand.soundsprinss.game.screens.MenuScreen
import com.princesssand.soundsprinss.game.screens.RulesScreen
import com.princesssand.soundsprinss.game.screens.SettingsScreen
import com.princesssand.soundsprinss.game.utils.actor.disable
import com.princesssand.soundsprinss.game.utils.actor.enable
import com.princesssand.soundsprinss.game.utils.actor.setOnClickListener
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen

class APanelMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    private val panel   = Image(screen.game.assetsAll.panel)
    private val btns    = Image(screen.game.assetsAll.butons)
    private val exit    = AButton(screen, AButton.Static.Type.Exit)
    private val btnActorList = List(3) { Actor() }
    private val fastGameBtn  = AButton(screen, AButton.Static.Type.FastGame)
    private val bigBallBtn   = AButton(screen, AButton.Static.Type.BigBall)


    var hideBlock: (() -> Unit) -> Unit = {}

    override fun addActorsOnGroup() {
        GameScreen.isFastGame = false
        GameScreen.isBigBall  = false

        addAndFillActor(panel)
        addButtons()
        addExit()
        addFastGame()
        addBigBall()

        if (screen.game.isTutorialsUtil.isTutorials) {
            when (ATutorials.STEP) {
                ATutorials.Static.Step.MenuRules -> {
                    children.onEach { it.disable() }
                    btnActorList[0].enable()
                }
                ATutorials.Static.Step.MenuSettings -> {
                    children.onEach { it.disable() }
                    btnActorList[1].enable()
                }
                ATutorials.Static.Step.MenuLevels -> {
                    children.onEach { it.disable() }
                    btnActorList[2].enable()
                }
                else -> {}
            }
        }
    }

    // Add Actors
    private fun addButtons() {
        addActor(btns)
        btns.setBounds(361f, 77f, 162f, 276f)

        var ny = 77f
        listOf(
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
            LevelsScreen::class.java.name,
        ).onEachIndexed { index, screenName ->
            addActor(btnActorList[index].apply {
                setBounds(361f, ny, 162f, 58f)
                setOnClickListener(screen.game.soundUtil) { hideBlock {
                    if (screen.game.isTutorialsUtil.isTutorials) ATutorials.nextStep()
                    screen.game.navigationManager.navigate(screenName, screen::class.java.name)
                } }
                ny += 51+58
            })
        }
    }

    private fun addExit() {
        addActor(exit)
        exit.setBounds(687f, 25f, 76f, 58f)
        exit.setOnClickListener {
            hideBlock { screen.game.navigationManager.exit() }
        }
    }

    private fun addFastGame() {
        addActor(fastGameBtn)
        fastGameBtn.setBounds(193f, -124f, 182f, 96f)
        fastGameBtn.setOnClickListener {
            GameScreen.isFastGame = true
            hideBlock { screen.game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        }
    }

    private fun addBigBall() {
        addActor(bigBallBtn)
        bigBallBtn.setBounds(406f, -124f, 182f, 96f)
        bigBallBtn.setOnClickListener {
            GameScreen.isBigBall = true
            hideBlock { screen.game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        }
    }

}