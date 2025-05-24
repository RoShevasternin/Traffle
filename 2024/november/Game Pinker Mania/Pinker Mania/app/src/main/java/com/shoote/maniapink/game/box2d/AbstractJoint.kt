package com.shoote.maniapink.game.box2d

import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.JointDef
import com.shoote.maniapink.game.utils.GColor
import com.shoote.maniapink.game.utils.JOINT_WIDTH
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.scaledToUI
import com.shoote.maniapink.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AbstractJoint<T : Joint, TD : JointDef>(val screenBox2d: AdvancedBox2dScreen): Destroyable {

    var coroutine: CoroutineScope? = null
        private set
    var joint: T? = null
        private set
    var jointDef: TD? = null
        private set

    private val colorJoint = GColor.background //joint.cpy()

    fun create(jointDef: TD) {
        if (joint == null) {
            this.jointDef = jointDef
            joint         = screenBox2d.worldUtil.world.createJoint(jointDef).apply { userData = this@AbstractJoint } as T
            coroutine     = CoroutineScope(Dispatchers.Default)
        }
    }

    override fun destroy() {
        if (joint != null) {
            cancelCoroutinesAll(coroutine)
            coroutine = null
            jointDef  = null

            screenBox2d.worldUtil.world.destroyJoint(joint)
            joint = null
        }
    }

    fun drawJoint(alpha: Float) = joint?.run { screenBox2d.drawerUtil.drawer.line(anchorA.scaledToUI, anchorB.scaledToUI, colorJoint.apply { a = alpha }, JOINT_WIDTH) }


}