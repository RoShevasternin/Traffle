package com.fontantwo.tennisfortwo.game.actors.panel

import com.fontantwo.tennisfortwo.game.actors.button.AButton
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen

class APanelMenu(override val screen: AdvancedScreen): AdvancedGroup() {

    val rulesBtn = AButton(screen, AButton.Static.Type.Ruls)
    val playBtn  = AButton(screen, AButton.Static.Type.Play)
    val settBtn  = AButton(screen, AButton.Static.Type.Sett)
    val exitBtn  = AButton(screen, AButton.Static.Type.Ext)

    override fun addActorsOnGroup() {
        addBtns()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtns() {
        addActors(rulesBtn, playBtn, settBtn, exitBtn)
        rulesBtn.setBounds(334f, 345f, 242f, 229f)
        playBtn.setBounds(635f, 320f, 294f, 278f)
        settBtn.setBounds(988f, 345f, 242f, 229f)
        exitBtn.setBounds(1407f, 22f, 133f, 126f)
    }

}