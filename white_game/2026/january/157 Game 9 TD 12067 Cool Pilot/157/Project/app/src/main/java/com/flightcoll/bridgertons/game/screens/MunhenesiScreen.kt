package com.flightcoll.bridgertons.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.flightcoll.bridgertons.game.SuberGame
import com.flightcoll.bridgertons.game.utils.actor.animHide
import com.flightcoll.bridgertons.game.utils.actor.animShow
import com.flightcoll.bridgertons.game.utils.actor.setBounds
import com.flightcoll.bridgertons.game.utils.actor.setOnClickListener
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedScreen
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedStage
import com.flightcoll.bridgertons.game.utils.region
import com.flightcoll.bridgertons.game.utils.vremia_ANIM

class MunhenesiScreen(override val game: SuberGame): AdvancedScreen() {

    // Actor
    private val aRules = Actor()
    private val aPlay  = Actor()
    private val aSette = Actor()

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.fisters.zagruzon.region)
        super.show()
        stageUI.root.animShow(vremia_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(Image(game.assets.manu).apply {
            setBounds(299f, 84f, 929f, 494f)
        })

        addAcitactor()

        val exit = Actor()
        addActor(exit)
        exit.apply {
            setBounds(683f, 84f, 156f, 92f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(vremia_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addAcitactor() {
        addActors(aRules, aPlay, aSette)

        aRules.apply {
            setBounds(299f, 322f, 263f, 256f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(vremia_ANIM) {
                    game.navigationManager.navigate(RuleroScreen::class.java.name, MunhenesiScreen::class.java.name)
                }
            }
        }
        aPlay.apply {
            setBounds(632f, 322f, 263f, 256f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(vremia_ANIM) {
                    game.navigationManager.navigate(IglaScreen::class.java.name, MunhenesiScreen::class.java.name)
                }
            }
        }
        aSette.apply {
            setBounds(965f, 322f, 263f, 256f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(vremia_ANIM) {
                    game.navigationManager.navigate(StertoyScreen::class.java.name, MunhenesiScreen::class.java.name)
                }
            }
        }
    }

}