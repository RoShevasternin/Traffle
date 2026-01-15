package com.cosmostarsca.quantumfruits.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cosmostarsca.quantumfruits.game.manager.SpriteManager

class SpriteUtil {

     class Load {
          val Loader = SpriteManager.EnumTexture.Loader.data.texture
     }

     class Assets {
          private fun getReg(name: String): TextureRegion = SpriteManager.EnumAtlas.ASSETS.data.atlas.findRegion(name)

          val b_def    = getReg("b_def")
          val b_press  = getReg("b_press")
          val progress = getReg("progress")

          val items = List(7) { getReg("${it.inc()}") }

          val mask     = SpriteManager.EnumTexture.mask.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture

     }

}