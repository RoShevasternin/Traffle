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

class CatapultaMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var CASTLE_INDEX = 0
    }

    private val casImg  = Image(game.allAssets.CASTLES)
    private val casList = List(4) { Actor() }
    private val exitA   = Actor()

    override fun show() {
        stageUI.root.animHide()
        setBackgrounds(game.startAssets.CBACA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(casImg)
        casImg.setBounds(46f, 165f, 1828f, 657f)

        var nx = 149f
        casList.onEachIndexed { index, actor ->
            addActor(actor)
            actor.apply {
                setBounds(nx,356f,368f,368f)
                nx += 50+368

                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM) {
                        CASTLE_INDEX = index
                        game.navigationManager.navigate(CatapultaGameScreen::class.java.name, CatapultaMenuScreen::class.java.name)
                    }
                }
            }
        }

        addActor(exitA)
        exitA.apply {
            setBounds(889f, 165f, 192f, 96f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

}