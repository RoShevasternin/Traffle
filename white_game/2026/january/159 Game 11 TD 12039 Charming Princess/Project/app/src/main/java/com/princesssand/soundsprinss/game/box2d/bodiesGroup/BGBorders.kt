package com.princesssand.soundsprinss.game.box2d.bodiesGroup

import com.princesssand.soundsprinss.game.box2d.AbstractBodyGroup
import com.princesssand.soundsprinss.game.box2d.BodyId
import com.princesssand.soundsprinss.game.box2d.bodies.BHorizontal
import com.princesssand.soundsprinss.game.box2d.bodies.BVertical
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 1440f

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
                BodyId.DYNAMIC, BodyId.BALL
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 15f, 780f, 1410f, 15f)
        createBody(bDown, 15f, 7f, 1410f, 15f)
    }

    private fun createVertical() {
        createBody(bLeft, 0f, 0f, 15f, 800f)
        createBody(bRight, 1425f, 0f, 15f, 800f)
    }

}