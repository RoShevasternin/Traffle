package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.AButton
import com.pinlq.esst.bloo.game.actors.ACollectionCard
import com.pinlq.esst.bloo.game.actors.layout.AHorizontalGroup
import com.pinlq.esst.bloo.game.utils.GColor
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHideScreen
import com.pinlq.esst.bloo.game.utils.actor.animShowScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage
import com.pinlq.esst.bloo.game.utils.font.FontParameter
import com.pinlq.esst.bloo.game.utils.region
import com.pinlq.esst.bloo.game.utils.runGDX
import kotlinx.coroutines.launch
import kotlin.math.truncate

class CollectionScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font100       = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"+").setBorder(3f, GColor.redDark).setSize(67))
    private val font64        = fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setBorder(0f, Color.WHITE).setSize(43))
    private val font40        = fontGenerator_Jost_Regular.generateFont(fontParameter.setSize(27))

    private val ls100 = Label.LabelStyle(font100, Color.WHITE)
    private val ls64  = Label.LabelStyle(font64, GColor.redLight)
    private val ls40  = Label.LabelStyle(font40, GColor.redDark)

    private val imgPanel  = Image(game.all.colle)
    private val btnBack   = AButton(this, AButton.Static.Type.Back)
    private val listCollection = List(11) { ACollectionCard(this, it, ls100, ls64, ls40) }

    private val aHorizontal = AHorizontalGroup(this, 76f, startGap = 50f, endGap = 50f, isWrapH = true)
    private val scroll = ScrollPane(aHorizontal)

    override fun show() {
        setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgPanel()
                addBtnBack()

                addImgBackground()
            }
        }
    }

    override fun dispose() {
        super.dispose()
        aHorizontal.dispose()
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(115f,1490f,850f,188f)
    }

    private fun AdvancedStage.addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(298f,114f,484f,239f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addImgBackground() {
        addActor(scroll)
        scroll.setBounds(0f, 447f, 1080f, 948f)

        listCollection.onEach {
            it.touchable = Touchable.disabled
            it.setSize(677f, 947f)
            aHorizontal.addActor(it)
        }
    }

}