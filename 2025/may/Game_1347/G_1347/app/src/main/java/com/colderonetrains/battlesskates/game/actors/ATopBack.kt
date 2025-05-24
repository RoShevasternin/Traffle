package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ATopBack(
    override val screen: AdvancedScreen,
    title: String
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font60        = screen.fontGenerator_AvenirNextRoundedStd_DemiItalic.generateFont(fontParameter.setSize(60))

    private val ls60 = Label.LabelStyle(font60, Color.WHITE)

    private val lblTitle = Label(title, ls60)
    private val btnBack  = AButton(screen, AButton.Type.Back)

    override fun addActorsOnGroup() {
        addActors(lblTitle, btnBack)
        lblTitle.setBounds(137f, 21f, 354f, 74f)
        btnBack.apply {
            setBounds(0f, 0f, 115f, 124f)
            setOnClickListener {
                screen.hideScreen {
                    gdxGame.navigationManager.back()
                }
            }
        }
    }

}