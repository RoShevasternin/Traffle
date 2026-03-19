package com.senqorvia774.lottomatica.game.actors.slotGroup

import com.badlogic.gdx.graphics.g2d.TextureRegion

data class SlotItem(
    val region: TextureRegion,
)

enum class FillStrategy {
    MIX, WIN
}