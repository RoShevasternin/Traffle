package com.bounceques.ternationaret.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.actors.button.AButton
import com.bounceques.ternationaret.game.screens.levels._1_Screen
import com.bounceques.ternationaret.game.screens.levels._2_Screen
import com.bounceques.ternationaret.game.screens.levels._3_Screen
import com.bounceques.ternationaret.game.screens.levels._4_Screen
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.animShow
import com.bounceques.ternationaret.game.utils.actor.setBounds
import com.bounceques.ternationaret.game.utils.actor.setOnClickListener
import com.bounceques.ternationaret.game.utils.advanced.AdvancedScreen
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.gdxGame
import com.bounceques.ternationaret.game.utils.region

var GDX_INDEX = 0
    private set

class PinkYrowniScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val panelImg = Image(game.assetsAll.LEVELS_PAN)
    private val aBackBtn = AButton(this, AButton.Static.Type.BACK)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B_BLUR.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addBtns()
        addBack()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(53f, 371f, 974f, 1319f)
    }

    private fun AdvancedStage.addBtns() {
        val sizik = Vector2(470f, 470f)

        val levelName = listOf(
            _1_Screen::class.java.name,
            _2_Screen::class.java.name,
            _3_Screen::class.java.name,
            _4_Screen::class.java.name,
        )

        arrayOf(
            Vector2(53f, 874f),
            Vector2(557f, 874f),
            Vector2(53f, 371f),
            Vector2(557f, 371f),
        ).onEachIndexed { index, pos ->
            addActor(Actor().apply {
                setBounds(pos, sizik)
                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM) {
                        GDX_INDEX = index
                        game.navigationManager.navigate(levelName[index])
                    }
                }
            })
        }
    }

    private fun AdvancedStage.addBack() {
        addActors(aBackBtn)
        aBackBtn.setBounds(68f, 1707f, 156f, 165f)

        aBackBtn.setOnClickListener {
            stageUI.root.animHide { gdxGame.navigationManager.back() }
        }
    }

}