package com.smallmini.races.gonca.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.smallmini.races.gonca.game.LibGDXGame
import com.smallmini.races.gonca.game.utils.TIME_ANIM
import com.smallmini.races.gonca.game.utils.actor.animHide
import com.smallmini.races.gonca.game.utils.actor.animShow
import com.smallmini.races.gonca.game.utils.actor.setBounds
import com.smallmini.races.gonca.game.utils.actor.setOnClickListener
import com.smallmini.races.gonca.game.utils.advanced.AdvancedScreen
import com.smallmini.races.gonca.game.utils.advanced.AdvancedStage
import com.smallmini.races.gonca.game.utils.region

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