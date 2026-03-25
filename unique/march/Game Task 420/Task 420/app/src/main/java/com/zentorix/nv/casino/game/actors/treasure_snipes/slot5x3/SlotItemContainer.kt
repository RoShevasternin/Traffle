package com.zentorix.nv.casino.game.actors.treasure_snipes.slot5x3

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.zentorix.nv.casino.game.actors.slots.SlotItem

class SlotItemContainer(private val itemRegions: List<TextureRegion>) {

    val list = List(12) { SlotItem(itemRegions[it]) }

}