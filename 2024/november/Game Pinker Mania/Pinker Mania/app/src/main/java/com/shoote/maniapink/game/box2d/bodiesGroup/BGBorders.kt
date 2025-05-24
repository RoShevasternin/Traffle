package com.shoote.maniapink.game.box2d.bodiesGroup

import com.shoote.maniapink.game.box2d.AbstractBodyGroup
import com.shoote.maniapink.game.box2d.BodyId
import com.shoote.maniapink.game.box2d.BodyId.BALL
import com.shoote.maniapink.game.box2d.BodyId.BOMB
import com.shoote.maniapink.game.box2d.bodies.BHorizontal
import com.shoote.maniapink.game.box2d.bodies.BVertical
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.WIDTH_UI
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    val bTop   = BHorizontal(screenBox2d)
//    val bDown  = BHorizontal(screenBox2d)
    val bLeft  = BVertical(screenBox2d)
    val bRight = BVertical(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()

        createHorizontal()
        createVertical()
    }


    // Init ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, /*bDown*/ bLeft, bRight).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(BALL, BOMB))
        } }
    }

    // Create Body ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 0f, 1613f, WIDTH_UI, 50f)
//        createBody(bDown, 0f, 0f, WIDTH_UI, 50f)
    }

    private fun createVertical() {
        createBody(bLeft, -50f, 0f, 50f, 1774f)
        createBody(bRight, WIDTH_UI, 0f, 50f, 1774f)
    }

}