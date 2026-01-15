package com.flightcoll.bridgertons.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.flightcoll.bridgertons.game.manager.SpriteManager

class SpriteUtil {

     class Start {
          val zagruzon = SpriteManager.EnumTexture.zagruzon.data.texture
     }

     class VseAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val _15          = getRegion("15")
          val _25          = getRegion("25")
          val _60          = getRegion("60")
          val _105         = getRegion("105")

          val cbdef       = getRegion("cbdef")
          val cbpres      = getRegion("cbpres")
          val colichestvo = getRegion("colichestvo")
          val compass     = getRegion("compass")
          val manu        = getRegion("manu")
          val mdef        = getRegion("mdef")
          val mpres       = getRegion("mpres")
          val pilot       = getRegion("pilot")

          val musishion = SpriteManager.EnumTexture.musishion.data.texture
          val welcome   = SpriteManager.EnumTexture.welcome.data.texture

     }

}