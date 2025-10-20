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

class MiniMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val imgMenu = Image(game.allAssets.menu)

    override fun show() {
        setBackBackground(game.loaderAssets.mini.region)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgMenu)
        imgMenu.setBounds(285f, 450f, 511f, 947f)

        var ny = 1176f

        val scrName = listOf(
            MiniGameScreen::class.java.name,
            MiniRulesScreen::class.java.name,
            MiniSettScreen::class.java.name,
        )

        val actors = listOf(Actor(), Actor(), Actor(), Actor())
        actors.forEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(285f, ny, 511f, 221f)
            ny -= 21 + 221

            actor.setOnClickListener {
                if (index == 3) game.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(scrName[index], MiniMenuScreen::class.java.name)
                }
            }
        }
    }

}