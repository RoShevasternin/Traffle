package com.portalend.fruitomaner.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.portalend.fruitomaner.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          val BACKICH = SpriteManager.EnumTexture.BACKICH.data.texture
          val LOAD    = SpriteManager.EnumTexture.LOAD.data.texture
     }

     class All {
          private fun getRegAll  (name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegMap  (name: String): TextureRegion = SpriteManager.EnumAtlas.MAP.data.atlas.findRegion(name)
          private fun getRegItems(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEMS.data.atlas.findRegion(name)

          val back_def    = getRegAll("back_def")
          val back_press  = getRegAll("back_press")
          val count       = getRegAll("count")
          val exit_def    = getRegAll("exit_def")
          val exit_press  = getRegAll("exit_press")
          val framer      = getRegAll("framer")
          val ic_apels    = getRegAll("ic_apels")
          val ic_left     = getRegAll("ic_left")
          val klubnik     = getRegAll("klubnik")
          val map_def     = getRegAll("map_def")
          val map_press   = getRegAll("map_press")
          val msend       = getRegAll("msend")
          val proflert    = getRegAll("proflert")
          val proger      = getRegAll("proger")
          val rules_def   = getRegAll("rules_def")
          val rules_press = getRegAll("rules_press")
          val sett_def    = getRegAll("sett_def")
          val sett_press  = getRegAll("sett_press")
          val timer       = getRegAll("timer")
          val vine        = getRegAll("vine")

          val maps  = List(18) { getRegMap("${it.inc()}") }
          val items = List(18) { getRegItems("${it.inc()}") }

          private val B1 = SpriteManager.EnumTexture.B1.data.texture
          private val B2 = SpriteManager.EnumTexture.B2.data.texture
          private val B3 = SpriteManager.EnumTexture.B3.data.texture

          val backgrounds = listOf(B1,B2,B3)

          val MASKA = SpriteManager.EnumTexture.MASKA.data.texture
          val RULES = SpriteManager.EnumTexture.RULES.data.texture

          val mmap = SpriteManager.EnumTexture.mmap.data.texture
          val you_collect = SpriteManager.EnumTexture.you_collect.data.texture


     }

}