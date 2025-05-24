package com.pinlq.esst.bloo.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pinlq.esst.bloo.game.actors.AImage
import com.pinlq.esst.bloo.game.box2d.AbstractBody
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedBox2dScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedGroup
import com.pinlq.esst.bloo.game.utils.runGDX
import com.pinlq.esst.bloo.util.log
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
        density     = 4f
        restitution = 0.7f
        friction    = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.all.listCube.random())

    fun startShake() {
        coroutine?.launch {
            delay(800)
            while (isActive) {
                runGDX {
                    (actor as AImage).drawable = TextureRegionDrawable(screenBox2d.game.all.listCube.random())
                }
                delay(150)
            }
        }
    }
}