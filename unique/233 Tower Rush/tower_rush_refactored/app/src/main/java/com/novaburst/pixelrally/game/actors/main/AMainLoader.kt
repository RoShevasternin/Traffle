/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.ALoading
import com.novaburst.pixelrally.game.actors.ALoadingLight
import com.novaburst.pixelrally.game.screens.LoaderScreen
import com.novaburst.pixelrally.game.utils.TIME_ANIM_SCREEN
import com.novaburst.pixelrally.game.utils.actor.animShow
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.gdxGame

class AMainLoader(
    override val screen: LoaderScreen,
): ComponentGroup() {

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
        imgGorilla.setBounds(13f, -153f, 1326f, 1769f)

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
        aLoading.setBounds(71f, 1739f, 254f, 130f)
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