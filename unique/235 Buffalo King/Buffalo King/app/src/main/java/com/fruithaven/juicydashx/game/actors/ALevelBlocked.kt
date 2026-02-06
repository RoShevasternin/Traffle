package com.fruithaven.juicydashx.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.fruithaven.juicydashx.game.utils.GameColor
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedGroup
import com.fruithaven.juicydashx.game.utils.advanced.AdvancedScreen
import com.fruithaven.juicydashx.game.utils.font.FontParameter
import com.fruithaven.juicydashx.game.utils.gdxGame

class ALevelBlocked(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS.chars + "REACH LEVEL TO UNLOCK")
        .setSize(80)

    private val font80 = screen.fontGenerator_Regular.generateFont(parameter)
    private val ls80   = Label.LabelStyle(font80, Color.valueOf("FBC200"))

    private val imgPanel = Image(gdxGame.assetsAll.LOCATION_IS_BLOCKED)
    private val lblText  = Label("", ls80)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblText()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblText() {
        addActor(lblText)
        lblText.setBounds(279f, 11f, 529f, 81f)
        lblText.setAlignment(Align.center)
    }

    // Logic ----------------------------------------------------------------------

    fun updateLevel(level: Int) {
        lblText.setText("REACH LEVEL $level TO UNLOCK")
    }

}