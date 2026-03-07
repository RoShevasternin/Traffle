package com.fushflyacensee.adventcoral.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fushflyacensee.adventcoral.game.actors.ATmpGroup
import com.fushflyacensee.adventcoral.game.actors.button.AButton
import com.fushflyacensee.adventcoral.game.utils.Block
import com.fushflyacensee.adventcoral.game.utils.HEIGHT_UI
import com.fushflyacensee.adventcoral.game.utils.TIME_ANIM_SCREEN
import com.fushflyacensee.adventcoral.game.utils.WIDTH_UI
import com.fushflyacensee.adventcoral.game.utils.actor.HAlign
import com.fushflyacensee.adventcoral.game.utils.actor.VAlign
import com.fushflyacensee.adventcoral.game.utils.actor.addActorAligned
import com.fushflyacensee.adventcoral.game.utils.actor.addActorWithConstraints
import com.fushflyacensee.adventcoral.game.utils.actor.addActors
import com.fushflyacensee.adventcoral.game.utils.actor.addAndFillActor
import com.fushflyacensee.adventcoral.game.utils.actor.animDelay
import com.fushflyacensee.adventcoral.game.utils.actor.animHide
import com.fushflyacensee.adventcoral.game.utils.actor.animShow
import com.fushflyacensee.adventcoral.game.utils.actor.setBounds
import com.fushflyacensee.adventcoral.game.utils.actor.setOnClickListener
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedScreen
import com.fushflyacensee.adventcoral.game.utils.font.FontParameter
import com.fushflyacensee.adventcoral.game.utils.gdxGame

class ResultScreen: AdvancedScreen() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(77)
    private val font   = fontGenerator_GochiHand_Regular.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(gdxGame.assetsAll.PANEL_SCORE)
    private val aCountLbl   = Label("$GDX_counter", Label.LabelStyle(font, Color.WHITE))

    private val aRulesImg = Image(gdxGame.assetsAll.RESULT)

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }

        setBackBackground(gdxGame.assetsAll.BACKGROUND_RESULT)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addRulesImg()

        animShow()
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShow(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(433f, 208f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf     = this@addPanelGroup
            startToStartOf = this@addPanelGroup
            endToEndOf     = this@addPanelGroup
            marginTop      = 63f
        }

        aPanelGroup.apply {
            addActors(aPanelImg, aCountLbl)
        }

        aPanelImg.setBounds(0f, 93f, 433f, 115f)
        aCountLbl.setBounds(178f, 104f, 76f, 92f)
        aCountLbl.setAlignment(Align.center)
    }

    private fun Group.addRulesImg() {
        val tmp = ATmpGroup(this@ResultScreen)
        tmp.setSize(553f, 780f)
        addActorAligned(tmp, HAlign.CENTER, VAlign.CENTER)
        tmp.y = 90f

        val aRestart = Actor()
        val aMenu    = Actor()

        tmp.apply {
            addAndFillActor(aRulesImg)
            addActors(aRestart, aMenu)
        }

        aRestart.setBounds(60f, 135f, 432f, 115f)
        aMenu.setBounds(60f, 0f, 432f, 115f)

        aRestart.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
        aMenu.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
    }

}