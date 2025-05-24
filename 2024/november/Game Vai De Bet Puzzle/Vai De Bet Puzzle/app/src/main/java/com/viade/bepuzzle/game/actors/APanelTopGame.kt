package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.actors.checkbox.ACheckBox
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.utils.SizeScaler
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.font.FontParameter
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class APanelTopGame(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1080f)

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS.chars + ":")
        .setSize(70)

    private val font70 = screen.fontGenerator_Karantina.generateFont(parameter)

    private val ls70 = LabelStyle(font70, Color.BLACK)

    private val imgPanel = Image(screen.game.assetsAll.top_panel)
    private val lblTime  = Label("", ls70)
    private val btnHome  = AButton(screen, AButton.Type.Home)
    private val boxPause = ACheckBox(screen, ACheckBox.Type.PAUSE)

    // Field
    var blockPause: (Boolean) -> Unit = {}
    var blockTimeOut = {}

    var tMinutes    = 5
    var isStopTimer = false

    override fun addActorsOnGroup() {
        addImgPanel()
        addLblTime()
        addBtnHome()
        addBoxPause()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addAndFillActor(imgPanel)
    }

    private fun addLblTime() {
        addActor(lblTime)
        lblTime.setBoundsScaled(483f, 123f, 113f, 90f)
        lblTime.setAlignment(Align.center)

        coroutine?.launch {
            var tSeconds = 0

            while (isActive) {
                if (isStopTimer.not()) {
                    delay(1_000)

                    // Зменшуємо секунди
                    if (tSeconds == 0) {
                        if (tMinutes == 0) {
                            runGDX {
                                lblTime.setText("00:00") // Якщо таймер досяг 0, показуємо 00:00 і зупиняємо
                                blockTimeOut()
                            }
                            break
                        } else {
                            tMinutes--
                            tSeconds = 59
                        }
                    } else {
                        tSeconds--
                    }

                    // Форматуємо хвилини і секунди з двома символами
                    val formattedTime = String.format("%02d:%02d", tMinutes, tSeconds)
                    runGDX { lblTime.setText(formattedTime) }
                }
            }

        }
    }

    private fun addBtnHome() {
        addActor(btnHome)
        btnHome.setBoundsScaled(21f, 107f, 105f, 105f)

        btnHome.setOnClickListener {
            screen.hideScreen {
                screen.game.navigationManager.clearBackStack()
                screen.game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }

    }

    private fun addBoxPause() {
        addActor(boxPause)
        boxPause.setBoundsScaled(942f, 114f, 97f, 97f)

        boxPause.setOnCheckListener {
            isStopTimer = it
            blockPause(it)
        }
    }

}