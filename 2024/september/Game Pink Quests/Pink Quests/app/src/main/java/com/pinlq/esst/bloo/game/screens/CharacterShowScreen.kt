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
import kotlinx.coroutines.launch

class CharacterShowScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var CHARACTER_INDEX = 0
            private set
    }

    private val fontParameter = FontParameter()
    private val font100       = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"+").setBorder(3f, GColor.redDark).setSize(100))
    private val font64        = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setBorder(0f, Color.WHITE).setSize(64))
    private val font40        = fontGenerator_Jost_Regular.generateFont(fontParameter.setSize(40))

    private val ls100 = Label.LabelStyle(font100, Color.WHITE)
    private val ls64  = Label.LabelStyle(font64, GColor.redLight)
    private val ls40  = Label.LabelStyle(font40, GColor.redDark)

    private val randomNum: Int get() = (1..9).random()
    private val randomCharIndex = (0..11).random()

    private val imgCard  = Image(game.all.character_show_card)
    private val imgChar  = Image(game.all.listChars[randomCharIndex])
    private val lblLeft  = Label("+$randomNum", ls100)
    private val lblRight = Label("+$randomNum", ls100)
    private val lblTitle = Label(appContext.resources.getStringArray(R.array.char_names)[randomCharIndex], ls64)
    private val lblDesc  = Label(appContext.resources.getStringArray(R.array.char_descs)[randomCharIndex], ls40)


    override fun show() {
        CHARACTER_INDEX = randomCharIndex
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
                game.navigationManager.navigate(SubjectScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgChar() {
        addActor(imgChar)
        imgChar.setBounds(183f,753f,713f,612f)
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
        addActors(lblLeft, lblRight, lblTitle, lblDesc)
        lblLeft.apply {
            setAlignment(Align.center)
            setBounds(185f,1393f,106f,158f)
        }
        lblRight.apply {
            setAlignment(Align.center)
            setBounds(794f,1390f,106f,158f)
        }
        lblTitle.apply {
            setAlignment(Align.center)
            setBounds(217f,679f,646f,46f)
        }
        lblDesc.apply {
            wrap = true
            setAlignment(Align.center)
            setBounds(155f,445f,770f,192f)
        }
    }

}