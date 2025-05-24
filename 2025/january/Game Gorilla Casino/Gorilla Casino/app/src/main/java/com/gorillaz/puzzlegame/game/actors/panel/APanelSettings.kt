package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.checkbox.ACheckBox
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var isVibration = true
            private set
    }

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SETTINGS)
    private val listBox  = List(3) { ACheckBox(screen, ACheckBox.Type.DEF) }

    override fun addActorsOnGroup() {
        addImgPanel()
        addListBox()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(0f, 0f, 649f, 662f)
    }

    private fun addListBox() {
        var ny = 459f
        listBox.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(670f, ny, 220f, 170f)
            ny -= 170 + 42

            box.setOnCheckListener { isCheck ->
                when(index) {
                    0 -> { // Music

                    }
                    1 -> { // Sound

                    }
                    2 -> { // Vibration
                        isVibration = isCheck
                    }
                }
            }
        }

        //if (isMusic) listBox[0].check(false)
        //if (isSound) listBox[1].check(false)
        if (isVibration) listBox[2].check(false)
    }

}