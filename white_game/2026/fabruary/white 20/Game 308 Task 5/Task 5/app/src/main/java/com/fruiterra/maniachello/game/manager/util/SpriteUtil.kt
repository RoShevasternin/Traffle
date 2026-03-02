package com.fruiterra.maniachello.game.manager.util

import com.fruiterra.maniachello.game.manager.SpriteManager
import com.fruiterra.maniachello.game.utils.TextureEmpty

class SpriteUtil {

    class Loader {
        //val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val LOADER     = SpriteManager.EnumTexture.LOADER.data.texture
    }

    class All {

        private val _1   = SpriteManager.EnumTexture._1.data.texture
        private val _2   = SpriteManager.EnumTexture._2.data.texture
        private val _3   = SpriteManager.EnumTexture._3.data.texture
        private val _4   = SpriteManager.EnumTexture._4.data.texture
        private val _5   = SpriteManager.EnumTexture._5.data.texture
        private val _6   = SpriteManager.EnumTexture._6.data.texture
        private val _7   = SpriteManager.EnumTexture._7.data.texture
        private val _8   = SpriteManager.EnumTexture._8.data.texture
        private val _9   = SpriteManager.EnumTexture._9.data.texture
        private val _10  = SpriteManager.EnumTexture._10.data.texture

        val listFruits = listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10)

        val BACK_DEF        = SpriteManager.EnumTexture.back_def.data.texture
        val BACK_PRESS      = SpriteManager.EnumTexture.back_press.data.texture
        val BACKGROUND      = SpriteManager.EnumTexture.background.data.texture
        val BACKGROUND_GAME = SpriteManager.EnumTexture.background_game.data.texture
        val BACKGROUND_WIN  = SpriteManager.EnumTexture.background_win.data.texture
        val BTNS            = SpriteManager.EnumTexture.btns.data.texture
        val CURSOR          = SpriteManager.EnumTexture.cursor.data.texture
        val MENU_DEF        = SpriteManager.EnumTexture.menu_def.data.texture
        val MENU_PRESS      = SpriteManager.EnumTexture.menu_press.data.texture
        val PANEL           = SpriteManager.EnumTexture.panel.data.texture
        val RULES           = SpriteManager.EnumTexture.rules.data.texture
        val SETT            = SpriteManager.EnumTexture.sett.data.texture
        val WIN             = SpriteManager.EnumTexture.win.data.texture

    }

}