package com.babun.flutterdash.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.babun.flutterdash.game.actors.ATmpGroup
import com.babun.flutterdash.game.actors.button.AButton
import com.babun.flutterdash.game.utils.Block
import com.babun.flutterdash.game.utils.HEIGHT_UI
import com.babun.flutterdash.game.utils.TIME_ANIM_SCREEN
import com.babun.flutterdash.game.utils.WIDTH_UI
import com.babun.flutterdash.game.utils.actor.HAlign
import com.babun.flutterdash.game.utils.actor.VAlign
import com.babun.flutterdash.game.utils.actor.addActorAligned
import com.babun.flutterdash.game.utils.actor.addActorWithConstraints
import com.babun.flutterdash.game.utils.actor.addActors
import com.babun.flutterdash.game.utils.actor.animDelay
import com.babun.flutterdash.game.utils.actor.animHide
import com.babun.flutterdash.game.utils.actor.animShow
import com.babun.flutterdash.game.utils.advanced.AdvancedScreen
import com.babun.flutterdash.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aSettBtn = AButton(this, AButton.Type.Sett)

    private val aPanelGroup = ATmpGroup(this)
    private val aGlowImg    = Image(gdxGame.assetsAll.GLOW)
    private val aBabkaImg   = Image(gdxGame.assetsAll.BABKA)
    private val aPlayBtn    = AButton(this, AButton.Type.Play)
    private val aRulesBtn   = AButton(this, AButton.Type.Rules)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND)
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
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addPanelGroup() {
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.TOP)

        aPanelGroup.apply {
            addBabka()
            addBtns()
        }
    }

    private fun Group.addBtns() {
        addActors(aPlayBtn, aRulesBtn)
        aPlayBtn.setBounds(702f, 238f, 517f, 137f)
        aRulesBtn.setBounds(744f, 110f, 433f, 115f)

        aPlayBtn.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRulesBtn.setOnClickListener { this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addBabka() {
        addActors(aGlowImg, aBabkaImg)
        aGlowImg.setBounds(541f, 286f, 785f, 785f)
        aBabkaImg.setBounds(537f, 388f, 847f, 655f)

        // ВАЖЛИВО — центр для правильного scale
        aGlowImg.setOrigin(Align.center)
        aBabkaImg.setOrigin(Align.center)

        // 🌟 Світіння — мʼяке дихання + альфа
        aGlowImg.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(1.18f, 1.18f, 1.2f, Interpolation.sine),
                        Actions.alpha(0.85f, 1.2f, Interpolation.sine)
                    ),
                    Actions.parallel(
                        Actions.scaleTo(1f, 1f, 1.2f, Interpolation.sine),
                        Actions.alpha(1f, 1.2f, Interpolation.sine)
                    )
                )
            )
        )

        // 🧁 Бабка — дуже легке дихання (менше ніж glow)
        aBabkaImg.addAction(
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