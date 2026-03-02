package com.skynebowow.runnerblue.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.skynebowow.runnerblue.game.actors.image.AImage
import com.skynebowow.runnerblue.game.box2d.AbstractBody
import com.skynebowow.runnerblue.game.box2d.BodyId
import com.skynebowow.runnerblue.game.screens.ShopScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedBox2dScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedGroup

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
        ShopScreen.AviaType._1  -> screenBox2d.game.gameAssets.listAvia[s.avia_index]
        ShopScreen.AviaType._2 -> screenBox2d.game.gameAssets.listAvia[s.avia_index]
        ShopScreen.AviaType._3 -> screenBox2d.game.gameAssets.listAvia[s.avia_index]
        ShopScreen.AviaType._4 -> screenBox2d.game.gameAssets.listAvia[s.avia_index]
    }

}