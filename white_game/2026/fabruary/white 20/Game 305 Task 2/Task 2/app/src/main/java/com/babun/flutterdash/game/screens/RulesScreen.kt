package com.babun.flutterdash.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.babun.flutterdash.game.actors.ATmpGroup
import com.babun.flutterdash.game.actors.button.AButton
import com.babun.flutterdash.game.utils.Block
import com.babun.flutterdash.game.utils.HEIGHT_UI
import com.babun.flutterdash.game.utils.TIME_ANIM_SCREEN
import com.babun.flutterdash.game.utils.WIDTH_UI
import com.babun.flutterdash.game.utils.actor.HAlign
import com.babun.flutterdash.game.utils.actor.VAlign
import com.babun.flutterdash.game.utils.actor.addActorAligned
import com.babun.flutterdash.game.utils.actor.addActorWithConstraints
import com.babun.flutterdash.game.utils.actor.addActors
import com.babun.flutterdash.game.utils.actor.animDelay
import com.babun.flutterdash.game.utils.actor.animHide
import com.babun.flutterdash.game.utils.actor.animShow
import com.babun.flutterdash.game.utils.advanced.AdvancedScreen
import com.babun.flutterdash.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aMenuBtn = AButton(this, AButton.Type.Back)

    private val aPanelGroup = ATmpGroup(this)
    private val aRulesImg   = Image(gdxGame.assetsAll.RULES)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
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