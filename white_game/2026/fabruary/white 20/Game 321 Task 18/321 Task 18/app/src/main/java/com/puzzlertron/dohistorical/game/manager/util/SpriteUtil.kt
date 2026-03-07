package com.puzzlertron.dohistorical.game.manager.util

import com.puzzlertron.dohistorical.game.manager.SpriteManager

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

         val B_DEF      = SpriteManager.EnumTexture.B_DEF.data.texture
         val B_LOSE     = SpriteManager.EnumTexture.B_LOSE.data.texture
         val B_WIN      = SpriteManager.EnumTexture.B_WIN.data.texture
         val BACK_DEF   = SpriteManager.EnumTexture.BACK_DEF.data.texture
         val BACK_PRESS = SpriteManager.EnumTexture.BACK_PRESS.data.texture
         val GAME_PAN   = SpriteManager.EnumTexture.GAME_PAN.data.texture
         val LOSE_PAN   = SpriteManager.EnumTexture.LOSE_PAN.data.texture
         val MD         = SpriteManager.EnumTexture.MD.data.texture
         val MENU_PAN   = SpriteManager.EnumTexture.MENU_PAN.data.texture
         val MP         = SpriteManager.EnumTexture.MP.data.texture
         val RULES_PAN  = SpriteManager.EnumTexture.RULES_PAN.data.texture
         val SD         = SpriteManager.EnumTexture.SD.data.texture
         val SP         = SpriteManager.EnumTexture.SP.data.texture
         val WIN_PAN    = SpriteManager.EnumTexture.WIN_PAN.data.texture
     }

}