package com.sugaraxplosion.candysmoy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.sugaraxplosion.candysmoy.game.LibGDXGame
import com.sugaraxplosion.candysmoy.game.actors.AButton
import com.sugaraxplosion.candysmoy.game.box2d.WorldUtil
import com.sugaraxplosion.candysmoy.game.utils.GColor
import com.sugaraxplosion.candysmoy.game.utils.TIME_ANIM
import com.sugaraxplosion.candysmoy.game.utils.actor.animHide
import com.sugaraxplosion.candysmoy.game.utils.actor.animShow
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedBox2dScreen
import com.sugaraxplosion.candysmoy.game.utils.advanced.AdvancedStage
import com.sugaraxplosion.candysmoy.game.utils.font.FontParameter
import com.sugaraxplosion.candysmoy.game.utils.region

class PitanieScreen(override val game: LibGDXGame) : AdvancedBox2dScreen(WorldUtil()) {

    private val imgGirl = Image(game.all.PITANIE)
    private val btnExit = AButton(this, AButton.Static.Type.Exit)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.VAFLA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgLogo()
        addBtns()
    }

    private fun AdvancedStage.addImgLogo() {
        addActor(imgGirl)
        imgGirl.setBounds(11f, 0f, 518f, 769f)
    }

    private fun AdvancedStage.addBtns() {
        addActor(btnExit)
        btnExit.apply {
            setBounds(42f, 851f, 70f, 79f)
            setOnClickListener {
                animHideScreen {
                    game.navigationManager.back()
                }
            }
        }
    }

}