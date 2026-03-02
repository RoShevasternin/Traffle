package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.monkeystreet.roadracejungle.game.actors.ATmpGroup
import com.monkeystreet.roadracejungle.game.actors.button.AButton
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.addActors
import com.monkeystreet.roadracejungle.game.utils.actor.animDelay
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val listHTPTexture = listOf(
        gdxGame.assetsAll.HTP_1,
        gdxGame.assetsAll.HTP_2,
    )

    private var currentIndex = 0
        set(value) {
            field = value
            updateImgHTP()
        }

    private val aPanelGroup = ATmpGroup(this)
    private val aRulesImg   = Image(listHTPTexture[currentIndex])
    private val aNextBtn    = AButton(this, AButton.Type.Next)
    private val aMenuBtn    = AButton(this, AButton.Type.ToMenu)

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
        aRulesImg.setBounds(148f, 601f, 785f, 1093f)
    }

    private fun Group.addBtnMenu() {
        addActors(aNextBtn, aMenuBtn)
        aNextBtn.setBounds(350f, 282f, 380f, 145f)
        aMenuBtn.setBounds(350f, 121f, 380f, 145f)

        aNextBtn.setOnClickListener { currentIndex = if (currentIndex + 1 >= 2) 0 else currentIndex + 1 }
        aMenuBtn.setOnClickListener { this@RulesScreen.animHide { gdxGame.navigationManager.back() } }
    }

    // Logic ------------------------------------------------------------------------

    private fun updateImgHTP() {
        aRulesImg.drawable = TextureRegionDrawable(listHTPTexture[currentIndex])
    }

}