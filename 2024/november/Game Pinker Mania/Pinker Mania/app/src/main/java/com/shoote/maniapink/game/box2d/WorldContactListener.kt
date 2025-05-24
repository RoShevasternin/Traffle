package com.shoote.maniapink.game.box2d

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.utils.Array
import com.shoote.maniapink.game.manager.util.SoundUtil
import com.shoote.maniapink.game.utils.currentTimeMinus
import com.shoote.maniapink.util.log

class WorldContactListener: ContactListener {

    private var timeContactBorders = 0L

    private val Contact.abstractBodyA get() = (fixtureA.body.userData as AbstractBody<*>)
    private val Contact.abstractBodyB get() = (fixtureB.body.userData as AbstractBody<*>)

    private val tmpArray = Array<AbstractBody<*>>().apply { setSize(2) }
    private lateinit var soundUtil: SoundUtil

    var beginContactBlockArray = Array<ContactBlock>()
    var endContactBlockArray   = Array<ContactBlock>()

    override fun beginContact(contact: Contact) {
        with(contact) {
            playSound(abstractBodyA, abstractBodyB)
            abstractBodyA.beginContact(abstractBodyB, contact)
            abstractBodyB.beginContact(abstractBodyA, contact)

            beginContactBlockArray.onEach { it.block(abstractBodyA, abstractBodyB) }
        }
    }

    override fun endContact(contact: Contact) {
        with(contact) {
            abstractBodyA.endContact(abstractBodyB, contact)
            abstractBodyB.endContact(abstractBodyA, contact)

            endContactBlockArray.onEach { it.block(abstractBodyA, abstractBodyB) }
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold?) {
//        oldManifold?.apply {
//            log(""" preSolve:
//                ${this.type}
//                ${this.points.joinToString()}
//                ${this.localPoint}
//                ${this.pointCount}
//                ${this.localNormal}
//            """)
//        }
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse?) {
//        impulse?.apply {
//            log(""" postSolve:
//                ${this.count}
//                ${this.normalImpulses.sum()}
//                ${this.tangentImpulses.joinToString()}
//            """)
//        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun playSound(bodyA: AbstractBody<*>, bodyB: AbstractBody<*>) {
        tmpArray[0] = bodyA
        tmpArray[1] = bodyB

        soundUtil = bodyA.screenBox2d.game.soundUtil

        if (tmpArray.all { it.fixtureDef.isSensor.not() }) {
            when {
                tmpArray.any { it.id == BodyId.BORDERS }
                -> if (currentTimeMinus(timeContactBorders) >= 220) {
                    soundUtil.apply { play(wooden, 0.5f) }
                    timeContactBorders = System.currentTimeMillis()
                }
            }
        }

    }

    fun interface ContactBlock { fun block(bodyA: AbstractBody<*>, bodyB: AbstractBody<*>) }

}