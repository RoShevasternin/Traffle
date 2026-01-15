package com.bounceroval.mazedackq.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.bounceroval.mazedackq.game.actors.AImage
import com.bounceroval.mazedackq.game.box2d.AbstractBody
import com.bounceroval.mazedackq.game.screens.PrePlayScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedBox2dScreen
import com.bounceroval.mazedackq.game.utils.advanced.AdvancedGroup

class BPlane(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "plane"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = (10..80).random() / 10f
        friction    = (10..80).random() / 100f
        restitution = (10..80).random() / 100f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.plans[PrePlayScreen.AVIA_INDEX])

}