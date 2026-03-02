package com.fruiterra.maniachello.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fruiterra.maniachello.game.actors.ATmpGroup
import com.fruiterra.maniachello.game.utils.Block
import com.fruiterra.maniachello.game.utils.HEIGHT_UI
import com.fruiterra.maniachello.game.utils.TIME_ANIM_SCREEN
import com.fruiterra.maniachello.game.utils.WIDTH_UI
import com.fruiterra.maniachello.game.utils.actor.HAlign
import com.fruiterra.maniachello.game.utils.actor.VAlign
import com.fruiterra.maniachello.game.utils.actor.addActorAligned
import com.fruiterra.maniachello.game.utils.actor.addActors
import com.fruiterra.maniachello.game.utils.actor.animDelay
import com.fruiterra.maniachello.game.utils.actor.animHide
import com.fruiterra.maniachello.game.utils.actor.animShow
import com.fruiterra.maniachello.game.utils.actor.setBounds
import com.fruiterra.maniachello.game.utils.actor.setOnClickListener
import com.fruiterra.maniachello.game.utils.advanced.AdvancedScreen
import com.fruiterra.maniachello.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aPanelGroup = ATmpGroup(this)
    private val aBtnsImg    = Image(gdxGame.assetsAll.BTNS)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND)
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
        aPanelGroup.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPanelGroup, HAlign.CENTER, VAlign.CENTER)

        aPanelGroup.apply {
            addBtns()
        }
    }

    private fun Group.addBtns() {
        addActor(aBtnsImg)
        aBtnsImg.setBounds(516f, 163f, 529f, 472f)

        var ny = 492f
        repeat(3) { index ->
            addActor(Actor().apply {
                setBounds(516f, ny, 529f, 143f)
                ny -= 21 + 143
                setOnClickListener {
                    when(index) {
                        0 -> this@MenuScreen.animHide { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) }
                        1 -> this@MenuScreen.animHide { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) }
                        2 -> this@MenuScreen.animHide { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) }
                    }
                }
            })
        }

    }

}