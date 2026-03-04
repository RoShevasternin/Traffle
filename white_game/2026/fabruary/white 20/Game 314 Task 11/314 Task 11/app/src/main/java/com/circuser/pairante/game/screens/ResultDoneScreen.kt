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

class ResultDoneScreen: AdvancedScreen() {

    private val aMenuGroup = ATmpGroup(this)

    override fun show() {
        gdxGame.soundUtil.apply { play(win_game) }

        setBackBackground(gdxGame.assetsAll.B_WIN)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addMenuGroup()

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
        aMenuGroup.setSize(801f, 930f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuGroup
            endToEndOf       = this@addMenuGroup
            topToTopOf       = this@addMenuGroup
            bottomToBottomOf = this@addMenuGroup
        }

        val aMenuImg = Image(gdxGame.assetsAll.RESULT)
        aMenuGroup.addAndFillActor(aMenuImg)

        val aRestart  = Actor()
        val aHome     = Actor()
        val aTitleImg = Image(gdxGame.assetsAll.VIC)

        aMenuGroup.addActors(aRestart, aHome, aTitleImg)
        aRestart.setBounds(130f, 304f, 540f, 181f)
        aHome.setBounds(130f, 94f, 540f, 181f)
        aTitleImg.setBounds(96f, 693f, 609f, 147f)

        aRestart.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
        aHome.setOnClickListener(gdxGame.soundUtil) { animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) } }
    }

}