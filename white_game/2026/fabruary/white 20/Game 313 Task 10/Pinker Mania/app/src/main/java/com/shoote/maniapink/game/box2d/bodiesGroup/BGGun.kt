package com.shoote.maniapink.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.shoote.maniapink.game.box2d.AbstractBodyGroup
import com.shoote.maniapink.game.box2d.AbstractJoint
import com.shoote.maniapink.game.box2d.BodyId
import com.shoote.maniapink.game.box2d.bodies.BBall
import com.shoote.maniapink.game.box2d.bodies.BBomb
import com.shoote.maniapink.game.box2d.bodies.BGun
import com.shoote.maniapink.game.box2d.bodies.standart.BDynamic
import com.shoote.maniapink.game.box2d.bodies.standart.BStatic
import com.shoote.maniapink.game.utils.*
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class BGGun(
    override val screenBox2d: AdvancedBox2dScreen,
    val bStatic: BStatic,
) : AbstractBodyGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 267f)

    // Body
    val bGun        = BGun(screenBox2d)
    val bTargetBall = BDynamic(screenBox2d)

    // Joint
    val jRevolute = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    val jWeld     = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    // Field
    private var isShot   = true
    var blockPreparation = {}

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        createB_Gun()
        createB_TargetBall()

        createJ_Gun()
        createJ_TargetBall()

        screenBox2d.stageBox2d.addListener(getInputListener())

        blockPreparation()
    }

    // Create Body ---------------------------------------------------

    private fun createB_Gun() {
        bGun.apply {
            id = BodyId.GUN
            collisionList.addAll(arrayOf(BodyId.BALL))

        }
        createBody(bGun, 0f, 0f, 267f, 349f)
    }

    private fun createB_TargetBall() {
        createBody(bTargetBall, 63f, 153f, 141f, 141f)
    }

    // Create Joint -------------------------------------------------------

    private fun createJ_Gun() {
        jRevolute.create(RevoluteJointDef().apply {
            bodyA = bStatic.body
            bodyB = bGun.body

            localAnchorA.set(Vector2(567f, 202f).subCenter(bodyA))

            referenceAngle = -90f * DEGTORAD
            enableLimit    = true
            lowerAngle     = 89.95f * DEGTORAD
            upperAngle     = 90.05f * DEGTORAD
        })
    }

    private fun createJ_TargetBall() {
        jWeld.create(WeldJointDef().apply {
            bodyA = bGun.body
            bodyB = bTargetBall.body

            localAnchorA.set(Vector2(133f, 233f).subCenter(bodyA))
        })
    }

    // Logic -------------------------------------------------------

    private var currentBBall : BBall?  = null
    private var currentBGBomb: BGBomb? = null

    fun preparationBall(bBall: BBall) {
        currentBBall = bBall
        bBall.actor?.let { bGun.preparation(it.region) }
    }

    fun preparationBomb(bgBomb: BGBomb) {
        currentBBall?.destroy()
        currentBBall = null

        currentBGBomb = bgBomb
        bGun.preparation(screenBox2d.game.all.bomb.region)
    }

    private fun shot(angle: Float) {
        isShot = false
        val impulseStrength = 20f  // Визначаємо силу імпульсу

        val currentItem = currentBBall ?: currentBGBomb?.bBomb

        currentItem?.body?.let { currentBallBody ->
            bTargetBall.body?.let { targetBallBody ->
                // Встановлюємо початкову позицію кулі
                currentBallBody.setTransform(targetBallBody.position, targetBallBody.angle)

                // Розраховуємо вектор імпульсу на основі кута
                val impulseX = impulseStrength * cos(angle)
                val impulseY = impulseStrength * sin(angle)

                if (currentItem is BBomb) currentBGBomb?.setGravity(1f) else currentBallBody.gravityScale = 1f

                // Застосовуємо імпульс для руху кулі
                currentBallBody.applyLinearImpulse(Vector2(impulseX, impulseY), currentBallBody.worldCenter, true)
            }
        }
        blockPreparation()

        coroutine?.launch {
            delay(150)
            isShot = true
        }
    }

    private var gunAngle        = 1.57f
    private var tmpVectorImpuls = Vector2()

    fun getImpulse(): Vector2 {
        // Розраховуємо вектор імпульсу на основі кута
        val impulseX = 20f * cos(gunAngle)
        val impulseY = 20f * sin(gunAngle)

        return tmpVectorImpuls.set(impulseX, impulseY)
    }

    fun getStartPosition(): Vector2 = bTargetBall.body?.position ?: tmpVectorImpuls

    // Listener -------------------------------------------------------

    private fun getInputListener() = object : InputListener() {

        val touchPointInBox = Vector2()
        val tmpVector2      = Vector2()
        var targetAngle     = 1.57f
        var resultAngle     = 1.57f

        var timeTouchDown = 0L

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            if (pointer != 0) return true
            if (isShot.not()) return false

            playSound_TouchDown()
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            if (pointer != 0) return

            // Обчислюємо цільовий кут на основі точки
            touchPointInBox.set(tmpVector2.set(x, y).scaledToB2)

            targetAngle = atan2(
                touchPointInBox.y - bGun.body!!.position.y,
                touchPointInBox.x - bGun.body!!.position.x
            )

            if ((targetAngle * RADTODEG).toInt() in 30..150) {
                // Встановлюємо ліміти на RevoluteJoint навколо цільового кута
                resultAngle = targetAngle
                jRevolute.joint?.setLimits(resultAngle - 0.005f, resultAngle + 0.005f)

                gunAngle = resultAngle
            }
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (pointer != 0) return
            playSound_TouchUp()

//            log("angle Gun: ${resultAngle * RADTODEG} | $resultAngle")
            shot(resultAngle)
        }


        private fun playSound_TouchDown() {
            if (currentTimeMinus(timeTouchDown) >= 100) {
                screenBox2d.game.soundUtil.apply { play(load_of_big_gun_fun, 0.6f) }
                timeTouchDown = System.currentTimeMillis()
            }
        }

        private fun playSound_TouchUp() {
            if (currentTimeMinus(timeTouchDown) >= 250) screenBox2d.game.soundUtil.apply { play(shot_of_big_gun_fun) }
        }

    }

}