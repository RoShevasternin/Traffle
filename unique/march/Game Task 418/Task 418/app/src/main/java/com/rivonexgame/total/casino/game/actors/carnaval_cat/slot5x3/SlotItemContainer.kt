package com.rivonexgame.total.casino.game.actors.carnaval_cat.slot5x3

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.rivonexgame.total.casino.game.actors.slots.SlotItem

class SlotItemContainer(private val itemRegions: List<TextureRegion>) {

    val wild = SlotItem(itemRegions[14])
    val list = List(14) { SlotItem(itemRegions[it]) }

}