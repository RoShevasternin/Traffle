package com.fushflyacensee.adventcoral.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
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
import com.fushflyacensee.adventcoral.game.utils.actor.animDelay
import com.fushflyacensee.adventcoral.game.utils.actor.animHide
import com.fushflyacensee.adventcoral.game.utils.actor.animShow
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedScreen
import com.fushflyacensee.adventcoral.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aMenuBtn = AButton(this, AButton.Type.Back)

    private val aPanelGroup = ATmpGroup(this)
    private val aRulesImg   = Image(gdxGame.assetsAll.RULES)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addBtnMenu()

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
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.TOP)

        aPanelGroup.apply {
            addRulesImg()
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(652f, 86f, 696f, 909f)
    }

    private fun Group.addBtnMenu() {
        aMenuBtn.setSize(130f, 130f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addBtnMenu
            topToTopOf     = this@addBtnMenu

            marginStart = 142f
            marginTop   = 55f
        }
        aMenuBtn.setOnClickListener { this@RulesScreen.animHide { gdxGame.navigationManager.back() } }
    }

}