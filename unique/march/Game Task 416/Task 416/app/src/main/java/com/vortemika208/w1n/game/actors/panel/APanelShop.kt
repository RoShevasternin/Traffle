package com.vortemika208.w1n.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.vortemika208.w1n.game.utils.actor.addActors
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.gdxGame

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
        aItem_1k_Img.setBounds(0f, 0f, 1052f, 1059f)
        aItem_1m_Img.setBounds(845f, 88f, 1059f, 1052f)
    }

}