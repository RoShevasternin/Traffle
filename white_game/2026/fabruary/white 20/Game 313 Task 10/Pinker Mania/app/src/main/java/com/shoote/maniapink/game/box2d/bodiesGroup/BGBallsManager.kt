package com.shoote.maniapink.game.box2d.bodiesGroup

import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.shoote.maniapink.game.box2d.AbstractBodyGroup
import com.shoote.maniapink.game.box2d.AbstractJoint
import com.shoote.maniapink.game.box2d.BodyId
import com.shoote.maniapink.game.box2d.BodyId.BALL
import com.shoote.maniapink.game.box2d.BodyId.BOMB
import com.shoote.maniapink.game.box2d.BodyId.BOMB_SENSOR
import com.shoote.maniapink.game.box2d.BodyId.BORDERS
import com.shoote.maniapink.game.box2d.BodyId.FINISH
import com.shoote.maniapink.game.box2d.WorldContactListener
import com.shoote.maniapink.game.box2d.bodies.BBall
import com.shoote.maniapink.game.box2d.bodies.BHorizontal
import com.shoote.maniapink.game.utils.SizeScaler
import com.shoote.maniapink.game.utils.WIDTH_UI
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.runGDX
import com.shoote.maniapink.game.utils.scaledToB2
import com.shoote.maniapink.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BGBallsManager(
    override val screenBox2d: AdvancedBox2dScreen,
    val bHorizontal: BHorizontal,
) : AbstractBodyGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WIDTH_UI)

    var blockDestroyBall = {}

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        handleCollideBalls()
        generateBBalls_7()
    }

    // Logic ----------------------------------------------------------

    private fun generateBBalls_7() {
        val arrCollision = arrayOf(BALL, BORDERS, FINISH, BOMB, BOMB_SENSOR)

        var nx = 0f
        repeat(7) {
            BBall(screenBox2d, BBall.Type.entries.random()).also { ball ->
                ball.apply {
                    id = BALL
                    collisionList.addAll(arrCollision)

                    fixtureDef.apply {
                        restitution = 0f
                        friction    = 1f
                    }
                }
                createBody(ball, nx, 0f, 141f, 141f)
                nx += 141f.scaled

                ball.blockDestroy = { blockDestroyBall() }

                createJ_DistanceBallByHorizontal(ball, bHorizontal)
            }
        }
    }

    fun updateBalls() {
        bHorizontal.body?.jointList?.let { arr ->
            arr.onEach { jointEdge ->
                runGDX {
                    (jointEdge.joint.userData as? AbstractJoint<*, *>)?.destroy()

                    if (arr.size == 0) {
                        coroutine?.launch {
                            delay(300)
                            runGDX {
                                generateBBalls_7()
                            }
                        }
                    }

                }
            }
        }
    }

    private fun handleCollideBalls() {
        screenBox2d.worldUtil.contactListener.beginContactBlockArray.add(WorldContactListener.ContactBlock { bodyA, bodyB ->
            if (bodyA is BBall && bodyB is BBall) {
                val isConnected = bodyA.body?.jointList?.any { it.other == bodyB.body } == true
                if (
                    isConnected.not()    &&
                    bodyA.isTarget.not() && bodyB.isTarget.not()
                ) {
                    runGDX { createJ_DistanceByBalls(bodyA, bodyB) }
                }

                if (bodyA.typeBall == bodyB.typeBall     ||
                    bodyA.typeBall == BBall.Type.Rainbow || bodyB.typeBall == BBall.Type.Rainbow
                ) {
                    if (bodyA.joinedBallUnique.contains(bodyB).not()) {
                        bodyA.joinedBallUnique.addAll(bodyB.joinedBallUnique)
                        bodyB.joinedBallUnique.addAll(bodyA.joinedBallUnique)

                        runGDX { createJ_DistanceByBalls(bodyA, bodyB) }

                        if (bodyA.joinedBallUnique.any { it.isTarget }) {
                            coroutine?.launch {
                                delay(150)
                                runGDX {
                                    if (bodyA.joinedBallUnique.size >= 3) bodyA.joinedBallUnique.onEach { it.destroy() }
                                }
                            }
                        }
                    }
                }

            }
        })
    }

    private fun createJ_DistanceByBalls(ballA: BBall, ballB: BBall) {
        if (ballA.body == null || ballB.body == null) {
            log("Error: createJ_DistanceByBalls | ballA.body = ${ballA.body} | ballB.body = ${ballB.body}")
            return
        }

        AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d).create(DistanceJointDef().apply {
            bodyA = ballA.body
            bodyB = ballB.body

            length = 141f.scaled.scaledToB2
        })
    }

    private fun createJ_DistanceBallByHorizontal(ball: BBall, horizontal: BHorizontal) {
        if (ball.body == null || horizontal.body == null) {
            log("Error: createJ_DistanceBallByHorizontal | ball.body = ${ball.body} | horizontal.body = ${horizontal.body}")
            return
        }

        AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d).create(DistanceJointDef().apply {
            bodyA = horizontal.body
            bodyB = ball.body

            collideConnected = true

            length = 70.5f.scaled.scaledToB2
            localAnchorA.set(bodyB.position.x, 0f)
        })
    }

}