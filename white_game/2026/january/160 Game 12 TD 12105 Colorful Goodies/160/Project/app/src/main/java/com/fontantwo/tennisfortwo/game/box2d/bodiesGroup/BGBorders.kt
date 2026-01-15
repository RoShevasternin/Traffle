package com.fontantwo.tennisfortwo.game.box2d.bodiesGroup

import com.fontantwo.tennisfortwo.game.box2d.AbstractBodyGroup
import com.fontantwo.tennisfortwo.game.box2d.BodyId
import com.fontantwo.tennisfortwo.game.box2d.bodies.BHorizontal
import com.fontantwo.tennisfortwo.game.box2d.bodies.BVertical
import com.fontantwo.tennisfortwo.game.utils.WIDTH_UI
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = WIDTH_UI

    val bTop   = BHorizontal(screenBox2d)
    val bDown  = BHorizontal(screenBox2d)
    val bLeft  = BVertical(screenBox2d)
    val bRight = BVertical(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()

        createHorizontal()
        createVertical()
    }


    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, bDown, bLeft, bRight).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(
                BodyId.USER_A,
                BodyId.USER_B,
                BodyId.BALL,
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 98f, 827f, 1180f, 67f)
        createBody(bDown, 98f, 46f, 1180f, 67f)
    }

    private fun createVertical() {
        createBody(bLeft, 57f, 38f, 40f, 872f)
        createBody(bRight, 1280f, 38f, 40f, 872f)
    }

}