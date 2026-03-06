package com.safaripuz.puzsurfacefari.game.actors.puzzle

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.safaripuz.puzsurfacefari.game.utils.actor.enable
import com.safaripuz.puzsurfacefari.game.utils.actor.addAndFillActor
import com.safaripuz.puzsurfacefari.game.utils.actor.disable
import com.safaripuz.puzsurfacefari.game.utils.actor.enable
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedGroup
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedScreen
import com.safaripuz.puzsurfacefari.game.utils.gdxGame

class APuzzle(
    override val screen: AdvancedScreen,
    private val textureRegion: TextureRegion,
): AdvancedGroup() {

    var doAfterRotate: (Float) -> Unit = { }

    override fun addActorsOnGroup() {
        addAndFillActor(Image(textureRegion))
        addListener(getInputAdapter())
    }

//    override fun draw(batch: Batch?, parentAlpha: Float) {
//        log("pppppppppppppppp")
//        batch?.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
//    }

    private fun getInputAdapter() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            gdxGame.soundUtil.apply { play(touch_game) }
            disable()
            addAction(Actions.sequence(
                Actions.rotateBy(90f, 0.3f, Interpolation.fade),
                Actions.run {
                    doAfterRotate(rotation)
                    enable()
                }
            ))
            return true
        }
    }

}