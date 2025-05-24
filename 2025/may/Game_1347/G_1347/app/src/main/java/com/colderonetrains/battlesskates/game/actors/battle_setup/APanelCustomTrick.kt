package com.colderonetrains.battlesskates.game.actors.battle_setup

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.colderonetrains.battlesskates.game.actors.AScrollPane
import com.colderonetrains.battlesskates.game.actors.autoLayout.AHorizontalGroup
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.checkbox.ATextCheckBox
import com.colderonetrains.battlesskates.game.utils.*
import com.colderonetrains.battlesskates.game.utils.actor.*
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter

class APanelCustomTrick(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font43        = screen.fontGenerator_GTWalsheimPro_Regular.generateFont(fontParameter.setSize(43))

    private val ls43 = Label.LabelStyle(font43, GameColor.white_43)

    private val btnEnterCustom = AButton(screen, AButton.Type.EnterCustom)

    private val horizontalGroup = AHorizontalGroup(screen, 30f, isWrapHorizontal = true)
    private val scroll          = AScrollPane(horizontalGroup)

    val listCustomTrick = mutableListOf<String>()

    override fun addActorsOnGroup() {
        addAndFillActor(btnEnterCustom)

        btnEnterCustom.setOnTouchListener(gdxGame.soundUtil) {
            btnEnterCustom.disable()
            btnEnterCustom.animHide(0.25f) {
                scroll.animShow(0.3f) {
                    scroll.enable()
                }
            }
        }

        addScroll()
    }

    private fun addScroll() {
        addAndFillActor(scroll)
        scroll.color.a = 0f
        scroll.disable()

        horizontalGroup.addActorsToHorizontalGroup()
    }

    private fun AHorizontalGroup.addActorsToHorizontalGroup() {
        setSize(width, height)

        runGDX {
            (GLOBAL_listDataTrick_Beginner + GLOBAL_listDataTrick_Intermediate + GLOBAL_listDataTrick_Pro).forEachIndexed { index, trick ->
                val box = ATextCheckBox(screen, shortenText(trick.nName), ls43)
                box.setSize(301f, 102f)
                addActor(box)

                box.setOnCheckListener { isCheck ->
                    if (isCheck) listCustomTrick.add(trick.nName) else listCustomTrick.remove(trick.nName)
                }
            }
        }
    }

    // Logic --------------------------------------------------------------

    private fun shortenText(text: String): String {
        return if (text.length <= 10) {
            text
        } else {
            text.take(10).trimEnd() + "..."
        }
    }

}