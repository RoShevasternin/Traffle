package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.actors.checkbox.ACheckBox
import com.viade.bepuzzle.game.actors.checkbox.ACheckBoxGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class ASelectLVLofDiff(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listBox = List(3) { ACheckBox(screen, ACheckBox.Type.CHECK) }

    var blockCheck: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.assetsAll.level_of_diff))
        addBoxs()
    }

    private fun addBoxs() {
        var ny = 491f
        val cbg = ACheckBoxGroup()
        listBox.onEachIndexed { index, aCheckBox ->
            addActor(aCheckBox)
            aCheckBox.checkBoxGroup = cbg

            if (index == 0) aCheckBox.check(false)
            aCheckBox.setBounds(273f, ny, 80f, 80f)
            ny -= (101 + 80)

            aCheckBox.setOnCheckListener { isCheck ->
                if (isCheck) blockCheck(index)
            }
        }
    }

}