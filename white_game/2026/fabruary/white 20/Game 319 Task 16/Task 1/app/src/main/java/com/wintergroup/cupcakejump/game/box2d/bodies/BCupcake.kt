package com.wintergroup.cupcakejump.game.box2d.bodies

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.wintergroup.cupcakejump.game.actors.AImage
import com.wintergroup.cupcakejump.game.box2d.AbstractBody
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedGroup
import com.wintergroup.cupcakejump.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.wintergroup.cupcakejump.game.utils.gdxGame

class BCupcake(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        isSensor = true
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.listCupcake.random()).apply {
        addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, 15f, 0.25f, Interpolation.sine),
            Actions.moveBy(0f, -15f, 0.15f, Interpolation.sine),
        )))
    }
}