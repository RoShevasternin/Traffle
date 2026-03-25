package com.zentorix.nv.casino.game.actors.sweet_bonanza.slot6x5

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.zentorix.nv.casino.game.actors.slots.SlotItem

class SlotItemContainer(private val itemRegions: List<TextureRegion>) {

//    val wild = SlotItem(itemRegions[26])
    val list = List(20) { SlotItem(itemRegions[it]) }

}