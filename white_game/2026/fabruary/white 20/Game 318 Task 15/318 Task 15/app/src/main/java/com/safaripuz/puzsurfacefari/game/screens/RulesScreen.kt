package com.safaripuz.puzsurfacefari.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.safaripuz.puzsurfacefari.game.actors.button.AButton
import com.safaripuz.puzsurfacefari.game.actors.checkbox.ACheckBox
import com.safaripuz.puzsurfacefari.game.utils.Block
import com.safaripuz.puzsurfacefari.game.utils.TIME_ANIM_SCREEN
import com.safaripuz.puzsurfacefari.game.utils.actor.addActorWithConstraints
import com.safaripuz.puzsurfacefari.game.utils.actor.animDelay
import com.safaripuz.puzsurfacefari.game.utils.actor.animHide
import com.safaripuz.puzsurfacefari.game.utils.actor.animShow
import com.safaripuz.puzsurfacefari.game.utils.advanced.AdvancedScreen
import com.safaripuz.puzsurfacefari.game.utils.gdxGame
import com.safaripuz.puzsurfacefari.util.log

class RulesScreen: AdvancedScreen() {

    private val aRulesImg = Image(gdxGame.assetsAll.RULES_PAN)
    private val aBackBtn  = AButton(this, AButton.Type.BACK)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.B_BLUR)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addRulesImg()
        addBtnBack()

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

    private fun Group.addBtnBack() {
        aBackBtn.setSize(150f, 150f)
        addActorWithConstraints(aBackBtn) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 71f
            marginTop   = 59f
        }

        aBackBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }

    }

    private fun Group.addRulesImg() {
        aRulesImg.setSize(860f, 1066f)
        addActorWithConstraints(aRulesImg) {
            startToStartOf   = this@addRulesImg
            endToEndOf       = this@addRulesImg
            topToTopOf       = this@addRulesImg
            bottomToBottomOf = this@addRulesImg
        }
    }

}