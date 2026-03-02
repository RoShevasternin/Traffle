package com.wintergroup.cupcakejump.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.wintergroup.cupcakejump.game.actors.ATmpGroup
import com.wintergroup.cupcakejump.game.actors.button.AButton
import com.wintergroup.cupcakejump.game.utils.Block
import com.wintergroup.cupcakejump.game.utils.HEIGHT_UI
import com.wintergroup.cupcakejump.game.utils.TIME_ANIM_SCREEN
import com.wintergroup.cupcakejump.game.utils.WIDTH_UI
import com.wintergroup.cupcakejump.game.utils.actor.HAlign
import com.wintergroup.cupcakejump.game.utils.actor.VAlign
import com.wintergroup.cupcakejump.game.utils.actor.addActorAligned
import com.wintergroup.cupcakejump.game.utils.actor.addActors
import com.wintergroup.cupcakejump.game.utils.actor.animDelay
import com.wintergroup.cupcakejump.game.utils.actor.animHide
import com.wintergroup.cupcakejump.game.utils.actor.animShow
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedScreen
import com.wintergroup.cupcakejump.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    private val aRulesImg  = Image(gdxGame.assetsAll.RULES)
    private val aMenuBtn   = AButton(this, AButton.Type.Menu)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()

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
            addBtnMenu()
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(137f, 384f, 823f, 1068f)
    }

    private fun Group.addBtnMenu() {
        addActor(aMenuBtn)
        aMenuBtn.setBounds(64f, 1673f, 180f, 180f)
        aMenuBtn.setOnClickListener { this@RulesScreen.animHide { gdxGame.navigationManager.back() } }
    }

}