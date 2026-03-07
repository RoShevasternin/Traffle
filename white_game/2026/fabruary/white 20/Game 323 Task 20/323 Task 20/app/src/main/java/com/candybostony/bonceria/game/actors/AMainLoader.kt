package com.candybostony.bonceria.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.candybostony.bonceria.game.screens.LoaderScreen
import com.candybostony.bonceria.game.utils.actor.HAlign
import com.candybostony.bonceria.game.utils.actor.VAlign
import com.candybostony.bonceria.game.utils.actor.addActorAligned
import com.candybostony.bonceria.game.utils.advanced.AdvancedGroup
import com.candybostony.bonceria.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(270f, 270f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(-360f, 2.0f, Interpolation.linear)))
    }

}