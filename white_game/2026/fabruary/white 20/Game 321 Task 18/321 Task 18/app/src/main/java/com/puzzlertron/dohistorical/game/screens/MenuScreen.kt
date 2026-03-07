package com.puzzlertron.dohistorical.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzzlertron.dohistorical.game.actors.ATmpGroup
import com.puzzlertron.dohistorical.game.actors.checkbox.ACheckBox
import com.puzzlertron.dohistorical.game.utils.Block
import com.puzzlertron.dohistorical.game.utils.TIME_ANIM_SCREEN
import com.puzzlertron.dohistorical.game.utils.actor.addActorWithConstraints
import com.puzzlertron.dohistorical.game.utils.actor.addActors
import com.puzzlertron.dohistorical.game.utils.actor.addAndFillActor
import com.puzzlertron.dohistorical.game.utils.actor.animDelay
import com.puzzlertron.dohistorical.game.utils.actor.animHide
import com.puzzlertron.dohistorical.game.utils.actor.animShow
import com.puzzlertron.dohistorical.game.utils.actor.setOnClickListener
import com.puzzlertron.dohistorical.game.utils.advanced.AdvancedScreen
import com.puzzlertron.dohistorical.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aMenuImg   = Image(gdxGame.assetsAll.MENU_PAN)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Type.MUSIC)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
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
        aMenuGroup.setSize(864f, 1044f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuImg
            endToEndOf       = this@addMenuImg
            topToTopOf       = this@addMenuImg
            bottomToBottomOf = this@addMenuImg
        }
        aMenuGroup.addAndFillActor(aMenuImg)

        val aPlay   = Actor()
        val aRules  = Actor()
        val aExit   = Actor()

        aMenuGroup.addActors(aPlay, aRules, aExit)
        aPlay .setBounds(158f, 666f, 543f, 205f)
        aRules.setBounds(158f, 419f, 543f, 205f)
        aExit .setBounds(158f, 171f, 543f, 205f)

        aPlay.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
        aExit.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.exit() } }
    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(148f, 137f)
        addActorWithConstraints(aSoundBox) {
            startToStartOf = this@addSoundBox
            topToTopOf     = this@addSoundBox

            marginStart = 77f
            marginTop   = 76f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(150f, 150f)
        addActorWithConstraints(aMusicBox) {
            endToEndOf     = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginEnd   = 77f
            marginTop   = 76f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}