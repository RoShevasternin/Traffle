package com.veldan.cosmolot.game.actors.slots.slot6x4

import com.veldan.cosmolot.game.actors.slots.SlotItem
import com.veldan.cosmolot.game.manager.SpriteManager

object SlotItemContainer {

    const val SLOT_ITEM_WILD_ID = 9

    val wild = SlotItem(SLOT_ITEM_WILD_ID, SpriteManager.GameRegion_2.WILD.region, 10.5f)

    val list = listOf<SlotItem>(
        SlotItem(1, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[0],1.5f),
        SlotItem(2, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[1],2.5f),
        SlotItem(3, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[2],3.5f),
        SlotItem(4, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[3],4.5f),
        SlotItem(5, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[4],5.5f),
        SlotItem(6, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[5],6.5f),
        SlotItem(7, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[6],7.5f),
        SlotItem(8, SpriteManager.ListRegion.GAME_2_ITEMS.regionList[7],8.5f),
    )

}