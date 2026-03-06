package com.bounceques.ternationaret.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.animShow
import com.bounceques.ternationaret.game.utils.actor.setOnClickListener
import com.bounceques.ternationaret.game.utils.advanced.AdvancedScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.gdxGame
import com.bounceques.ternationaret.game.utils.region

class WinScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.assetsAll.WIN_PAN)

    override fun show() {
        gdxGame.soundUtil.apply { play(WIN) }
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addButtons()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(118f, 361f, 845f, 1198f)
    }

    private fun AdvancedStage.addButtons() {
        var ny = 620f
        arrayOf(
            PinkMenuScreen::class.java.name,
            PinkRulesScreen::class.java.name,
            PinkYrowniScreen::class.java.name,
        ).onEach { sName ->
            addActor(Actor().apply {
                setBounds(268f, ny, 544f, 137f)
                ny += (80 + 137)

                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM) {
                        game.navigationManager.navigate(sName, WinScreen::class.java.name)
                    }
                }
            })
        }
    }

}