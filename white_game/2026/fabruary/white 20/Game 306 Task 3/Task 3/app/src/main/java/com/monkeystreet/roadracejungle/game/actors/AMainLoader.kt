package com.monkeystreet.roadracejungle.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.monkeystreet.roadracejungle.game.screens.LoaderScreen
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedGroup
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(332f, 332f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 1.2f, Interpolation.pow2)))
    }

}