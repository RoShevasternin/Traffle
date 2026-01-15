package com.toyscatcher.factoryatoy.utils

import com.badlogic.gdx.graphics.g2d.TextureRegion

data class Toy(
    val id: Int,
    val region: TextureRegion,
    val money: Int,
    val time: Int,
)