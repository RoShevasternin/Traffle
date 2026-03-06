package com.bounceques.ternationaret.game.manager.util

import com.bounceques.ternationaret.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val LOADER = SpriteManager.EnumTexture.LOADER.data.texture
     }

     class AllAssets {
         val BALL         = SpriteManager.EnumTexture.BALL.data.texture
         val BD           = SpriteManager.EnumTexture.BD.data.texture
         val BP           = SpriteManager.EnumTexture.BP.data.texture
         val COIN         = SpriteManager.EnumTexture.COIN.data.texture
         val LEFT_DEF     = SpriteManager.EnumTexture.LEFT_DEF.data.texture
         val LEFT_PRESS   = SpriteManager.EnumTexture.LEFT_PRESS.data.texture
         val LEVELS_PAN   = SpriteManager.EnumTexture.LEVELS_PAN.data.texture
         val LOSE_PAN     = SpriteManager.EnumTexture.LOSE_PAN.data.texture
         val MD           = SpriteManager.EnumTexture.MD.data.texture
         val MENU_PAN     = SpriteManager.EnumTexture.MENU_PAN.data.texture
         val MP           = SpriteManager.EnumTexture.MP.data.texture
         val RIGHT_DEF    = SpriteManager.EnumTexture.RIGHT_DEF.data.texture
         val RIGHT_PRESS  = SpriteManager.EnumTexture.RIGHT_PRESS.data.texture
         val RULES_PAN    = SpriteManager.EnumTexture.RULES_PAN.data.texture
         val SD           = SpriteManager.EnumTexture.SD.data.texture
         val SP           = SpriteManager.EnumTexture.SP.data.texture
         val TIMER_PAN    = SpriteManager.EnumTexture.TIMER_PAN.data.texture
         val UP_DEF       = SpriteManager.EnumTexture.UP_DEF.data.texture
         val UP_PRESS     = SpriteManager.EnumTexture.UP_PRESS.data.texture
         val WIN_PAN      = SpriteManager.EnumTexture.WIN_PAN.data.texture

         private val P1 = SpriteManager.EnumTexture.P1.data.texture
         private val P2 = SpriteManager.EnumTexture.P2.data.texture
         private val P3 = SpriteManager.EnumTexture.P3.data.texture
         private val P4 = SpriteManager.EnumTexture.P4.data.texture

         private val T1 = SpriteManager.EnumTexture.T1.data.texture
         private val T2 = SpriteManager.EnumTexture.T2.data.texture
         private val T3 = SpriteManager.EnumTexture.T3.data.texture
         private val T4 = SpriteManager.EnumTexture.T4.data.texture

         val listP = listOf(P1, P2, P3, P4)
         val listT = listOf(T1, T2, T3, T4)

         val B1     = SpriteManager.EnumTexture.B1.data.texture
         val B2     = SpriteManager.EnumTexture.B2.data.texture
         val B3     = SpriteManager.EnumTexture.B3.data.texture
         val B4     = SpriteManager.EnumTexture.B4.data.texture
         val B_BLUR = SpriteManager.EnumTexture.B_BLUR.data.texture
     }

}