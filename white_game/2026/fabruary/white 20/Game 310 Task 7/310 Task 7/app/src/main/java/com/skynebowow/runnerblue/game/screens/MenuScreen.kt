package com.skynebowow.runnerblue.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.skynebowow.runnerblue.game.GDXGame
import com.skynebowow.runnerblue.game.actors.button.AButton
import com.skynebowow.runnerblue.game.actors.checkbox.ACheckBox
import com.skynebowow.runnerblue.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.skynebowow.runnerblue.game.utils.actor.animHide
import com.skynebowow.runnerblue.game.utils.actor.animShow
import com.skynebowow.runnerblue.game.utils.actor.setOnClickListener
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedScreen
import com.skynebowow.runnerblue.game.utils.advanced.AdvancedStage
import com.skynebowow.runnerblue.game.utils.gdxGame
import com.skynebowow.runnerblue.game.utils.region

class MenuScreen(override val game: GDXGame) : AdvancedScreen() {

    private val aPlayBtn  = AButton(this, AButton.Static.Type.PLAY)
    private val aRulesBtn = AButton(this, AButton.Static.Type.RULES)
    private val aExitBtn  = AButton(this, AButton.Static.Type.EXIT)
    private val aShopBtn  = AButton(this, AButton.Static.Type.SHOP)
    private val aSoundBox = ACheckBox(this, ACheckBox.Static.Type.SOUND)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.gameAssets.BK_MENU.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActors(aPlayBtn, aRulesBtn, aExitBtn, aSoundBox, aShopBtn)
        aPlayBtn.setBounds(247f, 835f, 586f, 248f)
        aRulesBtn.setBounds(247f, 557f, 586f, 248f)
        aExitBtn.setBounds(321f, 343f, 438f, 185f)
        aSoundBox.setBounds(62f, 1757f, 110f, 110f)
        aShopBtn.setBounds(903f, 1757f, 110f, 110f)

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aPlayBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
        aRulesBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) }
        aShopBtn.setOnClickListener(game.soundUtil) { game.navigationManager.navigate(ShopScreen::class.java.name, MenuScreen::class.java.name) }
        aExitBtn.setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        aSoundBox.setOnCheckListener { isCheck -> gdxGame.soundUtil.isPause = isCheck }

    }

}