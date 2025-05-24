package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.autoLayout.AHorizontalGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AScrollerAchive(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val horizontalGroup = AHorizontalGroup(screen, 33f, paddingLeft = 61f, paddingRight = 61f, isWrapHorizontal = true)
    private val scroll          = AScrollPane(horizontalGroup)

    private val listImgAchive = listOf(
        gdxGame.assetsAll.ACHIVE_COMBO_MASTER,
        gdxGame.assetsAll.ACHIVE_BATTLE_CHAMPION,
    )

    override fun addActorsOnGroup() {
        addAndFillActor(scroll)
        horizontalGroup.addActorsToHorizontalGroup()
    }

    private fun AHorizontalGroup.addActorsToHorizontalGroup() {
        setSize(width, height)

        listImgAchive.forEachIndexed { index, achive ->
            val img = Image(achive)
            img.setSize(629f, 274f)
            addActor(img)
        }
    }

}