package com.dasholy.olympusdash.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.dasholy.olympusdash.game.actors.image.AImage
import com.dasholy.olympusdash.game.box2d.AbstractBody
import com.dasholy.olympusdash.game.box2d.BodyId
import com.dasholy.olympusdash.game.screens.ShopScreen
import com.dasholy.olympusdash.game.utils.TextureEmpty
import com.dasholy.olympusdash.game.utils.advanced.AdvancedBox2dScreen
import com.dasholy.olympusdash.game.utils.advanced.AdvancedGroup

class BAvia(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, getRegionByType())

    override var id            = BodyId.Game.AVIA
    override val collisionList = mutableListOf(BodyId.Game.COIN, BodyId.Game.ENEMY)

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getRegionByType() = when (val s = ShopScreen.AVIA) {
        ShopScreen.AviaType._100  -> screenBox2d.game.gameAssets.aviaList[s.avia_index]
        ShopScreen.AviaType._200 -> screenBox2d.game.gameAssets.aviaList[s.avia_index]
        ShopScreen.AviaType._500 -> screenBox2d.game.gameAssets.aviaList[s.avia_index]
        ShopScreen.AviaType._850 -> screenBox2d.game.gameAssets.aviaList[s.avia_index]
        ShopScreen.AviaType._1000 -> screenBox2d.game.gameAssets.aviaList[s.avia_index]
    }

}