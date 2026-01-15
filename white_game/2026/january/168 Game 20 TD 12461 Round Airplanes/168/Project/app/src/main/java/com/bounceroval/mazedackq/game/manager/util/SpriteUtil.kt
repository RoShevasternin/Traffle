package com.bounceroval.mazedackq.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.bounceroval.mazedackq.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          private val bg1 = SpriteManager.EnumTexture.bg1.data.texture
          private val bg2 = SpriteManager.EnumTexture.bg2.data.texture
          private val bg3 = SpriteManager.EnumTexture.bg3.data.texture

          val loading = SpriteManager.EnumTexture.loading.data.texture

          val backgrounds = listOf(bg1,bg2,bg3)
     }

     class All {
          private fun getReg(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val back_def   = getReg("back_def")
          val back_press = getReg("back_press")
          val ball       = getReg("ball")
          val exit       = getReg("exit")
          val off        = getReg("off")
          val on         = getReg("on")
          val panel      = getReg("panel")
          val play       = getReg("play")
          val progress   = getReg("progress")
          val random     = getReg("random")
          val rules      = getReg("rules")
          val settings   = getReg("settings")
          val info       = getReg("info")

          val avias = List(3) { getReg("f${it.inc()}") }
          val plans = List(3) { getReg("o${it.inc()}") }
          val items = List(10) { getReg("${it.inc()}") }

          val big_sett   = SpriteManager.EnumTexture.big_sett.data.texture
          val mask       = SpriteManager.EnumTexture.mask.data.texture
          val text_rules = SpriteManager.EnumTexture.text_rules.data.texture
          val rules_pan  = SpriteManager.EnumTexture.rules_pan.data.texture
          val t_rules    = SpriteManager.EnumTexture.t_rules.data.texture
          val t_settings = SpriteManager.EnumTexture.t_settings.data.texture
          val info1      = SpriteManager.EnumTexture.info1.data.texture
          val info2      = SpriteManager.EnumTexture.info2.data.texture
          val info3      = SpriteManager.EnumTexture.info3.data.texture

          val DAgr   = SpriteManager.EnumTexture.DAgr.data.texture
          val DDis   = SpriteManager.EnumTexture.DDis.data.texture
          val DPanel = SpriteManager.EnumTexture.DPanel.data.texture
          val DText  = SpriteManager.EnumTexture.DText.data.texture

     }

}