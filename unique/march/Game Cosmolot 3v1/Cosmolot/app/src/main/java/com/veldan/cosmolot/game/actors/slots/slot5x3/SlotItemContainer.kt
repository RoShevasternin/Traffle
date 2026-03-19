package com.veldan.cosmolot.game.actors.slots.slot5x3

import com.veldan.cosmolot.game.actors.slots.SlotItem
import com.veldan.cosmolot.game.manager.SpriteManager

object SlotItemContainer {

    const val SLOT_ITEM_WILD_ID = 9

    val wild = SlotItem(SLOT_ITEM_WILD_ID, SpriteManager.GameRegion_3.WILD.region, 15.7f)

    val list = listOf<SlotItem>(
        SlotItem(1, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[0],4.7f),
        SlotItem(2, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[1],5.7f),
        SlotItem(3, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[2],6.7f),
        SlotItem(4, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[3],7.7f),
        SlotItem(5, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[4],8.7f),
        SlotItem(6, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[5],9.7f),
        SlotItem(7, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[6],10.7f),
        SlotItem(8, SpriteManager.ListRegion.GAME_3_ITEMS.regionList[7],11.7f),
    )

}