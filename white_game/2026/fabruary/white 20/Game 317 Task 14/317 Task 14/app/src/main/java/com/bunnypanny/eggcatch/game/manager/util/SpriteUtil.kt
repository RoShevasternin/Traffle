package com.bunnypanny.eggcatch.game.manager.util

import com.bunnypanny.eggcatch.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          val LOADER  = SpriteManager.EnumTexture.LOADER.data.texture
     }

     class GameAssets {
         val _1 = SpriteManager.EnumTexture._1.data.texture
         val _2 = SpriteManager.EnumTexture._2.data.texture
         val _3 = SpriteManager.EnumTexture._3.data.texture
         val _4 = SpriteManager.EnumTexture._4.data.texture
         val _5 = SpriteManager.EnumTexture._5.data.texture
         val _6 = SpriteManager.EnumTexture._6.data.texture

         val listEgg = listOf(_1, _2, _3, _4, _5, _6)

         val AGAIN        = SpriteManager.EnumTexture.AGAIN.data.texture
         val BACK_DEF     = SpriteManager.EnumTexture.BACK_DEF.data.texture
         val BACK_PRESS   = SpriteManager.EnumTexture.BACK_PRESS.data.texture
         val BACKGROUND   = SpriteManager.EnumTexture.BACKGROUND.data.texture
         val BAG          = SpriteManager.EnumTexture.BAG.data.texture
         val BUNNY        = SpriteManager.EnumTexture.BUNNY.data.texture
         val DONE         = SpriteManager.EnumTexture.DONE.data.texture
         val LOSE         = SpriteManager.EnumTexture.LOSE.data.texture
         val MASK         = SpriteManager.EnumTexture.MASK.data.texture
         val MENU_PAN     = SpriteManager.EnumTexture.MENU_PAN.data.texture
         val PANEL        = SpriteManager.EnumTexture.PANEL.data.texture
         val PIP          = SpriteManager.EnumTexture.PIP.data.texture
         val PROGRESS     = SpriteManager.EnumTexture.PROGRESS.data.texture
         val RULES_PAN    = SpriteManager.EnumTexture.RULES_PAN.data.texture
         val SETTINGS_PAN = SpriteManager.EnumTexture.SETTINGS_PAN.data.texture
         val WIN          = SpriteManager.EnumTexture.WIN.data.texture

     }

}