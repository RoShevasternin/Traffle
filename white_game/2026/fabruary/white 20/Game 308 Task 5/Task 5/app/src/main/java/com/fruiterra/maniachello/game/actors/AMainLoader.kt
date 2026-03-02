package com.fruiterra.maniachello.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fruiterra.maniachello.game.screens.LoaderScreen
import com.fruiterra.maniachello.game.utils.actor.HAlign
import com.fruiterra.maniachello.game.utils.actor.VAlign
import com.fruiterra.maniachello.game.utils.actor.addActorAligned
import com.fruiterra.maniachello.game.utils.advanced.AdvancedGroup
import com.fruiterra.maniachello.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(142f, 142f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 1.23f, Interpolation.linear)))
    }

}