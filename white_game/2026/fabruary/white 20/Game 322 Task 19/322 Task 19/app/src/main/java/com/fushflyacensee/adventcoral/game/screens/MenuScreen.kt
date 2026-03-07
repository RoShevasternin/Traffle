package com.fushflyacensee.adventcoral.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fushflyacensee.adventcoral.game.actors.ATmpGroup
import com.fushflyacensee.adventcoral.game.actors.button.AButton
import com.fushflyacensee.adventcoral.game.utils.Block
import com.fushflyacensee.adventcoral.game.utils.HEIGHT_UI
import com.fushflyacensee.adventcoral.game.utils.TIME_ANIM_SCREEN
import com.fushflyacensee.adventcoral.game.utils.WIDTH_UI
import com.fushflyacensee.adventcoral.game.utils.actor.HAlign
import com.fushflyacensee.adventcoral.game.utils.actor.VAlign
import com.fushflyacensee.adventcoral.game.utils.actor.addActorAligned
import com.fushflyacensee.adventcoral.game.utils.actor.addActorWithConstraints
import com.fushflyacensee.adventcoral.game.utils.actor.addActors
import com.fushflyacensee.adventcoral.game.utils.actor.animDelay
import com.fushflyacensee.adventcoral.game.utils.actor.animHide
import com.fushflyacensee.adventcoral.game.utils.actor.animShow
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedScreen
import com.fushflyacensee.adventcoral.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aSettBtn = AButton(this, AButton.Type.Sett)

    private val aPanelGroup = ATmpGroup(this)
    private val aFishImg    = Image(gdxGame.assetsAll.FISH)
    private val aPlayBtn    = AButton(this, AButton.Type.Play)
    private val aRulesBtn   = AButton(this, AButton.Type.Rules)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGroup()
        addSettBtn()

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
            addBabka()
            addBtns()
        }
    }

    private fun Group.addBtns() {
        addActors(aPlayBtn, aRulesBtn)
        aPlayBtn.setBounds(702f, 210f, 515f, 137f)
        aRulesBtn.setBounds(744f, 82f, 433f, 115f)

        aPlayBtn.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRulesBtn.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addBabka() {
        addActors(aFishImg)
        aFishImg.setBounds(389f, 260f, 1089f, 793f)

        // ВАЖЛИВО — центр для правильного scale
        aFishImg.setOrigin(Align.center)

        // 🧁 Бабка — дуже легке дихання (менше ніж glow)
        aFishImg.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.scaleTo(1.03f, 1.03f, 1.4f, Interpolation.sine),
                    Actions.scaleTo(1f, 1f, 1.4f, Interpolation.sine)
                )
            )
        )
    }

    private fun Group.addSettBtn() {
        aSettBtn.setSize(130f, 130f)
        addActorWithConstraints(aSettBtn) {
            startToStartOf = this@addSettBtn
            topToTopOf     = this@addSettBtn

            marginStart = 142f
            marginTop   = 55f
        }

        aSettBtn.setOnClickListener {
            this@MenuScreen.animHide { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) }
        }
    }

}