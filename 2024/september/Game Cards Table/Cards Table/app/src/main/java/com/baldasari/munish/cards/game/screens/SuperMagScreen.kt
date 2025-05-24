package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.actors.AButton
import com.baldasari.munish.cards.game.utils.TIME_ANIM
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.region
import com.baldasari.munish.cards.game.utils.runGDX
import kotlinx.coroutines.launch

class SuperMagScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var SUPER_MAG_INDEX = 0
    }

    private val btnMenu      = AButton(this, AButton.Static.Type.Menu)
    private val btnRestart   = AButton(this, AButton.Static.Type.Restart)
    private val imgTitle     = Image(game.all.supermag)
    private val imgWinPlayer = Image(game.splash.magList[SUPER_MAG_INDEX])

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            game.soundUtil.apply { play(magical_victory, 0.7f) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgTitle()
                addMenu()
                addRestart()
                addPlayer()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(281f,318f,518f,205f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addRestart() {
        addActor(btnRestart)
        btnRestart.apply {
            setBounds(281f,82f,518f,205f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(142f,1430f,796f,316f)
    }

    private fun AdvancedStage.addPlayer() {
        addActor(imgWinPlayer)
        imgWinPlayer.setBounds(218f, 638f, 645f, 645f)
        imgWinPlayer.apply {
            setOrigin(322f, 0f)
            val scale = 0.18f
            addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-scale, -scale, 0.4f, Interpolation.sineIn),
                        Actions.scaleBy(scale, scale, 0.4f, Interpolation.sineOut),
                    )
                )
            )
        }
    }

}