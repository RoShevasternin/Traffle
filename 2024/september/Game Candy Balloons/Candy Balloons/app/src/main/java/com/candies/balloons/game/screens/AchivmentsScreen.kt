package com.candies.balloons.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.checkbox.ACheckBox
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.region
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.launch

class AchivmentsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val collectBox = ACheckBox(this, ACheckBox.Static.Type.COLLECT)
    private val taskmasBox = ACheckBox(this, ACheckBox.Static.Type.TASKMASTER)

    private val btnMenu     = AButton(this, AButton.Static.Type.Dom)
    private val imgTitle    = Image(game.all.achi)

    private val openCount = (0..2).random()

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.LIGHT.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgTitle()
                addBoxes()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(880f, 1724f, 157f, 154f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(178f, 1551f, 724f, 183f)
    }

    private fun AdvancedStage.addBoxes() {
        addActors(collectBox, taskmasBox)
        collectBox.setBounds(102f, 1003f, 392f, 478f)
        collectBox.disable()
        taskmasBox.setBounds(587f, 1003f, 392f, 478f)
        taskmasBox.disable()

        when(openCount) {
            1 -> collectBox.check()
            2 -> {
                collectBox.check()
                taskmasBox.check()
            }
        }
    }

}