package com.destroyer.catapulter.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.destroyer.catapulter.game.LibGDXGame
import com.destroyer.catapulter.game.utils.TIME_ANIM
import com.destroyer.catapulter.game.utils.actor.animHide
import com.destroyer.catapulter.game.utils.actor.animShow
import com.destroyer.catapulter.game.utils.actor.setOnClickListener
import com.destroyer.catapulter.game.utils.advanced.AdvancedScreen
import com.destroyer.catapulter.game.utils.advanced.AdvancedStage
import com.destroyer.catapulter.game.utils.region

class RulesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val casImg = Image(game.allAssets.rules)

    override fun show() {
        stageUI.root.animHide()
        setBackgrounds(game.startAssets.CBACA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(casImg)
        casImg.setBounds(105f, 77f, 1710f, 745f)

        val actor = Actor()
        addActor(actor)
        actor.setBounds(761f, 77f, 398f, 116f)


        actor.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()

            }
        }
    }


}