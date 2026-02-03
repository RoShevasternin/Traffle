package com.greciao.candyrocket.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.greciao.candyrocket.game.actors.image.AImage
import com.greciao.candyrocket.game.box2d.AbstractBody
import com.greciao.candyrocket.game.box2d.BodyId
import com.greciao.candyrocket.game.screens.ShopScreen
import com.greciao.candyrocket.game.utils.advanced.AdvancedBox2dScreen
import com.greciao.candyrocket.game.utils.advanced.AdvancedGroup

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