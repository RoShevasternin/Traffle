package com.fontantwo.tennisfortwo.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fontantwo.tennisfortwo.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          val MAIN = SpriteManager.EnumTexture.MAIN.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val back_def   = getRegion("back_def")
          val back_press = getRegion("back_press")
          val billy      = getRegion("billy")
          val down_def   = getRegion("down_def")
          val down_press = getRegion("down_press")
          val dvokrapka  = getRegion("dvokrapka")
          val ext_def    = getRegion("ext_def")
          val ext_press  = getRegion("ext_press")
          val p_bak      = getRegion("p_bak")
          val p_p        = getRegion("p_p")
          val play_def   = getRegion("play_def")
          val play_press = getRegion("play_press")
          val ruls_def   = getRegion("ruls_def")
          val ruls_press = getRegion("ruls_press")
          val sett_def   = getRegion("sett_def")
          val sett_press = getRegion("sett_press")
          val up_def     = getRegion("up_def")
          val up_press   = getRegion("up_press")
          val vs         = getRegion("vs")
          val a_hand     = getRegion("a_hand")
          val b_hand     = getRegion("b_hand")

          private val rockets = List(8) { getRegion("${it.inc()}") }
          val rocketLeftList  = rockets.take(4)
          val rocketRightList = rockets.takeLast(4)

          val COSMO_STOL = SpriteManager.EnumTexture.COSMO_STOL.data.texture
          val MOSKA      = SpriteManager.EnumTexture.MOSKA.data.texture
          val STOL       = SpriteManager.EnumTexture.STOL.data.texture
          val GO         = SpriteManager.EnumTexture.GO.data.texture
     }

}