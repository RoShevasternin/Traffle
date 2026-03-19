package com.senqorvia774.lottomatica.game.utils

import com.badlogic.gdx.graphics.Color

object GameColor {

    val background: Color = Color.valueOf("000000")

    val white_D4D4D4 : Color = Color.valueOf("EEF3F4")
    val blue_2CCEE3  : Color = Color.valueOf("EEF3F4")

    val black_62 : Color = Color.valueOf("0063A5").cpy().apply { a = 0.62f }
    val black_77 : Color = Color.valueOf("0063A5").cpy().apply { a = 0.77f }

}