package com.oceanstar.ballduinstar.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.oceanstar.ballduinstar.game.actors.ATmpGroup
import com.oceanstar.ballduinstar.game.actors.button.AButton
import com.oceanstar.ballduinstar.game.utils.Block
import com.oceanstar.ballduinstar.game.utils.HEIGHT_UI
import com.oceanstar.ballduinstar.game.utils.TIME_ANIM_SCREEN
import com.oceanstar.ballduinstar.game.utils.WIDTH_UI
import com.oceanstar.ballduinstar.game.utils.actor.HAlign
import com.oceanstar.ballduinstar.game.utils.actor.VAlign
import com.oceanstar.ballduinstar.game.utils.actor.addActorAligned
import com.oceanstar.ballduinstar.game.utils.actor.addActors
import com.oceanstar.ballduinstar.game.utils.actor.animDelay
import com.oceanstar.ballduinstar.game.utils.actor.animHide
import com.oceanstar.ballduinstar.game.utils.actor.animShow
import com.oceanstar.ballduinstar.game.utils.actor.setBounds
import com.oceanstar.ballduinstar.game.utils.actor.setOnClickListener
import com.oceanstar.ballduinstar.game.utils.advanced.AdvancedScreen
import com.oceanstar.ballduinstar.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    private val listR = listOf(
        gdxGame.assetsAll.R1,
        gdxGame.assetsAll.R2,
    )

    private val aRulesImg  = Image(listR.first())
    private val aMenuBtn   = AButton(this, AButton.Type.Menu)

    private var curIndex = 0

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addRulesImg()
            addBtnMenu()
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(87f, 316f, 906f, 1240f)

        val aBtn = Actor()
        addActor(aBtn)
        aBtn.setBounds(437f, 316f, 206f, 206f)
        aBtn.setOnClickListener {
            curIndex = if (curIndex + 1 >= 2) 0 else 1
            aRulesImg.drawable = TextureRegionDrawable(listR[curIndex])
        }
    }

    private fun Group.addBtnMenu() {
        addActor(aMenuBtn)
        aMenuBtn.setBounds(25f, 1681f, 206f, 206f)
        aMenuBtn.setOnClickListener { this@RulesScreen.animHide { gdxGame.navigationManager.back() } }
    }

}