package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pinlq.esst.bloo.R
import com.pinlq.esst.bloo.appContext
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.utils.*
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.font.FontParameter

class SubjectShowScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var SUBJECT_INDEX = 0
            private set
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font64        = fontGenerator_Jost_SemiBold.generateFont(fontParameter.setSize(64))
    private val font48        = fontGenerator_Jost_Regular.generateFont(fontParameter.setSize(48))

    private val ls64  = Label.LabelStyle(font64, Color.BLACK)
    private val ls48  = Label.LabelStyle(font48, Color.BLACK)

    private val randomNum: Int get() = (1..10).random()
    private val randomCharIndex = (0..31).random()

    private val imgCard  = Image(game.all.white_card)
    private val imgChar  = Image(game.all.listItems[randomCharIndex])
    private val lblTitle = Label(appContext.resources.getStringArray(R.array.subject_names)[randomCharIndex], ls64)
    private val lblDesc  = Label("+ $randomNum " + appContext.resources.getStringArray(R.array.subject_descs).random(), ls48)


    override fun show() {
        SUBJECT_INDEX = randomCharIndex
        setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgCard()
        addImgChar()
        addLbls()

        stageUI.root.children.onEach { it.disable() }
        imgCard.enable()
    }

    private fun AdvancedStage.addImgCard() {
        addActor(imgCard)
        imgCard.setBounds(39f,252f,1000f,1400f)
        imgCard.setOnClickListener {
            game.soundUtil.apply { play(CARD, 0.5f) }

            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(QuestScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgChar() {
        addActor(imgChar)
        imgChar.setBounds(167f,827f,744f,744f)
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
    }

    private fun AdvancedStage.addLbls() {
        addActors(lblTitle, lblDesc)
        lblTitle.apply {
            setAlignment(Align.center)
            setBounds(247f,721f,585f,88f)
        }
        lblDesc.apply {
            setAlignment(Align.center)
            setBounds(198f,612f,683f,88f)
        }
    }

}