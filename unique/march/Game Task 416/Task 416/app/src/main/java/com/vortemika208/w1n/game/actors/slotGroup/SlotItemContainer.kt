package com.vortemika208.w1n.game.actors.slotGroup

import com.badlogic.gdx.graphics.g2d.TextureRegion

class SlotItemContainer(
    private val itemRegions: List<TextureRegion>
) {

    val list = List(itemRegions.size) { SlotItem(itemRegions[it]) }

}