package com.slotscity.official.game.actors.carnaval_cat.slot5x3

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.slotscity.official.game.actors.slots.SlotItem

class SlotItemContainer(private val itemRegions: List<TextureRegion>) {

    val wild = SlotItem(itemRegions[14])
    val list = List(14) { SlotItem(itemRegions[it]) }

}