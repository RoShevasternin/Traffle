package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.enable
import com.pixe.lkicko.perlin.game.utils.font.FontParameter

class PlayScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var STATIC_counterLVL = 1
    }

    private val fontParameter = FontParameter()
    private val font250       = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(250))

    private val ls250 = LabelStyle(font250, GColor.black)

    // Actors
    private val aBack  = Actor()
    private val lblLVL = Label(STATIC_counterLVL.toString(), ls250)
    private val aLeft  = Actor()
    private val aRight = Actor()
    private val aPlay  = Actor()

    private val imgClosed = Image(game.all.closed)

    // Field

    private val availableLVL = game.dataStore.lvl


    override fun show() {
        setUIBackground(game.all.background_2)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addLblLVL()
        addLeftAndRight()
        addPlay()

        addActor(imgClosed)
        imgClosed.apply {
            setBounds(365f, 876f, 360f, 474f)
            this.animHide()
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(aBack)
        aBack.setBounds(833f, 1811f, 218f, 90f)

        aBack.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addLblLVL() {
        addActor(lblLVL)
        lblLVL.setBounds(537f, 858f, 103f, 313f)
    }

    private fun AdvancedStage.addLeftAndRight() {
        addActors(aLeft, aRight)
        aLeft.setBounds(28f, 812f, 218f, 90f)
        aRight.setBounds(833f, 1212f, 218f, 90f)

        aLeft.setOnClickListener(game.soundUtil) {
            if (STATIC_counterLVL - 1 >= 1) {
                STATIC_counterLVL -= 1
                lblLVL.setText(STATIC_counterLVL)

                if (availableLVL >= STATIC_counterLVL) {
                    imgClosed.animHide(TIME_ANIM)
                    aPlay.enable()
                }
            }
        }
        aRight.setOnClickListener(game.soundUtil) {
            if (STATIC_counterLVL + 1 <= 25) {
                STATIC_counterLVL += 1
                lblLVL.setText(STATIC_counterLVL)

                if (availableLVL < STATIC_counterLVL) {
                    imgClosed.animShow(TIME_ANIM)
                    aPlay.disable()
                }
            }
        }
    }

    private fun AdvancedStage.addPlay() {
        addActor(aPlay)
        aPlay.setBounds(437f, 670f, 596f, 246f)

        aPlay.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(GameScreen::class.java.name, PlayScreen::class.java.name)
            }
        }
    }



}