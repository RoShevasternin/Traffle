package com.pyramidconnect.sorting.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.pyramidconnect.sorting.game.screens.LoaderScreen
import com.pyramidconnect.sorting.game.utils.advanced.AdvancedGroup
import com.pyramidconnect.sorting.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        addActor(loaderImg)
        loaderImg.apply {
            setBounds(441f, 861f, 198f, 198f)
            setOrigin(Align.center) // Обов'язково для симетричного збільшення
            addAction(Actions.forever(Actions.rotateBy(360f, 1f)))
        }
    }

}