package com.cosmicbounce.galaxytic.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cosmicbounce.galaxytic.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
          val LOADER     = SpriteManager.EnumTexture.LOADER.data.texture
     }

     class All {
          private fun getRegAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val a        = getRegAll("a")
          val b        = getRegAll("b")
          val c        = getRegAll("c")
          val backdef  = getRegAll("backdef")
          val backprss = getRegAll("backprss")
          val bgd      = getRegAll("bgd")
          val bgp      = getRegAll("bgp")
          val bkd      = getRegAll("bkd")
          val bkp      = getRegAll("bkp")
          val bll      = getRegAll("bll")
          val bounce   = getRegAll("bounce")
          val bumleft  = getRegAll("bumleft")
          val bumright = getRegAll("bumright")
          val cntr     = getRegAll("cntr")
          val extd     = getRegAll("extd")
          val extp     = getRegAll("extp")
          val galka    = getRegAll("galka")
          val infd     = getRegAll("infd")
          val infp     = getRegAll("infp")
          val left     = getRegAll("left")
          val logo     = getRegAll("logo")
          val right    = getRegAll("right")
          val std      = getRegAll("std")
          val stp      = getRegAll("stp")
          val agd      = getRegAll("agd")
          val agp      = getRegAll("agp")

          val items = List(7) { getRegAll("${it.inc()}") }

          private val B1 = SpriteManager.EnumTexture.B1.data.texture
          private val B2 = SpriteManager.EnumTexture.B2.data.texture
          private val B3 = SpriteManager.EnumTexture.B3.data.texture

          val backgrounds = listOf(B1,B2,B3)

          val SETTINGS = SpriteManager.EnumTexture.SETTINGS.data.texture
          val texeste = SpriteManager.EnumTexture.texeste.data.texture

     }

}