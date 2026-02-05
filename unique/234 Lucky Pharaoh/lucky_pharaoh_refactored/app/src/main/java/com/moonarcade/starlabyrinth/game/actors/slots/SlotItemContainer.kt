/*
 * Refactored Application Module
 * Build: 3C583A78
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion

class SlotItemContainer(
    private val itemWild   : TextureRegion,
    private val itemRegions: List<TextureRegion>
) {

    val wild = SlotItem(itemWild)
    val list = List(itemRegions.size) { SlotItem(itemRegions[it]) }

}