package com.tictactoe.classic.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.tictactoe.classic.game.screens.LoaderScreen
import com.tictactoe.classic.game.utils.actor.HAlign
import com.tictactoe.classic.game.utils.actor.VAlign
import com.tictactoe.classic.game.utils.actor.addActorAligned
import com.tictactoe.classic.game.utils.advanced.AdvancedGroup
import com.tictactoe.classic.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(170f, 170f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 0.5f)))

    }

}