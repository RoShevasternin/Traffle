/*
 * Refactored Application Module
 * Build: BF76524B
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion

//data class SpinResult(
//    val winSlotItemSet: Set<SlotItem>?,
//)

data class SlotItem(
    val region: TextureRegion,
)

enum class FillStrategy {
    MIX, WIN
}