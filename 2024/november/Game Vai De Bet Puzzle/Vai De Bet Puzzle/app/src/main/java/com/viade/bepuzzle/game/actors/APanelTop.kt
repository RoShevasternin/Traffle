package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.screens.InAppScreen
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.utils.SizeScaler
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.font.FontParameter
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.launch

class APanelTop(override val screen: AdvancedScreen): AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 1080f)

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(60)

    private val font60 = screen.fontGenerator_Karantina.generateFont(parameter)

    private val ls60 = LabelStyle(font60, Color.BLACK)

    private val imgPanel    = Image(screen.game.assetsAll.top_panel)
    private val lblBalance  = Label(screen.game.dataStoreBalanceUtil.balanceFlow.value.toString(), ls60)
    private val btnPlusCoin = AButton(screen, AButton.Type.PlusCoin)
    private val imgPlusCoin = Image(screen.game.assetsAll.plus_coin_simple)
    private val btnMenu     = AButton(screen, AButton.Type.Menu)
    private val btnBack     = AButton(screen, AButton.Type.Back)

    // Field

    var blockMenu = {}

    var isBtnBack  = false
    var isPlusCoin = true

    override fun addActorsOnGroup() {
        addImgPanel()
        addLblBalance()
        if (isPlusCoin) addBtnPlusCoin()
        if (isPlusCoin.not()) addImgPlusCoin()
        if (isBtnBack.not()) addBtnMenu()
        if (isBtnBack) addBtnBack()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addAndFillActor(imgPanel)
    }

    private fun addLblBalance() {
        addActor(lblBalance)
        lblBalance.setBoundsScaled(970f, 139f, 87f, 58f)

        coroutine?.launch {
            screen.game.dataStoreBalanceUtil.balanceFlow.collect { balance ->
                runGDX {
                    lblBalance.setText(balance.toString())
                }
            }
        }
    }

    private fun addImgPlusCoin() {
        addActor(imgPlusCoin)
        imgPlusCoin.setBoundsScaled(781f, 97f, 176f, 110f)
    }

    private fun addBtnPlusCoin() {
        addActor(btnPlusCoin)
        btnPlusCoin.setBoundsScaled(781f, 97f, 176f, 110f)

        btnPlusCoin.setOnClickListener {
            screen.hideScreen {
                screen.game.navigationManager.navigate(InAppScreen::class.java.name, screen::class.java.name)
            }
        }

    }

    private fun addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBoundsScaled(24f, 130f, 92f, 66f)

        btnMenu.setOnClickListener { blockMenu() }
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBoundsScaled(21f, 107f, 105f, 105f)

        btnBack.setOnClickListener {
            screen.hideScreen { screen.game.navigationManager.back() }
        }

    }

}