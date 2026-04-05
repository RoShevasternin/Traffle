package com.bramlix.cas888.casino.game.util

import com.badlogic.gdx.graphics.Color

object GameColor {



    fun rgba(r: Int, g: Int, b: Int, a: Int = 1) = Color(r / 255f, g / 255f, b / 255f, a.toFloat())

}