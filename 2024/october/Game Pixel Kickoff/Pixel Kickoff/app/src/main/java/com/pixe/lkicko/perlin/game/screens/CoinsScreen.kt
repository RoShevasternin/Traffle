package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage

class CoinsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actors
    private val aBack   = Actor()
    private val aB_0_99 = Actor()
    private val aB_1_99 = Actor()
    private val aB_2_99 = Actor()

    override fun show() {
        setUIBackground(game.all.background_5)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addBuys()
    }

    private fun AdvancedStage.addBack() {
        addActor(aBack)
        aBack.setBounds(833f, 1811f, 218f, 90f)

        aBack.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addBuys() {
        addActors(aB_0_99, aB_1_99, aB_2_99)
        aB_0_99.setBounds(611f, 1370f, 421f, 174f)
        aB_1_99.setBounds(611f, 1022f, 421f, 174f)
        aB_2_99.setBounds(611f, 703f, 421f, 174f)
        aB_0_99.setOnClickListener {
            buy(0)
        }
        aB_1_99.setOnClickListener {
            buy(1)
        }
        aB_2_99.setOnClickListener {
            buy(2)
        }

        game.activity.blockPurchase = { purchase ->
            when(purchase) {
                "coins_100" -> {
                    game.dataStore.updatePX { it + 100 }
                }
                "1.99" -> {
                    game.dataStore.updatePX { it + 250 }
                }
                "2.99" -> {
                    game.dataStore.updatePX { it + 500 }
                }
            }
        }
    }

    // Logic -----------------------------------------------------

    private fun buy(index: Int) {
        game.soundUtil.apply { play(buy, 0.15f) }

        game.activity.apply {
            productDetailsList?.let { listDetails ->
                launchBilling(listDetails[index])
            }
        }

    }

}