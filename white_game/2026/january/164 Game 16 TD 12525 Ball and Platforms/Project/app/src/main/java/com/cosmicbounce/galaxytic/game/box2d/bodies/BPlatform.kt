package com.cosmicbounce.galaxytic.game.box2d.bodies

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.cosmicbounce.galaxytic.game.actors.AImage
import com.cosmicbounce.galaxytic.game.box2d.AbstractBody
import com.cosmicbounce.galaxytic.game.box2d.AbstractJoint
import com.cosmicbounce.galaxytic.game.box2d.BodyId
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.actor.animShow
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedBox2dScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedGroup
import com.cosmicbounce.galaxytic.game.utils.toB2
import java.util.concurrent.atomic.AtomicBoolean

class BPlatform(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "panel"
    override val bodyDef    = BodyDef().apply {
        fixedRotation = true
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 10_000f
        friction    = 0.5f
        restitution = 0.2f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.bounce)

    var isOnStart = AtomicBoolean(true)


    private val bItem = BItem(screenBox2d)
    private val jWeld = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    fun createItem() {
        bItem.create(position.add(44f, 65f), Vector2(90f, 90f))
        bItem.platfaramar = this

        bItem.apply {
            id = BodyId.ITEM
            collisionList.add(BodyId.BALL)
        }

        jWeld.create(WeldJointDef().apply {
            bodyA = body
            bodyB = bItem.body
            localAnchorA.set(Vector2(89f, 110f).toB2)
        })
    }

    fun showItem() {
        bItem.isOnStart.set(true)

        bItem.id = BodyId.ITEM
        (bItem.actor as AImage).apply {
            drawable = TextureRegionDrawable(screenBox2d.game.all.items.random())
            animShow()
        }
    }

    fun hideItem() {
        bItem.id = BodyId.NONE
        bItem.actor?.animHide(TIME_ANIM)
    }

}