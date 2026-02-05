/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.slots

import com.badlogic.gdx.graphics.g2d.TextureRegion

class SlotItemContainer(
    private val itemWild   : TextureRegion,
    private val itemRegions: List<TextureRegion>
) {

    val wild = SlotItem(itemWild)
    val list = List(itemRegions.size) { SlotItem(itemRegions[it]) }

}