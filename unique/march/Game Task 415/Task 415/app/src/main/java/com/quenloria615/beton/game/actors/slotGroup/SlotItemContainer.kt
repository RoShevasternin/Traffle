package com.quenloria615.beton.game.actors.slotGroup

import com.badlogic.gdx.graphics.g2d.TextureRegion

class SlotItemContainer(
    private val itemRegions: List<TextureRegion>
) {

    val list = List(itemRegions.size) { SlotItem(itemRegions[it]) }

}