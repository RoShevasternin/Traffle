package com.arcadepixel.roadracer.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.arcadepixel.roadracer.game.LibGDXGame
import com.arcadepixel.roadracer.game.utils.TIME_ANIM
import com.arcadepixel.roadracer.game.utils.actor.animHide
import com.arcadepixel.roadracer.game.utils.actor.animShow
import com.arcadepixel.roadracer.game.utils.actor.setOnClickListener
import com.arcadepixel.roadracer.game.utils.advanced.AdvancedScreen
import com.arcadepixel.roadracer.game.utils.advanced.AdvancedStage
import com.arcadepixel.roadracer.game.utils.region

class MiniRulesScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val imgMenu = Image(game.allAssets.rules)

    override fun show() {
        setBackBackground(game.loaderAssets.mini.region)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgMenu)
        imgMenu.setBounds(192f, 176f, 697f, 1568f)

        val actor = Actor()
        addActor(actor)
        actor.setBounds(285f, 176f, 511f, 221f)

        actor.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

}