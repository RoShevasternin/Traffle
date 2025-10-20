package com.cargodance.liftoffer.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.cargodance.liftoffer.game.actors.AImage
import com.cargodance.liftoffer.game.box2d.AbstractBody
import com.cargodance.liftoffer.game.box2d.BodyId
import com.cargodance.liftoffer.game.utils.advanced.AdvancedBox2dScreen
import com.cargodance.liftoffer.game.utils.advanced.AdvancedGroup

class BTractor(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "tractor"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
        friction    = 0.4f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.tractor)

}