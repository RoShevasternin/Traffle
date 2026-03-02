package com.wintergroup.cupcakejump.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.wintergroup.cupcakejump.game.actors.ATmpGroup
import com.wintergroup.cupcakejump.game.actors.checkbox.ACheckBox
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
import com.wintergroup.cupcakejump.game.utils.actor.setOnClickListener
import com.wintergroup.cupcakejump.game.utils.advanced.AdvancedScreen
import com.wintergroup.cupcakejump.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)

    private val aBtnsImg  = Image(gdxGame.assetsAll.btns)
    private val aSoundBox = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox = ACheckBox(this, ACheckBox.Type.MUSIC)

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
            addBtnsImg()
            addSoundMusic()
        }
    }

    private fun Group.addBtnsImg() {
        addActor(aBtnsImg)
        aBtnsImg.setBounds(277f, 643f, 527f, 634f)

        var ny = 1077f
        repeat(3) { index ->
            addActor(Actor().apply {
                setBounds(280f, ny, 524f, 200f)
                ny -= 17 + 200

                setOnClickListener {
                    when(index) {
                        0 -> this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, this@MenuScreen::class.java.name) }
                        1 -> this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, this@MenuScreen::class.java.name) }
                        2 -> this@MenuScreen.animHide { gdxGame.navigationManager.exit() }
                    }
                }
            })
        }
    }

    private fun Group.addSoundMusic() {
        addActors(aSoundBox, aMusicBox)
        aSoundBox.setBounds(57f, 1673f, 180f, 183f)
        aMusicBox.setBounds(852f, 1673f, 180f, 183f)

        if (gdxGame.soundUtil.isPause) aSoundBox.check()
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }
        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}