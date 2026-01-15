package com.sugaraxplosion.candysmoy.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.sugaraxplosion.candysmoy.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          private fun getReg(name: String): TextureRegion = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(name)

          val loader = getReg("loader")
          val logo   = getReg("logo")

          val LOAD = SpriteManager.EnumTexture.LOAD.data.texture
     }

     class All {
          private fun getRegAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val bomb_counter = getRegAll("bomb_counter")
          val counter_pan  = getRegAll("counter_pan")
          val logo         = getRegAll("logo")
          val max          = getRegAll("max")
          val oclik_def    = getRegAll("oclik_def")
          val oclik_prs    = getRegAll("oclik_prs")
          val panel        = getRegAll("panel")
          val pitan_prs    = getRegAll("pitan_prs")
          val pitann_def   = getRegAll("pitann_def")
          val plus_def     = getRegAll("plus_def")
          val plus_prs     = getRegAll("plus_prs")
          val star_def     = getRegAll("star_def")
          val star_prs     = getRegAll("star_prs")
          val sweet_bomb   = getRegAll("sweet_bomb")
          val sweet_def    = getRegAll("sweet_def")
          val sweet_prs    = getRegAll("sweet_prs")
          val x_def        = getRegAll("x_def")
          val x_prrs       = getRegAll("x_prrs")
          val d       = getRegAll("agd")
          val p       = getRegAll("agp")

          val sweets      = List(15) { getRegAll("${it.inc()}") }
          val sweets_gray = List(15) { getRegAll("${it.inc()}_gray") }

          val BLUE    = SpriteManager.EnumTexture.BLUE.data.texture
          val BLURES  = SpriteManager.EnumTexture.BLURES.data.texture
          val GIRL    = SpriteManager.EnumTexture.GIRL.data.texture
          val RECORDS = SpriteManager.EnumTexture.RECORDS.data.texture
          val RULES   = SpriteManager.EnumTexture.RULES.data.texture
          val VAFLA   = SpriteManager.EnumTexture.VAFLA.data.texture
          val PITANIE = SpriteManager.EnumTexture.PITANIE.data.texture
          val agree = SpriteManager.EnumTexture.agree.data.texture
     }

}