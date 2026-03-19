package com.quenloria615.beton.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.quenloria615.beton.game.utils.actor.addActors
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.gdxGame

class APanelShop(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aItem_1k_Img = Image(gdxGame.assetsAll.SHOP_1K)
    private val aItem_5k_Img = Image(gdxGame.assetsAll.SHOP_5K)
    private val aItem_1m_Img = Image(gdxGame.assetsAll.SHOP_1M)

    override fun addActorsOnGroup() {
        addListMenuItemImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addListMenuItemImg() {
        addActors(aItem_1k_Img, aItem_5k_Img, aItem_1m_Img)
        aItem_1k_Img.setBounds(0f, 3f, 458f, 549f)
        aItem_5k_Img.setBounds(490f, 3f, 469f, 549f)
        aItem_1m_Img.setBounds(940f, 0f, 663f, 658f)
    }

}