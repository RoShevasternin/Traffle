package com.jellymp.jumpem.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.jellymp.jumpem.game.screens.LoaderScreen
import com.jellymp.jumpem.game.utils.actor.HAlign
import com.jellymp.jumpem.game.utils.actor.VAlign
import com.jellymp.jumpem.game.utils.actor.addActorAligned
import com.jellymp.jumpem.game.utils.advanced.AdvancedGroup
import com.jellymp.jumpem.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(262f, 262f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(-360f, 2.5f, Interpolation.linear)))
    }

}