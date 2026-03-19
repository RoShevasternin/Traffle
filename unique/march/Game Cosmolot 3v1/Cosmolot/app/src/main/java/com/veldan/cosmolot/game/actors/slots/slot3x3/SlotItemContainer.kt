package com.veldan.cosmolot.game.actors.slots.slot3x3

import com.veldan.cosmolot.game.actors.slots.SlotItem
import com.veldan.cosmolot.game.manager.SpriteManager

object SlotItemContainer {

    const val SLOT_ITEM_WILD_ID = 10

    val wild = SlotItem(SLOT_ITEM_WILD_ID, SpriteManager.GameRegion_1.WILD.region, 10f)

    val list = listOf<SlotItem>(
        SlotItem(1, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[0],1f),
        SlotItem(2, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[1],2f),
        SlotItem(3, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[2],3f),
        SlotItem(4, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[3],4f),
        SlotItem(5, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[4],5f),
        SlotItem(6, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[5],6f),
        SlotItem(7, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[6],7f),
        SlotItem(8, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[7],8f),
        SlotItem(9, SpriteManager.ListRegion.GAME_1_ITEMS.regionList[8],9f),
    )

}