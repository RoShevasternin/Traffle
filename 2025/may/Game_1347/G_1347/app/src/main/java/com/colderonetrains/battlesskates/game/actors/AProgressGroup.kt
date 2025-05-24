package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.progress.AProgress
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame
import kotlin.math.roundToInt

class AProgressGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font43        = screen.fontGenerator_GTWalsheimPro_Regular.generateFont(fontParameter.setSize(43))

    private val ls43 = Label.LabelStyle(font43, GameColor.white_D3)

    private val valueTotal        = gdxGame.ds_DataDidItTrick.flow.value.count()
    private val valueBeginner     = gdxGame.ds_DataDidItTrick.flow.value.count { it.type == LevelType.Beginner }
    private val valueIntermediate = gdxGame.ds_DataDidItTrick.flow.value.count { it.type == LevelType.Intermediate }
    private val valuePro          = gdxGame.ds_DataDidItTrick.flow.value.count { it.type == LevelType.Pro }

    private val percentTotal        = ((valueTotal / 30f) * 100f).roundToInt()
    private val percentBeginner     = ((valueBeginner / 10f) * 100f).roundToInt()
    private val percentIntermediate = ((valueIntermediate / 10f) * 100f).roundToInt()
    private val percentPro          = ((valuePro / 10f) * 100f).roundToInt()

    private val lblTotal             = Label("$valueTotal/30", ls43)
    private val lblBeginner          = Label("$percentBeginner%", ls43)
    private val lblIntermediate      = Label("$percentIntermediate%", ls43)
    private val lblPro               = Label("$percentPro%", ls43)
    private val progressTotal        = AProgress(screen)
    private val progressBeginner     = AProgress(screen)
    private val progressIntermediate = AProgress(screen)
    private val progressPro          = AProgress(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(Image(gdxGame.assetsAll.YOUR_PROGRESS))
        addProgresses()
        addLbls()
    }

    private fun addProgresses() {
        addActors(progressTotal, progressBeginner, progressIntermediate, progressPro)
        progressTotal.apply {
            setBounds(5f, 478f, 920f, 22f)
            progressPercentFlow.value = percentTotal.toFloat()
        }
        progressBeginner.apply {
            setBounds(5f, 320f, 920f, 22f)
            progressPercentFlow.value = percentBeginner.toFloat()
        }
        progressIntermediate.apply {
            setBounds(5f, 162f, 920f, 22f)
            progressPercentFlow.value = percentIntermediate.toFloat()
        }
        progressPro.apply {
            setBounds(5f, 4f, 920f, 22f)
            progressPercentFlow.value = percentPro.toFloat()
        }
    }

    private fun addLbls() {
        addActors(lblTotal, lblBeginner, lblIntermediate, lblPro)
        lblTotal.apply {
            setBounds(5f, 540f, 920f, 22f)
            setAlignment(Align.right)
        }
        lblBeginner.apply {
            setBounds(5f, 382f, 920f, 22f)
            setAlignment(Align.right)
        }
        lblIntermediate.apply {
            setBounds(5f, 224f, 920f, 22f)
            setAlignment(Align.right)
        }
        lblPro.apply {
            setBounds(5f, 66f, 920f, 22f)
            setAlignment(Align.right)
        }
    }

}