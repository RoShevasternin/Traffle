package com.candies.balloons.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.AHTP
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.disable
import com.candies.balloons.game.utils.region
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.launch

class HowToPlayScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnMenu  = AButton(this, AButton.Static.Type.Dom)
    private val imgTitle = Image(game.all.htp)
    private val htp      = AHTP(this)
    private val scroll   = ScrollPane(htp)
    private val imgArrow = Image(game.all.arrow)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.DARK.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgTitle()
                addHTP()
                addArrow()
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
        imgTitle.setBounds(228f, 1592f, 624f, 170f)
    }

    private fun AdvancedStage.addHTP() {
        addActor(scroll)
        scroll.setBounds(9f, 0f, 1062f, 1538f)
    }

    private fun AdvancedStage.addArrow() {
        addActor(imgArrow)
        imgArrow.setBounds(792f, 180f, 204f, 204f)

        imgArrow.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, 60f, 0.3f, Interpolation.sineIn),
            Actions.moveBy(0f, -60f, 0.3f, Interpolation.sineOut),
        )))

        imgArrow.setOnClickListener(game.soundUtil) {
            imgArrow.disable()
            imgArrow.animHide(TIME_ANIM)
            scroll.scrollPercentY = 100f
        }
    }

}