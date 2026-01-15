package com.sweetfruit.catchermandarin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.sweetfruit.catchermandarin.game.PoromGame
import com.sweetfruit.catchermandarin.game.utils.actor.animHide
import com.sweetfruit.catchermandarin.game.utils.actor.animShow
import com.sweetfruit.catchermandarin.game.utils.actor.setOnClickListener
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedScreen
import com.sweetfruit.catchermandarin.game.utils.advanced.AdvancedStage
import com.sweetfruit.catchermandarin.game.utils.region
import com.sweetfruit.catchermandarin.game.utils.Poshlo_VREMA

class MedinaOleylaScreen(override val game: PoromGame): AdvancedScreen() {

    // Actor
    private val aSets     = Actor()
    private val aGo       = Actor()
    private val aSettings = Actor()

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.guglas.lodrinking.region)
        super.show()
        stageUI.root.animShow(Poshlo_VREMA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(Image(game.faradeo.buttons).apply {
            setBounds(237f, 23f, 1253f, 586f)
        })

        addBaaban()

        val exit = Actor()
        addActor(exit)
        exit.apply {
            setBounds(1325f, 23f, 165f, 165f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(Poshlo_VREMA) {
                    game.navigationManager.exit()
                }
            }
        }
    }

    private fun AdvancedStage.addBaaban() {
        addActors(aSets, aGo, aSettings)

        aSets.apply {
            setBounds(237f, 291f, 318f, 318f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(Poshlo_VREMA) {
                    game.navigationManager.navigate(SetsScreen::class.java.name, MedinaOleylaScreen::class.java.name)
                }
            }
        }
        aGo.apply {
            setBounds(605f, 291f, 318f, 318f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(Poshlo_VREMA) {
                    game.navigationManager.navigate(SviterScreen::class.java.name, MedinaOleylaScreen::class.java.name)
                }
            }
        }
        aSettings.apply {
            setBounds(973f, 291f, 318f, 318f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(Poshlo_VREMA) {
                    game.navigationManager.navigate(EsteticaScreen::class.java.name, MedinaOleylaScreen::class.java.name)
                }
            }
        }
    }

}