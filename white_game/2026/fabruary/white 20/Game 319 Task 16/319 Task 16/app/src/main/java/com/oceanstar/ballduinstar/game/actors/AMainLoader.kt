package com.oceanstar.ballduinstar.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.oceanstar.ballduinstar.game.screens.LoaderScreen
import com.oceanstar.ballduinstar.game.utils.Acts
import com.oceanstar.ballduinstar.game.utils.actor.HAlign
import com.oceanstar.ballduinstar.game.utils.actor.VAlign
import com.oceanstar.ballduinstar.game.utils.actor.addActorAligned
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedGroup
import com.oceanstar.ballduinstar.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val aLoaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        aLoaderImg.setSize(191f, 189f)
        addActorAligned(aLoaderImg, HAlign.CENTER, VAlign.CENTER)

        aLoaderImg.setOrigin(Align.center)
        aLoaderImg.addAction(Acts.forever(Acts.rotateBy(360f, 1.25f)))
    }

}