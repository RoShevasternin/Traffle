package com.spacepuz.puzlesspace.game.manager.util

import com.spacepuz.puzlesspace.game.manager.SpriteManager

class SpriteUtil {

    class Loader {
        val LOADER = SpriteManager.EnumTexture.LOADER.data.texture
    }

     class All {
         private val _1 = SpriteManager.EnumTexture._1.data.texture
         private val _2 = SpriteManager.EnumTexture._2.data.texture
         private val _3 = SpriteManager.EnumTexture._3.data.texture
         private val _4 = SpriteManager.EnumTexture._4.data.texture
         private val _5 = SpriteManager.EnumTexture._5.data.texture
         private val _6 = SpriteManager.EnumTexture._6.data.texture

         val listPuzzle = listOf(_1, _2, _3, _4, _5, _6)

         val BACK_DEF     = SpriteManager.EnumTexture.BACK_DEF.data.texture
         val BACK_PRESS   = SpriteManager.EnumTexture.BACK_PRESS.data.texture
         val DEF          = SpriteManager.EnumTexture.DEF.data.texture
         val GAME_PANEL   = SpriteManager.EnumTexture.GAME_PANEL.data.texture
         val LOSE         = SpriteManager.EnumTexture.LOSE.data.texture
         val MD           = SpriteManager.EnumTexture.MD.data.texture
         val MENU         = SpriteManager.EnumTexture.MENU.data.texture
         val MP           = SpriteManager.EnumTexture.MP.data.texture
         val RESTART_HOME = SpriteManager.EnumTexture.RESTART_HOME.data.texture
         val RULES        = SpriteManager.EnumTexture.RULES.data.texture
         val SD           = SpriteManager.EnumTexture.SD.data.texture
         val SP           = SpriteManager.EnumTexture.SP.data.texture
         val WIN          = SpriteManager.EnumTexture.WIN.data.texture
         val PANEL_RULES          = SpriteManager.EnumTexture.PANEL_RULES.data.texture
     }

}