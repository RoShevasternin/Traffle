package com.fontantwo.tennisfortwo.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fontantwo.tennisfortwo.game.actors.button.AButton
import com.fontantwo.tennisfortwo.game.utils.GameColor
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen
import com.fontantwo.tennisfortwo.game.utils.font.FontParameter

class APanelRules(override val screen: AdvancedScreen): AdvancedGroup() {

    private val text = "Colorful Goodies is a fun, engaging game for two players. Choose a space  tennis racket and start the match. Hit your friend's goodies to prevent  them from reaching your goal while aiming to score points by getting  the goodies into your friend's goal. Enjoy a delightful time with  friends!"

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(40)
    private val font      = screen.fontGenerator_Langar.generateFont(parameter)

    private val cosmoStolImg = Image(screen.game.assetsAll.COSMO_STOL)
    private val textLbl      = Label(text, Label.LabelStyle(font, GameColor.whiter))

    val backBtn = AButton(screen, AButton.Static.Type.Back)

    override fun addActorsOnGroup() {
        addRules()
        addBack()
    }

    // Actors ------------------------------------------------------------------------

    private fun addRules() {
        addActors(cosmoStolImg, textLbl)
        cosmoStolImg.setBounds(90f, 152f, 1227f, 615f)
        textLbl.apply {
            setBounds(331f, 264f, 813f, 392f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    private fun addBack() {
        addActor(backBtn)
        backBtn.setBounds(1407f, 22f, 133f, 126f)
    }

}