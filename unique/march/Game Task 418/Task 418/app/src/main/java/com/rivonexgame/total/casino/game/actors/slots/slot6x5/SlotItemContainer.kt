package com.rivonexgame.total.casino.game.actors.slots.slot6x5

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.rivonexgame.total.casino.game.actors.slots.SlotItem

class SlotItemContainer(private val itemRegions: List<TextureRegion>) {

    val wild = SlotItem(itemRegions[26])
    val list = List(26) { SlotItem(itemRegions[it]) }

}