package com.gorillaz.puzzlegame.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.ALoading
import com.gorillaz.puzzlegame.game.actors.ALoadingLight
import com.gorillaz.puzzlegame.game.screens.LoaderScreen
import com.gorillaz.puzzlegame.game.utils.TIME_ANIM_SCREEN
import com.gorillaz.puzzlegame.game.utils.actor.animShow
import com.gorillaz.puzzlegame.game.utils.actor.setOrigin
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgGorilla    = Image(gdxGame.assetsLoader.gorilla)
    private val aLoading      = ALoading(screen)
    private val imgSevens     = Image(gdxGame.assetsLoader.sevebs)
    private val aLoadingLight = ALoadingLight(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgGorilla()
        addALoading()
        addImgSevens()
        addALoadingLight()

        animShow(TIME_ANIM_SCREEN)
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgGorilla() {
        addActor(imgGorilla)
        imgGorilla.setBounds(2f, 269f, 1075f, 1129f)

        imgGorilla.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -25f, 0.45f, Interpolation.sineIn),
            Actions.moveBy(0f, 25f, 0.45f, Interpolation.sineOut),
        )))
    }

    private fun addALoading() {
        addActor(aLoading)
        aLoading.setBounds(147f, 0f, 785f, 285f)
    }

    private fun addImgSevens() {
        addActor(imgSevens)
        imgSevens.setBounds(460f, 1f, 620f, 513f)
        imgSevens.setOrigin(Align.bottomRight)

        imgSevens.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(0.015f, 0.015f, 0.45f, Interpolation.sineIn),
            Actions.scaleBy(-0.015f, -0.015f, 0.45f, Interpolation.sineOut),
        )))
    }

    private fun addALoadingLight() {
        addActor(aLoadingLight)
        aLoadingLight.setBounds(86f, 1251f, 907f, 598f)

        aLoadingLight.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(0.015f, 0.015f, 0.45f, Interpolation.sineIn),
            Actions.scaleBy(-0.015f, -0.015f, 0.45f, Interpolation.sineOut),
        )))
    }

}