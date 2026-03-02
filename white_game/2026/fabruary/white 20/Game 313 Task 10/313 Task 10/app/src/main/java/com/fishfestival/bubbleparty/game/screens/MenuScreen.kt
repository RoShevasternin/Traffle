package com.fishfestival.bubbleparty.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.actors.ATmpGroup
import com.fishfestival.bubbleparty.game.actors.checkbox.ACheckBox
import com.fishfestival.bubbleparty.game.utils.Block
import com.fishfestival.bubbleparty.game.utils.TIME_ANIM_SCREEN
import com.fishfestival.bubbleparty.game.utils.actor.HAlign
import com.fishfestival.bubbleparty.game.utils.actor.VAlign
import com.fishfestival.bubbleparty.game.utils.actor.addActorAligned
import com.fishfestival.bubbleparty.game.utils.actor.addActorWithConstraints
import com.fishfestival.bubbleparty.game.utils.actor.addActors
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.actor.animDelay
import com.fishfestival.bubbleparty.game.utils.actor.animHide
import com.fishfestival.bubbleparty.game.utils.actor.animShow
import com.fishfestival.bubbleparty.game.utils.actor.setOnClickListener
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aFishImg   = Image(gdxGame.assetsAll.FISH)
    private val aMenuImg   = Image(gdxGame.assetsAll.BOTTOM)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Type.MUSIC)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addFishImg()
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

    private fun Group.addFishImg() {
        aFishImg.setSize(1096f, 1095f)
        addActorAligned(aFishImg, HAlign.CENTER, VAlign.CENTER)
    }

    private fun Group.addMenuImg() {
        val aMenuGroup = ATmpGroup(this@MenuScreen)
        aMenuGroup.setSize(1080f, 492f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuImg
            endToEndOf       = this@addMenuImg
            bottomToBottomOf = this@addMenuImg

            marginBottom = -65f
        }
        aMenuGroup.addAndFillActor(aMenuImg)

        val aPlay   = Actor()
        val aRules  = Actor()
        val aLeader = Actor()

        aMenuGroup.addActors(aPlay, aRules, aLeader)
        aPlay.setBounds(430f, 164f, 220f, 220f)
        aRules.setBounds(150f, 164f, 220f, 220f)
        aLeader.setBounds(710f, 164f, 220f, 220f)

        aPlay.setOnClickListener { animHideScreen { gdxGame.goToGame() } }//gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
        aLeader.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(LiaderScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(118f, 118f)
        addActorWithConstraints(aSoundBox) {
            startToStartOf = this@addSoundBox
            topToTopOf     = this@addSoundBox

            marginStart = 52f
            marginTop   = 59f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(118f, 118f)
        addActorWithConstraints(aMusicBox) {
            endToEndOf     = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginEnd   = 52f
            marginTop   = 59f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

}