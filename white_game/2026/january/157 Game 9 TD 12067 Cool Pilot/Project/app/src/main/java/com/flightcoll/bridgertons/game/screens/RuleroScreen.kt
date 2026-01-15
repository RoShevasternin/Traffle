package com.flightcoll.bridgertons.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.flightcoll.bridgertons.game.SuberGame
import com.flightcoll.bridgertons.game.actors.button.AButton
import com.flightcoll.bridgertons.game.utils.actor.animHide
import com.flightcoll.bridgertons.game.utils.actor.animShow
import com.flightcoll.bridgertons.game.utils.actor.setBounds
import com.flightcoll.bridgertons.game.utils.actor.setOnClickListener
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedScreen
import com.flightcoll.bridgertons.game.utils.advanced.AdvancedStage
import com.flightcoll.bridgertons.game.utils.region
import com.flightcoll.bridgertons.game.utils.vremia_ANIM

class RuleroScreen(override val game: SuberGame): AdvancedScreen() {

    // Actor
    private val aBack = AButton(this, AButton.Static.Type.mEnU)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.fisters.zagruzon.region)
        super.show()
        stageUI.root.animShow(vremia_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(Image(game.assets.welcome).apply {
            setBounds(340f, 187f, 918f, 682f)
        })

        addBack()
    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addBack() {
        addActors(aBack)

        aBack.apply {
            setBounds(96f, 458f, 156f, 92f)
            setOnClickListener {
                stageUI.root.animHide(vremia_ANIM) { game.navigationManager.back() }
            }
        }
    }

}