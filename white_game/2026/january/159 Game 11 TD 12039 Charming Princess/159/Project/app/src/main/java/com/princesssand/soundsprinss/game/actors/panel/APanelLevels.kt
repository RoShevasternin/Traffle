package com.princesssand.soundsprinss.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.princesssand.soundsprinss.game.actors.ATutorials
import com.princesssand.soundsprinss.game.actors.button.AButton
import com.princesssand.soundsprinss.game.actors.button.AButtonLVL
import com.princesssand.soundsprinss.game.screens.GameScreen
import com.princesssand.soundsprinss.game.screens.LevelsScreen
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen
import com.princesssand.soundsprinss.game.utils.font.FontParameter

class APanelLevels(override val screen: AdvancedScreen): AdvancedGroup() {
    companion object {
        var LEVEL_INDEX = 0
            private set

        val targetList = listOf(
            100, 200, 350, 470, 525,
            647, 777, 890, 969, 1000
        )
    }

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "/")
    private val font72    = screen.fontGenerator_Merienda.generateFont(parameter.setSize(72))
    private val font30    = screen.fontGenerator_Merienda.generateFont(parameter.setSize(30))

    private val labelStyle72 = LabelStyle(font72, Color.WHITE)
    private val labelStyle30 = LabelStyle(font30, Color.WHITE)

    // Actor
    private val panel = Image(screen.game.assetsAll.levels_panel)
    private val back  = AButton(screen, AButton.Static.Type.Back)

    private val btnList = List(10) {
        AButtonLVL(
            screen,
            labelStyle72,
            labelStyle30,
            it.inc(),
            screen.game.resultUtil.resultList[it],
            targetList[it]

        )
    }

    // Field
    var hideBlock: (() -> Unit) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(panel)
        addBack()
        addBtnList()
    }

    // Add Actors
    private fun addBack() {
        addActor(back)
        back.setBounds(667f, 27f, 98f, 58f)
        back.setOnClickListener {
            hideBlock { screen.game.navigationManager.back() }
        }
    }

    private fun addBtnList() {
        var nx = 78f
        var ny = 450f
        btnList.onEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(nx, ny, 199f, 199f)
            nx += 70+199
            if (index.inc() % 5 == 0) {
                nx = 78f
                ny -= 100+199
            }

            btn.setOnClickListener {
                if (screen.game.isTutorialsUtil.isTutorials) ATutorials.nextStep()

                LEVEL_INDEX = index
                hideBlock {
                    screen.game.navigationManager.navigate(GameScreen::class.java.name, LevelsScreen::class.java.name)
                }
            }
        }

        btnList.take(screen.game.levelUtil.level).onEach { it.unlock() }
    }

}