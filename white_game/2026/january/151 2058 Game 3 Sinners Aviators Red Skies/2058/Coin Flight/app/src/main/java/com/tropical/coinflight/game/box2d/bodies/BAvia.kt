package com.tropical.coinflight.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.tropical.coinflight.game.actors.image.AImage
import com.tropical.coinflight.game.box2d.AbstractBody
import com.tropical.coinflight.game.box2d.BodyId
import com.tropical.coinflight.game.screens.ShopScreen
import com.tropical.coinflight.game.utils.advanced.AdvancedBox2dScreen
import com.tropical.coinflight.game.utils.advanced.AdvancedGroup

class BAvia(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.airList[ShopScreen.AVIA.avia_index])

    override var id            = BodyId.Game.AVIA
    override val collisionList = mutableListOf(BodyId.Game.COIN, BodyId.Game.ENEMY)

}