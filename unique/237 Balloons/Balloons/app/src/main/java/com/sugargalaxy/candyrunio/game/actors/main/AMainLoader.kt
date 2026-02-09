package com.sugargalaxy.candyrunio.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.sugargalaxy.candyrunio.game.actors.ALoading
import com.sugargalaxy.candyrunio.game.actors.ALoadingLight
import com.sugargalaxy.candyrunio.game.screens.LoaderScreen
import com.sugargalaxy.candyrunio.game.utils.TIME_ANIM_SCREEN
import com.sugargalaxy.candyrunio.game.utils.actor.animShow
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedGroup
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgGorilla    = Image(gdxGame.assetsLoader.gorilla)
    private val aLoading      = ALoading(screen)
    //private val imgSevens     = Image(gdxGame.assetsLoader.sevebs)
    //private val aLoadingLight = ALoadingLight(screen)

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
        imgGorilla.setBounds(123f, 360f, 833f, 981f)

        imgGorilla.addAction(Actions.forever(Actions.parallel(
            // Плавно вгору-вниз (левітація)
            Actions.sequence(
                Actions.moveBy(0f, 20f, 2f, Interpolation.sine),
                Actions.moveBy(0f, -20f, 2f, Interpolation.sine)
            ),
            // Легке розширення (дихання)
            Actions.sequence(
                Actions.scaleTo(1.03f, 1.03f, 2.5f, Interpolation.sine),
                Actions.scaleTo(1f, 1f, 2.5f, Interpolation.sine)
            )
        )))
    }

    private fun addALoading() {
        addActor(aLoading)
        aLoading.setBounds(388f, 65f, 254f, 130f)
    }

    private fun addImgSevens() {
//        addActor(imgSevens)
//        imgSevens.setBounds(460f, 1f, 620f, 513f)
//        imgSevens.setOrigin(Align.bottomRight)
//
//        imgSevens.addAction(Actions.forever(Actions.sequence(
//            Actions.scaleBy(0.015f, 0.015f, 0.45f, Interpolation.sineIn),
//            Actions.scaleBy(-0.015f, -0.015f, 0.45f, Interpolation.sineOut),
//        )))
    }

    private fun addALoadingLight() {
//        addActor(aLoadingLight)
//        aLoadingLight.setBounds(86f, 1251f, 907f, 598f)
//
//        aLoadingLight.addAction(Actions.forever(Actions.sequence(
//            Actions.scaleBy(0.015f, 0.015f, 0.45f, Interpolation.sineIn),
//            Actions.scaleBy(-0.015f, -0.015f, 0.45f, Interpolation.sineOut),
//        )))
    }

}