package com.cargodance.liftoffer.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.cargodance.liftoffer.game.LibGDXGame
import com.cargodance.liftoffer.game.actors.checkbox.ACheckBox
import com.cargodance.liftoffer.game.utils.TIME_ANIM
import com.cargodance.liftoffer.game.utils.actor.animHide
import com.cargodance.liftoffer.game.utils.actor.setOnClickListener
import com.cargodance.liftoffer.game.utils.advanced.AdvancedScreen
import com.cargodance.liftoffer.game.utils.advanced.AdvancedStage
import com.cargodance.liftoffer.game.utils.region

class RulesScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val controlPanelImg = Image(game.allAssets.reluser)

    override fun show() {
        setBackBackground(game.loaderAssets.GARAGES.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(controlPanelImg)
        controlPanelImg.setBounds(292f, 135f, 1340f, 771f)

        val img = Actor()
        img.setBounds(758f, 135f, 404f, 146f)
        addActor(img)

        img.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }


}