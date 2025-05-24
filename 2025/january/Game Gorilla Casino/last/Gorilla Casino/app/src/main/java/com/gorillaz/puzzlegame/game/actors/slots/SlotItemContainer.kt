package com.gorillaz.puzzlegame.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion

class SlotItemContainer(
    private val itemWild   : TextureRegion,
    private val itemRegions: List<TextureRegion>
) {

    val wild = SlotItem(itemWild)
    val list = List(itemRegions.size) { SlotItem(itemRegions[it]) }

}