package com.circuser.pairante.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.circuser.pairante.game.actors.ATmpGroup
import com.circuser.pairante.game.utils.Block
import com.circuser.pairante.game.utils.TIME_ANIM_SCREEN
import com.circuser.pairante.game.utils.actor.addActorWithConstraints
import com.circuser.pairante.game.utils.actor.addActors
import com.circuser.pairante.game.utils.actor.addAndFillActor
import com.circuser.pairante.game.utils.actor.animDelay
import com.circuser.pairante.game.utils.actor.animHide
import com.circuser.pairante.game.utils.actor.animShow
import com.circuser.pairante.game.utils.actor.setOnClickListener
import com.circuser.pairante.game.utils.advanced.AdvancedScreen
import com.circuser.pairante.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aMenuGroup = ATmpGroup(this)
    private val aSlonImg   = Image(gdxGame.assetsAll.SLON)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addMenuGroup()
        addSlonImg()

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

    private fun Group.addMenuGroup() {
        aMenuGroup.setSize(540f, 392f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuGroup
            endToEndOf       = this@addMenuGroup
            topToTopOf       = this@addMenuGroup
            bottomToBottomOf = this@addMenuGroup
        }

        val aMenuImg = Image(gdxGame.assetsAll.MENU)
        aMenuGroup.addAndFillActor(aMenuImg)

        val aPlay = Actor()
        val aSett = Actor()

        aMenuGroup.addActors(aPlay, aSett)
        aPlay.setBounds(0f, 209f, 540f, 181f)
        aSett.setBounds(0f, 0f, 540f, 181f)

        aPlay.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(SelecteScreen::class.java.name, MenuScreen::class.java.name) } }
        aSett.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addSlonImg() {
        aSlonImg.setSize(830f, 730f)
        addActorWithConstraints(aSlonImg) {
            startToStartOf   = this@addSlonImg
            bottomToBottomOf = this@addSlonImg
        }
    }

}