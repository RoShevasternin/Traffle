package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.viade.bepuzzle.game.utils.actor.PosSize
import com.viade.bepuzzle.game.utils.actor.setBounds
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class ACircles(override val screen: AdvancedScreen): AdvancedGroup() {

    val listStartPosItem = listOf(
        PosSize(375f, 1511f, 305f, 305f),
        PosSize(235f, 1493f, 155f, 155f),
        PosSize(233f, 1149f, 245f, 245f),
        PosSize(616f, 737f, 382f, 382f),
        PosSize(165f, 474f, 303f, 303f),
        PosSize(40f, 34f, 390f, 390f),
        PosSize(502f, 228f, 157f, 157f),
        PosSize(776f, 113f, 245f, 245f),
    )

    override fun addActorsOnGroup() {
        val listAngle    = listOf(360f, -360f)
        val listDuration = listOf(2.2f, 3f, 4.3f, 5f, 7.5f, 10f)

        screen.game.assetsLoader.listCircles.onEachIndexed { index, tex ->
            Image(tex).also { img ->
                addActor(img)
                img.setBounds(listStartPosItem[index])
                img.setOrigin(Align.center)
                img.addAction(Actions.forever(Actions.rotateBy(listAngle.random(), listDuration.random())))
            }
        }
    }

}