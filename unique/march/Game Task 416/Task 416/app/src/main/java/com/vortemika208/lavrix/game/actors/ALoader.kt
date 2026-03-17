package com.vortemika208.lavrix.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.vortemika208.lavrix.game.screens.LoaderScreen
import com.vortemika208.lavrix.game.utils.Acts
import com.vortemika208.lavrix.game.utils.AlignH
import com.vortemika208.lavrix.game.utils.AlignV
import com.vortemika208.lavrix.game.utils.actor.addActorAligned
import com.vortemika208.lavrix.game.utils.advanced.AdvancedGroup
import com.vortemika208.lavrix.game.utils.gdxGame

class ALoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoading  = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addImgLoading()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoading() {
        imgLoading.setSize(333f, 333f)
        addActorAligned(imgLoading, AlignH.CENTER, AlignV.CENTER)
        imgLoading.setOrigin(Align.center)

        // Rotate
        imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 0.8f)))
        // Scale
        imgLoading.addAction(
            Acts.forever(Acts.sequence(
                Acts.scaleTo(0.92f, 0.92f, 0.3f),
                Acts.scaleTo(1f, 1f, 0.3f),
            ))
        )
    }

}