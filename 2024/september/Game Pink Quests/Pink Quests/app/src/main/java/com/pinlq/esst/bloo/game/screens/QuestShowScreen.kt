package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pinlq.esst.bloo.R
import com.pinlq.esst.bloo.appContext
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.utils.*
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.font.FontParameter

class QuestShowScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font64        = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setSize(64))
    private val font65        = fontGenerator_Jost_Regular.generateFont(fontParameter.setSize(55).setSpaceY(10))

    private val ls64  = Label.LabelStyle(font64, GColor.redLight)
    private val ls65  = Label.LabelStyle(font65, GColor.redDark)

    private val randomQuestIndex = (0..19).random()

    private val imgCard  = Image(game.all.white_card)
    private val imgChar  = Image(game.all.listChars[CharacterShowScreen.CHARACTER_INDEX])
    private val imgSubj  = Image(game.all.listItems[SubjectShowScreen.SUBJECT_INDEX])
    private val lblTitle = Label(appContext.resources.getStringArray(R.array.quest_names)[randomQuestIndex], ls64)
    private val lblDesc  = Label(appContext.resources.getStringArray(R.array.quest_descs)[randomQuestIndex], ls65)
    private val btnNext  = AButton(this, AButton.Static.Type.Next)

    override fun show() {
          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgCard()
        addBtnNext()
        addImgCharSubj()
        addLbls()
    }

    private fun AdvancedStage.addImgCard() {
        addActor(imgCard)
        imgCard.setBounds(39f,252f,1000f,1400f)
    }

    private fun AdvancedStage.addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(298f,52f,484f,239f)
        btnNext.setOnClickListener(null) {
            val players = SelectPlayersScreen.listPlayerRegionIndex
            val current = ShakeScreen.CURRENT_USER_INDEX
            ShakeScreen.CURRENT_USER_INDEX = if (current + 1 > players.lastIndex) 0 else current + 1
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.soundUtil.apply { play(WIN) }
                game.navigationManager.navigate(ShakeScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgCharSubj() {
        addActors(imgChar, imgSubj)
        imgChar.setBounds(171f,1249f,358f,308f)
        imgChar.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(
                Actions.forever(
                    Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.smooth),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.smooth),
                ))
            )
        }
        imgSubj.setBounds(600f,1249f,308f,308f)
        imgSubj.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(Actions.sequence(
                Actions.delay(0.5f),
                Actions.forever(
                    Actions.sequence(
                        Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.smooth),
                        Actions.scaleBy(scale, scale, 0.5f, Interpolation.smooth),
                    ))
            ))
        }
    }

    private fun AdvancedStage.addLbls() {
        addActors(lblTitle, lblDesc)
        lblTitle.apply {
            setAlignment(Align.center)
            setBounds(285f,1100f,510f,46f)
        }
        lblDesc.apply {
            wrap = true
            setAlignment(Align.top, Align.center)
            setBounds(114f,592f,850f,405f)
        }
    }

}