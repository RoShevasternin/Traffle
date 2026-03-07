package com.neonticiton.toetacker.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.neonticiton.toetacker.game.screens.LoaderScreen
import com.neonticiton.toetacker.game.utils.actor.HAlign
import com.neonticiton.toetacker.game.utils.actor.VAlign
import com.neonticiton.toetacker.game.utils.actor.addActorAligned
import com.neonticiton.toetacker.game.utils.advanced.AdvancedGroup
import com.neonticiton.toetacker.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        loaderImg.setSize(835f, 835f)
        addActorAligned(loaderImg, HAlign.CENTER, VAlign.CENTER)

        loaderImg.setOrigin(Align.center)
        loaderImg.addAction(Actions.forever(Actions.rotateBy(360f, 0.35f)))

    }

}