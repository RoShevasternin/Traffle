package com.babun.flutterdash.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.babun.flutterdash.game.screens.LoaderScreen
import com.babun.flutterdash.game.utils.actor.HAlign
import com.babun.flutterdash.game.utils.actor.VAlign
import com.babun.flutterdash.game.utils.actor.addActorAligned
import com.babun.flutterdash.game.utils.advanced.AdvancedGroup
import com.babun.flutterdash.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(355f, 355f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 1.5f, Interpolation.sine)))
    }

}