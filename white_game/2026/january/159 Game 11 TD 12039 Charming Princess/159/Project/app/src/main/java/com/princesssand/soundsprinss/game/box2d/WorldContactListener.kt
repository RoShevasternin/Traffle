package com.princesssand.soundsprinss.game.box2d

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.utils.Array
import com.princesssand.soundsprinss.game.manager.util.SoundUtil
import com.princesssand.soundsprinss.game.utils.currentTimeMinus

class WorldContactListener: ContactListener {

    private var timeContactBorders = 0L

    private val Contact.abstractBodyA get() = (fixtureA.body.userData as AbstractBody)
    private val Contact.abstractBodyB get() = (fixtureB.body.userData as AbstractBody)

    private val tmpArray = Array<AbstractBody>().apply { setSize(2) }
    private lateinit var soundUtil: SoundUtil

    //var beginContactBlockArray = Array<ContactBlock>()
    //var endContactBlockArray   = Array<ContactBlock>()

    override fun beginContact(contact: Contact) {
        with(contact) {
            playSound(abstractBodyA, abstractBodyB)
            abstractBodyA.beginContact(abstractBodyB, contact)
            abstractBodyB.beginContact(abstractBodyA, contact)
        }
    }

    override fun endContact(contact: Contact) {
        with(contact) {
            abstractBodyA.endContact(abstractBodyB, contact)
            abstractBodyB.endContact(abstractBodyA, contact)
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold?) {}

    override fun postSolve(contact: Contact, impulse: ContactImpulse?) {}

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun playSound(bodyA: AbstractBody, bodyB: AbstractBody) {
        tmpArray[0] = bodyA
        tmpArray[1] = bodyB

        soundUtil = bodyA.screenBox2d.game.soundUtil

        if (tmpArray.all { it.fixtureDef.isSensor.not() }) {
            when {
                tmpArray.any { it.id == BodyId.BORDERS }
                -> if (currentTimeMinus(timeContactBorders) >= 100) {
                    soundUtil.apply { play(border) }
                    timeContactBorders = System.currentTimeMillis()
                }
            }
        }

    }

    //fun interface ContactBlock { fun block(bodyA: AbstractBody, bodyB: AbstractBody) }

}