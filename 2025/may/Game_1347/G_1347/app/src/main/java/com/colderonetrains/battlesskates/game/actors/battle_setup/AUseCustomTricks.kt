package com.colderonetrains.battlesskates.game.actors.battle_setup

import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.util.log

class AUseCustomTricks(
    override val screen: AdvancedScreen,
): AVerticalGroup(screen, 48f, isWrap = true) {

    private val boxUseCustomTricks = ACheckBox(screen, ACheckBox.Type.UseCustomTricks)
    private val aPanelCustomTrick  = APanelCustomTrick(screen)

    var blockIsUseCustomTricks: (Boolean) -> Unit = {}

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        boxUseCustomTricks.setSize(width, height)
        addActor(boxUseCustomTricks)

        boxUseCustomTricks.setOnCheckListener { isCheck ->
            blockIsUseCustomTricks(isCheck)

            if (isCheck) {
                aPanelCustomTrick.setSize(766f, 102f)
                addActor(aPanelCustomTrick)
            } else {
                aPanelCustomTrick.remove()
            }
        }

        aPanelCustomTrick.isDisposeOnRemove = false
    }

    // Logic -------------------------------------------------------

    fun getListCustomTrick(): List<String> = aPanelCustomTrick.listCustomTrick

}