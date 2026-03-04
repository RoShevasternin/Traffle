package com.circuser.pairante.game.manager.util

import com.circuser.pairante.game.manager.SpriteManager

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
         private val _7 = SpriteManager.EnumTexture._7.data.texture
         private val _8 = SpriteManager.EnumTexture._8.data.texture

         val listItems = listOf(_1, _2, _3, _4, _5, _6, _7, _8)

         val B_DEF      = SpriteManager.EnumTexture.B_DEF.data.texture
         val B_GAME     = SpriteManager.EnumTexture.B_GAME.data.texture
         val B_LOSE     = SpriteManager.EnumTexture.B_LOSE.data.texture
         val B_SELECTER = SpriteManager.EnumTexture.B_SELECTER.data.texture
         val B_WIN      = SpriteManager.EnumTexture.B_WIN.data.texture
         val GALCA      = SpriteManager.EnumTexture.GALCA.data.texture
         val GAME       = SpriteManager.EnumTexture.GAME.data.texture
         val LOSE       = SpriteManager.EnumTexture.LOSE.data.texture
         val MASK       = SpriteManager.EnumTexture.MASK.data.texture
         val MENU       = SpriteManager.EnumTexture.MENU.data.texture
         val PIP        = SpriteManager.EnumTexture.PIP.data.texture
         val PROG       = SpriteManager.EnumTexture.PROG.data.texture
         val RESULT     = SpriteManager.EnumTexture.RESULT.data.texture
         val SCHOOSE    = SpriteManager.EnumTexture.SCHOOSE.data.texture
         val SETT       = SpriteManager.EnumTexture.SETT.data.texture
         val SLON       = SpriteManager.EnumTexture.SLON.data.texture
         val VIC        = SpriteManager.EnumTexture.VIC.data.texture
         val BD         = SpriteManager.EnumTexture.BD.data.texture
         val BP         = SpriteManager.EnumTexture.BP.data.texture
     }

}