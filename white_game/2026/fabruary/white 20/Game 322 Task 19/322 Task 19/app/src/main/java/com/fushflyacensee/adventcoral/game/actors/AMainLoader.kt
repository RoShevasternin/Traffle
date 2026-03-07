package com.fushflyacensee.adventcoral.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fushflyacensee.adventcoral.game.screens.LoaderScreen
import com.fushflyacensee.adventcoral.game.utils.actor.HAlign
import com.fushflyacensee.adventcoral.game.utils.actor.VAlign
import com.fushflyacensee.adventcoral.game.utils.actor.addActorAligned
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedGroup
import com.fushflyacensee.adventcoral.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addALightLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addALightLoader() {
        aLoaderImg.setSize(263f, 263f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 2.0f, Interpolation.sine)))
    }

}