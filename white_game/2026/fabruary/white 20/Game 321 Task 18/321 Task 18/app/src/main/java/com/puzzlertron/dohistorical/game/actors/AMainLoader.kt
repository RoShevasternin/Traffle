package com.puzzlertron.dohistorical.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.puzzlertron.dohistorical.game.screens.LoaderScreen
import com.puzzlertron.dohistorical.game.utils.actor.HAlign
import com.puzzlertron.dohistorical.game.utils.actor.VAlign
import com.puzzlertron.dohistorical.game.utils.actor.addActorAligned
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedGroup
import com.puzzlertron.dohistorical.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(243f, 245f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 0.45f)))

    }

}