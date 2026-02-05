/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.button.InteractiveButton
import com.novaburst.pixelrally.game.actors.button.IconButton
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelBet(
    override val screen: DisplayScreen,
    currentLocationIndex: Int,
): ComponentGroup() {

    private val listBet = BetType.entries[currentLocationIndex].listBet

    private val parameter70 = TypefaceConfig().setCharacters(TypefaceConfig.CharType.NUMBERS).setSize(70)

    private val font70 = screen.fontGenerator_Regular.generateFont(parameter70)

    private val ls70 = Label.LabelStyle(font70, ColorPalette.black_26)

    var currentBetIndex = 0
        private set

    var currentBet = listBet[currentBetIndex]
        private set

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_BET)
    private val lblBet    = Label(currentBet.toString(),ls70 )
    private val btnMinus = InteractiveButton(screen, InteractiveButton.Type.Minus)
    private val btnPlus = InteractiveButton(screen, InteractiveButton.Type.Plus)

    // Handler method
    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblBet()
        addBtnMinusPlus()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblBet() {
        addActor(lblBet)
        lblBet.setBounds(173f, 59f, 249f, 61f)
        lblBet.setAlignment(Align.center)
    }

    // Handler method
    private fun addBtnMinusPlus() {
        addActors(btnMinus, btnPlus)
        btnMinus.apply {
            setBounds(66f, 63f, 113f, 94f)
            setOnClickListener { handlerMinus() }
        }
        btnPlus.apply {
            setBounds(422f, 63f, 113f, 94f)
            setOnClickListener { handlerPlus() }
        }
    }

    // Logic --------------------------------------------------------------------------

    private fun handlerMinus() {
        if (currentBetIndex - 1 >= 0) {
            currentBetIndex -= 1
        } else {
            currentBetIndex = listBet.lastIndex
        }

        nextBet()
    }

    private fun handlerPlus() {
        if (currentBetIndex + 1 <= listBet.lastIndex) {
            currentBetIndex += 1
        } else {
            currentBetIndex = 0
        }

        nextBet()
    }

    private fun nextBet() {
        currentBet = listBet[currentBetIndex]
        lblBet.setText(currentBet)
    }

    fun disableBtns() {
        btnMinus.disable()
        btnPlus.disable()
    }
    fun enableBtns() {
        btnMinus.enable()
        btnPlus.enable()
    }

    enum class BetType(
        val listBet: List<Int>,
    ) {
        _1(listOf(100   , 250   , 500   , 750   , 1000                   )),
        _2(listOf(500   , 1000  , 2500  , 3000  , 4500  , 5000           )),
        _3(listOf(1000  , 2500  , 5000  , 7500  , 8500  , 9000  , 10_000 )),
        _4(listOf(2500  , 5000  , 8000  , 10_000, 12_500, 15_000, 20_000 )),
        _5(listOf(5000  , 10_000, 15_000, 20_000, 25_000, 30_000         )),
        _6(listOf(7500  , 12_500, 20_000, 27_000, 35_000, 40_000         )),
        _7(listOf(10_000, 20_000, 30_000, 40_000, 50_000                 )),
    }

}