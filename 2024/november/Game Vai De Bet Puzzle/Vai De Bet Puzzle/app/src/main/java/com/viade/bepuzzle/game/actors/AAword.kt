package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.GameColor
import com.viade.bepuzzle.game.utils.actor.disable
import com.viade.bepuzzle.game.utils.actor.enable
import com.viade.bepuzzle.game.utils.actor.setOnClickListener
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.font.FontParameter

class AAword(override val screen: AdvancedScreen, private val aIndex: Int, private val sum: Int): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS.chars + "COLLECT GAME COINS")
        .setSize(60)

    private val font60 = screen.fontGenerator_Karantina.generateFont(parameter)

    private val ls60 = LabelStyle(font60, GameColor.f9)

    private val imgPanel = Image(screen.game.assetsAll.aword)
    private val lblAword = Label("COLLECT $sum GAME COINS", ls60)
    private val btnGet   = AButton(screen, AButton.Type.Get)
    private val imgGeted = Image(screen.game.assetsAll.geted)

    // Field
    private val isAvailable = screen.game.dataStoreAwordsUtil.awordsFlow.value[aIndex]
    private val balance     = screen.game.dataStoreBalanceUtil.balanceFlow.value

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblAword()

        if (isAvailable) addBtnGet() else addImgGeted()
    }

    private fun addLblAword() {
        addActor(lblAword)
        lblAword.setBounds(42f, 203f, 367f, 61f)
    }

    private fun addBtnGet() {
        addActor(btnGet)
        btnGet.setBounds(242f, 27f, 222f, 138f)

        if (isAvailable && balance >= sum) btnGet.enable() else btnGet.disable()

        btnGet.setOnClickListener {
            screen.game.soundUtil.apply { play(buy) }

            btnGet.disable()
            addImgGeted()
            screen.game.dataStoreAwordsUtil.update { it.toMutableList().apply { set(aIndex, false) } }
            screen.game.dataStoreBalanceUtil.update { it + 100 }
        }
    }

    private fun addImgGeted() {
        addActor(imgGeted)
        imgGeted.setBounds(242f, 27f, 222f, 138f)
    }

}