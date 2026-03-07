package com.fushflyacensee.adventcoral.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.fushflyacensee.adventcoral.game.actors.AImage
import com.fushflyacensee.adventcoral.game.box2d.AbstractBody
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedGroup
import com.fushflyacensee.adventcoral.game.utils.advanced.box2d.AdvancedBox2dScreen
import com.fushflyacensee.adventcoral.game.utils.gdxGame

class BTop(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "top"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.KinematicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.TOP)

    var isCounted = false
}