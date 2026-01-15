package com.cosmicbounce.galaxytic.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedGroup
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen
import com.cosmicbounce.galaxytic.game.utils.font.FontParameter

class AInfo(override val screen: AdvancedScreen,
    val region: TextureRegion,
    title : String,
    text  : String,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font35        = screen.fontGenerator_Gugi.generateFont(fontParameter.setSize(35))
    private val font20        = screen.fontGenerator_Gugi.generateFont(fontParameter.setSize(20))

    private val imgA     = Image(region)
    private val lblTitle = Label(title, Label.LabelStyle(font35, Color.WHITE))
    private val lblText  = Label(text, Label.LabelStyle(font20, Color.WHITE))

    override fun addActorsOnGroup() {
        addActors(imgA, lblTitle, lblText)
        imgA.setBounds(181f, 400f, 218f, 218f)
        lblTitle.setBounds(166f, 340f, 249f, 44f)
        lblText.setBounds(0f, 0f, 580f, 325f)

        lblTitle.setAlignment(Align.center)
        lblText.wrap = true

    }

}