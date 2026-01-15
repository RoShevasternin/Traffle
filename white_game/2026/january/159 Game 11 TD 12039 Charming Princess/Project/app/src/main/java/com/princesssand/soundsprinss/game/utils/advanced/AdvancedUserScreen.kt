package com.princesssand.soundsprinss.game.utils.advanced

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.QueryCallback
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.princesssand.soundsprinss.game.box2d.AbstractBody
import com.princesssand.soundsprinss.game.box2d.AbstractJoint
import com.princesssand.soundsprinss.game.box2d.BodyId
import com.princesssand.soundsprinss.game.box2d.WorldUtil
import com.princesssand.soundsprinss.game.box2d.bodies.standart.BStatic
import com.princesssand.soundsprinss.game.utils.currentTimeMinus
import com.princesssand.soundsprinss.game.utils.toB2
import com.princesssand.soundsprinss.util.log

abstract class AdvancedUserScreen: AdvancedBox2dScreen(WorldUtil()) {

    // Joint
    private val jMouse by lazy { AbstractJoint<MouseJoint, MouseJointDef>(this) }

    // Body
    private val bStatic by lazy { BStatic(this) }

    // Fields
    protected val listenerForMouseJoint : InputListener by lazy { getInputListenerForMouseJoint() }

    private var maxForce     = 1000f
    private var frequencyHz  = 5.0f
    private var dampingRatio = 0.7f

    var touchDownBallBlock = {}

    override fun show() {
        super.show()

        addMouseActorsOnStageUI()
        stageUI.addListener(listenerForMouseJoint)
    }

    override fun dispose() {
        log("dispose AdvancedMouseScreen: ${this::class.java.name.substringAfterLast('.')}")
        super.dispose()
    }

    private fun addMouseActorsOnStageUI() {
        createB_Static()
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(0f, 0f,1f,1f)
    }

    // ------------------------------------------------------------------------
    // Listener
    // ------------------------------------------------------------------------

    private fun getInputListenerForMouseJoint() = object : InputListener() {

        var hitAbstractBody: AbstractBody? = null
        val touchPointInBox = Vector2()
        val tmpVector2      = Vector2()

        val callback        = QueryCallback { fixture ->
            if (
                fixture.isSensor.not() &&
                fixture.testPoint(touchPointInBox) &&
                (fixture.body?.userData as AbstractBody).id != BodyId.NONE
            ) {
                hitAbstractBody = fixture.body?.userData as AbstractBody
                return@QueryCallback false
            }
            return@QueryCallback true
        }

        var timeTouchDown = 0L

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            if (pointer != 0) return true

            touchPointInBox.set(tmpVector2.set(x, y).toB2)
            hitAbstractBody = null

            worldUtil.world.QueryAABB(callback,
                touchPointInBox.x - 0.01f,
                touchPointInBox.y - 0.01f,
                touchPointInBox.x + 0.01f,
                touchPointInBox.y + 0.01f)

            hitAbstractBody?.let {
                if (it.id == BodyId.BALL) touchDownBallBlock()

                playSound_TouchDown()

                jMouse.create(MouseJointDef().apply {
                    bodyA = bStatic.body
                    bodyB = it.body
                    collideConnected = true

                    target.set(touchPointInBox)

                    maxForce     = this@AdvancedUserScreen.maxForce * bodyB.mass
                    frequencyHz  = this@AdvancedUserScreen.frequencyHz
                    dampingRatio = this@AdvancedUserScreen.dampingRatio
                })
            }

            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            if (pointer != 0) return
            jMouse.joint?.target = tmpVector2.set(x, y).toB2
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (pointer != 0) return
            hitAbstractBody?.let {
                playSound_TouchUp()
                jMouse.destroy()
            }
        }


        private fun playSound_TouchDown() {
            if (currentTimeMinus(timeTouchDown) >= 100) {
                game.soundUtil.apply { play(touch) }
                timeTouchDown = System.currentTimeMillis()
            }
        }

        private fun playSound_TouchUp() {
            if (currentTimeMinus(timeTouchDown) >= 400) game.soundUtil.apply { play(touch) }
        }

    }

}