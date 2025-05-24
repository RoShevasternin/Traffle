package com.pink.plinuirtaster.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pink.plinuirtaster.game.actors.AImage
import com.pink.plinuirtaster.game.box2d.AbstractBody
import com.pink.plinuirtaster.game.utils.advanced.AdvancedBox2dScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedGroup
import com.pink.plinuirtaster.game.utils.runGDX
import com.pink.plinuirtaster.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BCube(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "cube"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = false
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 5f
        restitution = 0.65f
        friction    = 0.67f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.cubesList.random())

    fun startShake() {
        coroutine?.launch {
            delay(700)
            while (isActive) {
                runGDX {
                    log("aaa")
                    (actor as AImage).drawable = TextureRegionDrawable(screenBox2d.game.all.cubesList.random())
                }
                delay(150)
            }
        }
    }
}