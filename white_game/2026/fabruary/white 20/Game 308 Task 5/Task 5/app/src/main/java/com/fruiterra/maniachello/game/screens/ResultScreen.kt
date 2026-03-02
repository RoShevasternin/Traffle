package com.fruiterra.maniachello.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.fruiterra.maniachello.game.actors.ATmpGroup
import com.fruiterra.maniachello.game.utils.Block
import com.fruiterra.maniachello.game.utils.TIME_ANIM_SCREEN
import com.fruiterra.maniachello.game.utils.actor.HAlign
import com.fruiterra.maniachello.game.utils.actor.VAlign
import com.fruiterra.maniachello.game.utils.actor.addActorAligned
import com.fruiterra.maniachello.game.utils.actor.addActorWithConstraints
import com.fruiterra.maniachello.game.utils.actor.addActors
import com.fruiterra.maniachello.game.utils.actor.addAndFillActor
import com.fruiterra.maniachello.game.utils.actor.animDelay
import com.fruiterra.maniachello.game.utils.actor.animHide
import com.fruiterra.maniachello.game.utils.actor.animShow
import com.fruiterra.maniachello.game.utils.actor.setBounds
import com.fruiterra.maniachello.game.utils.actor.setOnClickListener
import com.fruiterra.maniachello.game.utils.advanced.AdvancedScreen
import com.fruiterra.maniachello.game.utils.gdxGame

class ResultScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aPanelImg   = Image(gdxGame.assetsAll.WIN)

    override fun show() {
        gdxGame.soundUtil.apply { play(win) }

        setBackBackground(gdxGame.assetsAll.BACKGROUND_WIN)
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
        aPanelGroup.setSize(466f, 707f)
        addActorWithConstraints(aPanelGroup) {
            topToTopOf     = this@addPanelGroup
            startToStartOf = this@addPanelGroup
            endToEndOf     = this@addPanelGroup
            marginTop      = 46f
        }

        val aMen  = Actor()
        val aNext = Actor()
        aPanelGroup.apply {
            addAndFillActor(aPanelImg)
            addActors(aMen, aNext)
        }

        aMen.setBounds(83f, 151f, 300f, 80f)
        aNext.setBounds(83f, 59f, 300f, 80f)

        aMen.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.back() } }
        aNext.setOnClickListener { this@ResultScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name) } }
    }

}