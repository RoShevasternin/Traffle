package com.spacepuz.puzlesspace.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.spacepuz.puzlesspace.game.actors.ATmpGroup
import com.spacepuz.puzlesspace.game.actors.checkbox.ACheckBox
import com.spacepuz.puzlesspace.game.utils.Block
import com.spacepuz.puzlesspace.game.utils.TIME_ANIM_SCREEN
import com.spacepuz.puzlesspace.game.utils.actor.addActorWithConstraints
import com.spacepuz.puzlesspace.game.utils.actor.addActors
import com.spacepuz.puzlesspace.game.utils.actor.addAndFillActor
import com.spacepuz.puzlesspace.game.utils.actor.animDelay
import com.spacepuz.puzlesspace.game.utils.actor.animHide
import com.spacepuz.puzlesspace.game.utils.actor.animShow
import com.spacepuz.puzlesspace.game.utils.actor.setOnClickListener
import com.spacepuz.puzlesspace.game.utils.advanced.AdvancedScreen
import com.spacepuz.puzlesspace.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aMenuImg   = Image(gdxGame.assetsAll.MENU)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Type.MUSIC)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addMenuImg()
        addSoundBox()
        addMusicBox()

        animShowScreen()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addMenuImg() {
        val aMenuGroup = ATmpGroup(this@MenuScreen)
        aMenuGroup.setSize(876f, 943f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuImg
            endToEndOf       = this@addMenuImg
            topToTopOf       = this@addMenuImg
            bottomToBottomOf = this@addMenuImg
        }
        aMenuGroup.addAndFillActor(aMenuImg)

        val aLetsGo = Actor()
        val aRules  = Actor()
        val aExit   = Actor()

        aMenuGroup.addActors(aLetsGo, aRules, aExit)
        aLetsGo.setBounds(145f, 573f, 585f, 217f)
        aRules.setBounds(145f, 340f, 585f, 217f)
        aExit.setBounds(225f, 151f, 423f, 157f)

        aLetsGo.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
        aExit.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.exit() } }
    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(110f, 110f)
        addActorWithConstraints(aSoundBox) {
            startToStartOf = this@addSoundBox
            topToTopOf     = this@addSoundBox

            marginStart = 85f
            marginTop   = 65f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(110f, 110f)
        addActorWithConstraints(aMusicBox) {
            endToEndOf     = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginEnd   = 85f
            marginTop   = 65f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}