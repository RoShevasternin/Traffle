package com.puzdever.puzsweet.game.actors.main

import com.puzdever.puzsweet.game.screens.LoaderScreen
import com.puzdever.puzsweet.game.utils.Acts
import com.puzdever.puzsweet.game.utils.advanced.AdvancedGroup
import com.puzdever.puzsweet.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.puzdever.puzsweet.game.actors.AProgress
import com.puzdever.puzsweet.game.utils.actor.disable

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoaderPan = Image(gdxGame.assetsLoader.load_pan)
    private val progress     = AProgress(screen)

    override fun addActorsOnGroup() {
        addImgLoaderPan()
        addProgress()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoaderPan() {
        addActor(imgLoaderPan)
        imgLoaderPan.setBounds(68f, 233f, 944f, 123f)

        // Rotate
        //imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 1f)))

        // Scale
        //imgLoader.addAction(Acts.forever(Acts.sequence(
        //    Acts.scaleTo(1.134f, 1.134f, 0.36f),
        //    Acts.scaleTo(1f, 1f, 0.36f),
        //)))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(86f, 247f, 907f, 94f)
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}