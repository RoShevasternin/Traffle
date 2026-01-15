package com.sweetfruit.catchermandarin.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.sweetfruit.catchermandarin.game.manager.SpriteManager

class SpriteUtil {

     class Guglas {
          val lodrinking = SpriteManager.EnumTexture.lodrinking.data.texture
     }

     class Faradeo {
          private fun getConhetu(name: String): TextureRegion = SpriteManager.EnumAtlas.conhetu.data.atlas.findRegion(name)

          val cb_def    = getConhetu("cb_def")
          val cb_press  = getConhetu("cb_press")
          val munu      = getConhetu("munu")
          val munu_pers = getConhetu("munu_pers")

          val confeti = List(10) { getConhetu("${it.inc()}") }

          val blures     = SpriteManager.EnumTexture.blures.data.texture
          val buttons    = SpriteManager.EnumTexture.buttons.data.texture
          val cucerci    = SpriteManager.EnumTexture.cucerci.data.texture
          val musical    = SpriteManager.EnumTexture.musical.data.texture
          val soundel    = SpriteManager.EnumTexture.soundel.data.texture

     }

}