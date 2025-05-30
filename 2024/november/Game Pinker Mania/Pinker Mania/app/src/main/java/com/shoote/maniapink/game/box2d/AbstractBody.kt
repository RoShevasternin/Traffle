package com.shoote.maniapink.game.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.shoote.maniapink.game.utils.RADTODEG
import com.shoote.maniapink.game.utils.actor.setBounds
import com.shoote.maniapink.game.utils.actor.setOrigin
import com.shoote.maniapink.game.utils.actor.setPosition
import com.shoote.maniapink.game.utils.advanced.AdvancedBox2dScreen
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.scaledToB2
import com.shoote.maniapink.game.utils.scaledToUI
import com.shoote.maniapink.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AbstractBody<T: AdvancedGroup?>: Destroyable {

    abstract val screenBox2d: AdvancedBox2dScreen
    abstract val name       : String
    abstract val bodyDef    : BodyDef
    abstract val fixtureDef : FixtureDef

    open var actor: T? = null
    open val collisionList  = mutableListOf<String>()

    open var originalId: String = BodyId.NONE
    open var id        : String = BodyId.NONE

    private val tmpVector2 = Vector2()

    val size       = Vector2()
    val position   = Vector2()

    var body: Body? = null
        private set
    var scale = 0f
        private set
    var center = tmpVector2
        private set
    var coroutine: CoroutineScope? = null
        private set

    var beginContactBlockArray = Array<ContactBlock>()
    var endContactBlockArray   = Array<ContactBlock>()
    var renderBlockArray       = Array<RenderBlock>()

    var blockDestroy = {}

    var isDestroyActor = true

    open fun render(deltaTime: Float) {
        renderBlockArray.onEach { it.block(deltaTime) }
        transformActor()
    }

    open fun beginContact(contactBody: AbstractBody<*>, contact: Contact) = beginContactBlockArray.onEach { it.block(contactBody, contact) }

    open fun endContact(contactBody: AbstractBody<*>, contact: Contact) = endContactBlockArray.onEach { it.block(contactBody, contact) }

    override fun destroy() {
        if (body != null) {
            id = BodyId.NONE

            cancelCoroutinesAll(coroutine)
            coroutine = null

            if (isDestroyActor) {
                actor?.dispose()
                actor = null
            }

            body?.jointList?.map { (it.joint.userData as AbstractJoint<out Joint, out JointDef>) }?.destroyAll()

            screenBox2d.worldUtil.world.destroyBody(body)
            body = null

            collisionList.clear()
            renderBlockArray.clear()
            beginContactBlockArray.clear()
            endContactBlockArray.clear()

            blockDestroy()
        }
    }

    fun create(x: Float, y: Float, w: Float, h: Float) {
        if (body == null) {
            position.set(x, y)
            size.set(w, h)
            scale  = size.x.scaledToB2
            center = screenBox2d.worldUtil.bodyEditor.getOrigin(name, scale)

            bodyDef.position.set(tmpVector2.set(position).scaledToB2.add(center))

            body = screenBox2d.worldUtil.world.createBody(bodyDef).apply { userData = this@AbstractBody }
            screenBox2d.worldUtil.bodyEditor.attachFixture(body!!, name, fixtureDef, scale)

            coroutine = CoroutineScope(Dispatchers.Default)
            addActor()

            isDestroyActor = true
        }
    }

    fun create(position: Vector2, size: Vector2) {
        create(position.x, position.y, size.x, size.y)
    }

    private fun addActor() {
        actor?.apply {
            screenBox2d.stageBox2d.addActor(this)
            setBounds(position, size)
        }
    }

    private fun transformActor() {
        body?.let { b ->
            actor?.apply {
                setPosition(tmpVector2.set(b.position).sub(center).scaledToUI)
                setOrigin(tmpVector2.set(center).scaledToUI)
                rotation = b.angle * RADTODEG
            }
        }
    }

    fun setNoneId() {
        id = BodyId.NONE
    }

    fun setOriginalId() {
        id = originalId
    }

    // ---------------------------------------------------
    // SAM
    // ---------------------------------------------------

    fun interface ContactBlock { fun block(body: AbstractBody<*>, contact: Contact) }
    fun interface RenderBlock { fun block(deltaTime: Float) }

}