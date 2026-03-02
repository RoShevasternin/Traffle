package com.monkeystreet.roadracejungle.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.monkeystreet.roadracejungle.game.actors.AScrollPane
import com.monkeystreet.roadracejungle.game.actors.ATmpGroup
import com.monkeystreet.roadracejungle.game.actors.AVerticalGroup
import com.monkeystreet.roadracejungle.game.actors.button.AButton
import com.monkeystreet.roadracejungle.game.utils.Block
import com.monkeystreet.roadracejungle.game.utils.HEIGHT_UI
import com.monkeystreet.roadracejungle.game.utils.TIME_ANIM_SCREEN
import com.monkeystreet.roadracejungle.game.utils.WIDTH_UI
import com.monkeystreet.roadracejungle.game.utils.actor.HAlign
import com.monkeystreet.roadracejungle.game.utils.actor.VAlign
import com.monkeystreet.roadracejungle.game.utils.actor.addActorAligned
import com.monkeystreet.roadracejungle.game.utils.actor.addActors
import com.monkeystreet.roadracejungle.game.utils.actor.animDelay
import com.monkeystreet.roadracejungle.game.utils.actor.animHide
import com.monkeystreet.roadracejungle.game.utils.actor.animShow
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.font.FontParameter
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class LeaderboardScreen: AdvancedScreen() {

    // Field

    private val listName = listOf(
        "Momo",
        "Coco",
        "Bongo",
        "Kiki",
        "Zippy",
        "Peanut",
        "Banjo",
        "Chippy",
        "Pogo",
        "Snickers",
        "Gizmo",
        "Nibbles",
        "Toffee",
        "Milo",
        "Zuzu",
        "BamBam",
        "Swingy",
        "Jungle",
        "Beanie",
        "Tiki",
    ).shuffled()
    private val listRecord = List(listName.size) { (3..99).random() }.sortedDescending()

    // Font

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font70    = fontGenerator_GochiHand_Regular.generateFont(parameter.setSize(70))
    private val font50    = fontGenerator_GochiHand_Regular.generateFont(parameter.setSize(50))

    // Actor

    private val aPANEL = ATmpGroup(this)

    private val lblRecord = Label("${gdxGame.ds_Record.flow.value}", Label.LabelStyle(font70, Color.WHITE))

    private val imgLEADERBOARD = Image(gdxGame.assetsAll.LEADERBOARD)

    private val btnMenu = AButton(this, AButton.Type.ToMenu)

    private val listImgCook   = List(listName.size) { Image(gdxGame.assetsAll.MINI) }
    private val listLblRecord = List(listName.size) { Label("${listName[it]}: ${listRecord[it]}", Label.LabelStyle(font50, Color.WHITE)) }

    private val verticalGroup = AVerticalGroup(this, space = 23f, isWrap = true)
    private val scrollPane    = AScrollPane(verticalGroup)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_LEADER)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        aPANEL.setSize(WIDTH_UI, HEIGHT_UI)
        addActorAligned(aPANEL, HAlign.CENTER, VAlign.CENTER)
        aPANEL.apply {
            addImgLEADERBOARD()
            addLblRecord()
            addBtnMenu()
            addScrollPane()
        }

        animShow()
    }

    override fun animShow(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------


    private fun Group.addImgLEADERBOARD() {
        addActor(imgLEADERBOARD)
        imgLEADERBOARD.setBounds(148f, 351f, 785f, 1528f)
    }

    private fun Group.addLblRecord() {
        addActor(lblRecord)
        lblRecord.setBounds(519f, 1668f, 44f, 84f)
        lblRecord.setAlignment(Align.center)
    }

    private fun Group.addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(350f, 121f, 379f, 145f)
        btnMenu.setOnClickListener { animHide { gdxGame.navigationManager.back() } }
    }

    private fun Group.addScrollPane() {
        addActor(scrollPane)
        scrollPane.setBounds(229f, 445f, 609f, 906f)

        verticalGroup.setSize(scrollPane.width, scrollPane.height)

        listImgCook.forEachIndexed { index, image ->
            val tmpGroup = ATmpGroup(this@LeaderboardScreen)
            tmpGroup.setSize(609f, 132f)

            verticalGroup.addActor(tmpGroup)
            //tmpGroup.debug()

            image.setBounds(0f, 0f, 140f, 132f)
            val lblRecord = listLblRecord[index]
            lblRecord.setBounds(145f, 36f, 253f, 60f)

            tmpGroup.addActors(image, lblRecord)
        }
    }
}