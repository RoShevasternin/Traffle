package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ALanded(override val screen: AdvancedScreen) : AdvancedGroup() {

    private val imgIcon = Image(gdxGame.assetsAll.LANDED)
    private val btnNext = AButton(screen, AButton.Type.NextPlayer)

    var blockNextPlayer = {}

    override fun addActorsOnGroup() {
        addActors(imgIcon, btnNext)
        imgIcon.setBounds(41f, 1076f, 998f, 413f)
        btnNext.setBounds(332f, 835f, 416f, 102f)

        btnNext.setOnClickListener { blockNextPlayer() }
    }

}