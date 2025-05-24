package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.autoLayout.AVerticalGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AScrollerProgress(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val verticalGroup = AVerticalGroup(screen, 84f, paddingBottom = 100f, isWrap = true)
    private val scroll        = AScrollPane(verticalGroup)

    private val aProgressGroup  = AProgressGroup(screen)
    private val imgAhive        = Image(gdxGame.assetsAll.ACHIVE_UNLOCK)
    private val aScrollerAchive = AScrollerAchive(screen)

    override fun addActorsOnGroup() {
        addAndFillActor(scroll)
        verticalGroup.addActorsToVerticalGroup()
    }

    private fun AVerticalGroup.addActorsToVerticalGroup() {
        setSize(width, height)

        aProgressGroup.setSize(931f, 682f)
        addActor(aProgressGroup)
        imgAhive.setSize(575f, 63f)
        addActor(imgAhive)
        aScrollerAchive.setSize(1080f, 274f)
        addActor(aScrollerAchive)
    }

}