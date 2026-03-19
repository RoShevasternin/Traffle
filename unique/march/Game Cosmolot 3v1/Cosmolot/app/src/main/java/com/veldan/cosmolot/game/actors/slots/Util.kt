package com.veldan.cosmolot.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion

data class SpinResult(
    val winSlotItemSet: Set<SlotItem>?,
)

data class FillResult(
    val winSlotItemSet  : Set<SlotItem>,
    val intersectionList: List<Intersection>,
)

data class Intersection(
    val slotIndex: Int,
    val rowIndex : Int,
)

data class SlotItem(
    val id       : Int,
    val region   : TextureRegion,
    var priceCoff: Float,
)

enum class FillStrategy {
    MIX, WIN
}