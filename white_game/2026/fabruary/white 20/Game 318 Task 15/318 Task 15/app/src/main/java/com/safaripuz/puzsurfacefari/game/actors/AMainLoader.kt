package com.safaripuz.puzsurfacefari.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.safaripuz.puzsurfacefari.game.screens.LoaderScreen
import com.safaripuz.puzsurfacefari.game.utils.actor.HAlign
import com.safaripuz.puzsurfacefari.game.utils.actor.VAlign
import com.safaripuz.puzsurfacefari.game.utils.actor.addActorAligned
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedGroup
import com.safaripuz.puzsurfacefari.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(231f, 237f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(-360f, 0.58f)))

    }

}