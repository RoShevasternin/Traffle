package com.shoote.maniapink.game.box2d.bodiesGroup

import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.shoote.maniapink.game.box2d.AbstractBody
import com.shoote.maniapink.game.box2d.AbstractBodyGroup
import com.shoote.maniapink.game.box2d.AbstractJoint
import com.shoote.maniapink.game.box2d.BodyId
import com.shoote.maniapink.game.box2d.bodies.BBall
import com.shoote.maniapink.game.box2d.bodies.BBomb
import com.shoote.maniapink.game.box2d.bodies.BHorizontal
import com.shoote.maniapink.game.box2d.bodies.standart.BDynamic
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.WIDTH_UI
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.runGDX
import com.shoote.maniapink.game.utils.scaledToB2
import com.shoote.maniapink.util.OneTime
import com.shoote.maniapink.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BGBomb(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 141f)

    // Body
    val bBomb   = BBomb(screenBox2d)
    val bSensor = BDynamic(screenBox2d)

    // Joint
    private val jWeld = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        createB_Bomb()
        createB_Sensor()

        jWeld.create(WeldJointDef().apply {
            bodyA = bBomb.body
            bodyB = bSensor.body
        })
    }

    // Create Body ---------------------------------------------------

    private fun createB_Bomb() {
        bBomb.apply {
            id = BodyId.BOMB
            collisionList.addAll(arrayOf(BodyId.BALL, BodyId.BORDERS))

            beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, _ ->
                if (enemy is BBall) {
                    runGDX { createJ_DistanceByBalls(bBomb, enemy) }
                }
            })
        }
        createBody(bBomb, 0f, 0f, 141f, 141f)
    }

    private val listSensorBall = mutableListOf<BBall>()

    private fun createB_Sensor() {
        bSensor.apply {
            id = BodyId.BOMB_SENSOR
            collisionList.add(BodyId.BALL)

            bodyDef.gravityScale = 0f
            fixtureDef.isSensor  = true
            fixtureDef.density   = 0.01f

            beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, _ ->
                if (enemy is BBall) {
                    log("eee")
                    listSensorBall.add(enemy)
                }
            })
        }
        createBody(bSensor, -185f, -185f, 510f, 510f)
    }

    // Logic -----------------------------------------------------------------

    fun setGravity(value: Float) {
        bodyList.onEach { it.body?.gravityScale = value }
    }


    private var oneTime = OneTime()

    private fun createJ_DistanceByBalls(ballA: BBomb, ballB: BBall) {
        oneTime.use {
            if (ballA.body == null || ballB.body == null) {
                log("Error Bomb: createJ_DistanceByBalls | ballA.body = ${ballA.body} | ballB.body = ${ballB.body}")
                return@use
            }

            AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d).create(DistanceJointDef().apply {
                bodyA = ballA.body
                bodyB = ballB.body

                length = 141f.scaled.scaledToB2
            })

            coroutine?.launch {
                delay(300)
                runGDX {
                    screenBox2d.game.soundUtil.apply { play(bomb) }
                    destroy()
                    listSensorBall.onEach { it.destroy() }
                }
            }

        }
    }

}