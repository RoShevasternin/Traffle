package com.senqorvia774.lottomatica.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.senqorvia774.lottomatica.game.utils.actor.addActors
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedGroup
import com.senqorvia774.lottomatica.game.utils.advanced.AdvancedScreen
import com.senqorvia774.lottomatica.game.utils.gdxGame

class APanelShop(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aItem_1k_Img = Image(gdxGame.assetsAll.SHOP_1K)
    private val aItem_1m_Img = Image(gdxGame.assetsAll.SHOP_1M)

    override fun addActorsOnGroup() {
        addListMenuItemImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addListMenuItemImg() {
        addActors(aItem_1k_Img, aItem_1m_Img)
        aItem_1k_Img.setBounds(0f, 0f, 549f, 536f)
        aItem_1m_Img.setBounds(620f, 0f, 632f, 549f)
    }

}