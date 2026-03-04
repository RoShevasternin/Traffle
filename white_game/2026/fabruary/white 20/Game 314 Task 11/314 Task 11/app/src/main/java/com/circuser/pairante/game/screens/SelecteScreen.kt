package com.circuser.pairante.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.circuser.pairante.game.actors.ATmpGroup
import com.circuser.pairante.game.actors.checkbox.ACheckBox
import com.circuser.pairante.game.utils.Block
import com.circuser.pairante.game.utils.TIME_ANIM_SCREEN
import com.circuser.pairante.game.utils.actor.addActorWithConstraints
import com.circuser.pairante.game.utils.actor.addActors
import com.circuser.pairante.game.utils.actor.addAndFillActor
import com.circuser.pairante.game.utils.actor.animDelay
import com.circuser.pairante.game.utils.actor.animHide
import com.circuser.pairante.game.utils.actor.animShow
import com.circuser.pairante.game.utils.advanced.AdvancedScreen
import com.circuser.pairante.game.utils.gdxGame

class SelecteScreen: AdvancedScreen() {

    companion object {
        var INDEX = 0
            private set
    }

    private val aMenuGroup = ATmpGroup(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_SELECTER)
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
        aMenuGroup.setSize(1109f, 1556f)
        addActorWithConstraints(aMenuGroup) {
            startToStartOf   = this@addMenuGroup
            endToEndOf       = this@addMenuGroup
            topToTopOf       = this@addMenuGroup
            bottomToBottomOf = this@addMenuGroup
        }

        val aSelectImg = Image(gdxGame.assetsAll.SCHOOSE)
        aMenuGroup.addAndFillActor(aSelectImg)

        val cb1 = ACheckBox(this@SelecteScreen, ACheckBox.Type.CHECK)
        val cb2 = ACheckBox(this@SelecteScreen, ACheckBox.Type.CHECK)

        aMenuGroup.addActors(cb1, cb2)
        cb1.setBounds(247f, 636f, 613f, 588f)
        cb2.setBounds(247f, 0f, 613f, 588f)

        fun navToGame() { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }

        cb1.setOnCheckListener {
            if (it) {
                INDEX = 0
                navToGame()
            }
        }
        cb2.setOnCheckListener {
            if (it) {
                INDEX = 1
                navToGame()
            }
        }
    }

}