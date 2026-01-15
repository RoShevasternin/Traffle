package com.samartachokitse.endelgase.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.samartachokitse.endelgase.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          val BOLOTER = SpriteManager.EnumTexture.BOLOTER.data.texture
          val LOADING = SpriteManager.EnumTexture.LOADING.data.texture
     }

     class All {
          private fun getRegAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val a           = getRegAll("a")
          val b           = getRegAll("b")
          val c           = getRegAll("c")
          val cursor      = getRegAll("cursor")
          val exit_def    = getRegAll("exit_def")
          val exit_press  = getRegAll("exit_press")
          val frm         = getRegAll("frm")
          val info_def    = getRegAll("info_def")
          val info_press  = getRegAll("info_press")
          val menu_def    = getRegAll("menu_def")
          val menu_press  = getRegAll("menu_press")
          val mu_sd       = getRegAll("mu_sd")
          val prg         = getRegAll("prg")
          val sett_def    = getRegAll("sett_def")
          val sett_press  = getRegAll("sett_press")
          val start_def   = getRegAll("start_def")
          val start_press = getRegAll("start_press")
          val timer       = getRegAll("timer")
          val settk       = getRegAll("settk")
          val infok       = getRegAll("infok")
          val starka      = getRegAll("starka")
          val iag_press   = getRegAll("iag_press")
          val iag_def     = getRegAll("iag_def")

          val results = List(3) { getRegAll("r${it.inc()}") }
          val items   = List(10) { getRegAll("i${it.inc()}") }

          private val B1 = SpriteManager.EnumTexture.B1.data.texture
          private val B2 = SpriteManager.EnumTexture.B2.data.texture
          private val B3 = SpriteManager.EnumTexture.B3.data.texture

          val backgrounds = listOf(B1,B2,B3)

          val FRAME  = SpriteManager.EnumTexture.FRAME.data.texture
          val GIFT   = SpriteManager.EnumTexture.GIFT.data.texture
          val KING   = SpriteManager.EnumTexture.KING.data.texture
          val LEFT   = SpriteManager.EnumTexture.LEFT.data.texture
          val MASTER = SpriteManager.EnumTexture.MASTER.data.texture
          val MSK    = SpriteManager.EnumTexture.MSK.data.texture
          val RIGHT  = SpriteManager.EnumTexture.RIGHT.data.texture
          val TRICA  = SpriteManager.EnumTexture.TRICA.data.texture
          val INTRO  = SpriteManager.EnumTexture.INTRO.data.texture


     }

}