package com.colderonetrains.battlesskates.game.actors.battle_setup

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.actor.setOnClickListener
import com.colderonetrains.battlesskates.game.utils.actor.setOnTouchListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AItemPlayer(
    override val screen: AdvancedScreen,
    val count      : Int,
    val lsRegular43: Label.LabelStyle,
): AdvancedGroup() {

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    private val imgPanel = Image(gdxGame.assetsAll.PLAYER)
    val lblTitle = Label("Player $count Name", lsRegular43)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblTitle()

        setOnTouchListener(gdxGame.soundUtil) {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    if (text.isNotBlank()) {
                        val newName = text.take(13)

                        lblTitle.setText(newName)
                    }
                }

                override fun canceled() {}
            }, "Player $count Name", "", "")
        }
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(61f, 11f, 688f, 112f)
    }

}