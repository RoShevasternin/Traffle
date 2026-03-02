package com.spacepuz.puzlesspace.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.spacepuz.puzlesspace.game.screens.LoaderScreen
import com.spacepuz.puzlesspace.game.utils.actor.HAlign
import com.spacepuz.puzlesspace.game.utils.actor.VAlign
import com.spacepuz.puzlesspace.game.utils.actor.addActorAligned
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedGroup
import com.spacepuz.puzlesspace.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(202f, 202f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(-360f, 0.6f)))

    }

}