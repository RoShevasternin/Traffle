package com.senqorvia774.lottomatica.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.senqorvia774.lottomatica.game.screens.LoaderScreen
import com.senqorvia774.lottomatica.game.utils.Acts
import com.senqorvia774.lottomatica.game.utils.AlignH
import com.senqorvia774.lottomatica.game.utils.AlignV
import com.senqorvia774.lottomatica.game.utils.actor.addActorAligned
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.gdxGame

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