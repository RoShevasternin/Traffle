package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.utils.*
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true

        var BACKGROUND_INDEX = 0
    }

    private val imgPersonage = Image(game.splash.listPersonage.random())
    private val imgBtns      = Image(game.all.game_rules)

    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = quest.apply {
                isLooping = true
                volumeLevelFlow.value = 25f
            } }
        }

         setBackBackground(game.splash.listBackground[BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgPersonage()
                addImgBtns()
            }
        }
    }

    private fun AdvancedStage.addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.apply {
            setBounds(233f,1109f,613f,690f)

            val valueY = 155f
            addAction(Actions.forever(
                Actions.sequence(
                    Actions.moveBy(0f, valueY, 1.2f, Interpolation.smoother),
                    Actions.moveBy(0f, -valueY, 1.5f, Interpolation.smoother),
                )
            ))
        }
    }

    private fun AdvancedStage.addImgBtns() {
        addActor(imgBtns)
        imgBtns.setBounds(96f,-10f,889f, 1066f)

        val aGame  = Actor()
        val aRules = Actor()
        val aSett  = Actor()
        val aColl  = Actor()
        addActors(aGame, aRules, aSett, aColl)
        aGame.apply {
            setBounds(196f,722f,689f,238f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(NumOfPlayersScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aRules.apply {
            setBounds(196f,518f,689f,180f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aSett.apply {
            setBounds(196f,310f,689f,180f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(SettScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        aColl.apply {
            setBounds(196f,94f,689f,180f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(CollectionScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

}