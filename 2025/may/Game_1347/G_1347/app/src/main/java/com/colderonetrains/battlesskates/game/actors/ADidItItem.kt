package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ADidItItem(
    override val screen: AdvancedScreen,
    val nName    : String,
    val lsBold43 : Label.LabelStyle,
): AdvancedGroup() {

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    private val imgPanel = Image(gdxGame.assetsAll.ACHIVE)
    private val lblTitle = Label(nName, lsBold43)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblTitle()
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(133f, 29f, 297f, 50f)
    }

}