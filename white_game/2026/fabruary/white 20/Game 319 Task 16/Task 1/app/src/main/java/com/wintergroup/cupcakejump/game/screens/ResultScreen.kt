package com.wintergroup.cupcakejump.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
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
import com.wintergroup.cupcakejump.game.utils.actor.setBounds
import com.wintergroup.cupcakejump.game.utils.actor.setOnClickListener
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedScreen
import com.wintergroup.cupcakejump.game.utils.font.FontParameter
import com.wintergroup.cupcakejump.game.utils.gdxGame

class ResultScreen: AdvancedScreen() {

    private val params = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(113)
    private val font   = fontGenerator_AsapCondensed_SemiBold.generateFont(params)

    private val aPanelGroup = ATmpGroup(this)

    private val aRulesImg = Image(gdxGame.assetsAll.SCORE)
    private val aCountLbl  = Label((10..100).random().toString(), Label.LabelStyle(font, Color.valueOf("FF2268")))

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }

        setBackBackground(gdxGame.assetsAll.RESULT)
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

            addActor(aCountLbl)
            aCountLbl.setBounds(499f, 804f, 125f, 97f)
            aCountLbl.setAlignment(Align.center)
        }
    }

    private fun Group.addRulesImg() {
        addActor(aRulesImg)
        aRulesImg.setBounds(33f, 255f, 1052f, 1366f)
    }

    private fun Group.addBtnMenu() {
        val aRestart = Actor()
        val aMenu    = Actor()
        addActors(aRestart, aMenu)
        aRestart.setBounds(389f, 629f, 346f, 132f)
        aMenu.setBounds(389f, 489f, 346f, 132f)
        aRestart.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
        aMenu.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
    }

}