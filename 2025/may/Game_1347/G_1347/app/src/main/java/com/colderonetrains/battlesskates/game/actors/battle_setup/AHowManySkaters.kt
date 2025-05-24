package com.colderonetrains.battlesskates.game.actors.battle_setup

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.AScrollPane
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBoxGroup
import com.colderonetrains.battlesskates.game.screens.BattleSetupScreen
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.actor.setOnTouchListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.game.utils.isNumTake
import com.colderonetrains.battlesskates.game.utils.runGDX

class AHowManySkaters(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font43        = screen.fontGenerator_GTWalsheimPro_Regular.generateFont(fontParameter.setSize(43))
    private val font80        = screen.fontGenerator_GTWalsheimPro_RegularOblique.generateFont(fontParameter.setSize(80))

    private val ls43 = Label.LabelStyle(font43, GameColor.white_43)
    private val ls80 = Label.LabelStyle(font80, Color.WHITE)

    private val box2 = ACheckBox(screen, ACheckBox.Type.S2)
    private val box3 = ACheckBox(screen, ACheckBox.Type.S3)
    private val box4 = ACheckBox(screen, ACheckBox.Type.S4)

    private val imgPanel = Image(gdxGame.assetsAll.HOW_MANY_SKATERS)
    private val lblCount = Label("", ls80)

    private val verticalGroup = AVerticalGroup(screen, 31f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    // Field
    var blockCountSkaters: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addActor(imgPanel)
        imgPanel.setBounds(1f, 395f, 767f, 747f)

        addBoxes()
        addScroll()
        addLblCount()
    }

    private fun addBoxes() {
        addActors(box2, box3, box4)
        box2.setBounds(1f, 810f, 203f, 203f)
        box3.setBounds(283f, 810f, 203f, 203f)
        box4.setBounds(565f, 810f, 203f, 203f)

        val cbg = ACheckBoxGroup()
        box2.checkBoxGroup = cbg
        box3.checkBoxGroup = cbg
        box4.checkBoxGroup = cbg

        box2.check(false)

        box2.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockCountSkaters(2)
                createAItemPlayer(2)
            }
        }
        box3.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockCountSkaters(3)
                createAItemPlayer(3)
            }
        }
        box4.setOnCheckListener { isCheck ->
            if (isCheck) {
                blockCountSkaters(4)
                createAItemPlayer(4)
            }
        }
    }

    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 0f, 768f, 343f)

        verticalGroup.addActorsToVerticalGroup()
    }

    private fun addLblCount() {
        addActor(lblCount)
        lblCount.setBounds(447f, 656f, 310f, 92f)
        lblCount.setAlignment(Align.center)

        lblCount.setOnTouchListener(gdxGame.soundUtil) {
            Gdx.input.getTextInput(object : Input.TextInputListener {
                override fun input(text: String) {
                    val number = text.isNumTake(2)

                    if (number in 2..12) {
                        lblCount.setText(number)
                        createAItemPlayer(number)
                    }
                }

                override fun canceled() {}
            }, "Enter number of skaters:", "", "", Input.OnscreenKeyboardType.NumberPad)
        }
    }

    // Actors VerticalGroup------------------------------------------------------------------------

    private fun AVerticalGroup.addActorsToVerticalGroup() {
        setSize(768f, 343f)
        createAItemPlayer(2)
    }

    // Logic ---------------------------------------------------------------

    private fun createAItemPlayer(count: Int) {
        runGDX {
            verticalGroup.disposeAndClearChildren()

            repeat(count) {
                val aItemPlayer = AItemPlayer(screen, it.inc(), ls43)
                aItemPlayer.setSize(767f, 135f)
                verticalGroup.addActor(aItemPlayer)
            }
        }
    }

    fun getListPlayerName(): List<String> = verticalGroup.children.map { (it as AItemPlayer).lblTitle.text.toString() }

}