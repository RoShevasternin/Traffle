package com.circuser.pairante.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.circuser.pairante.game.screens.LoaderScreen
import com.circuser.pairante.game.utils.actor.HAlign
import com.circuser.pairante.game.utils.actor.VAlign
import com.circuser.pairante.game.utils.actor.addActorAligned
import com.circuser.pairante.game.utils.advanced.AdvancedGroup
import com.circuser.pairante.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(230f, 233f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 0.55f)))

    }

}