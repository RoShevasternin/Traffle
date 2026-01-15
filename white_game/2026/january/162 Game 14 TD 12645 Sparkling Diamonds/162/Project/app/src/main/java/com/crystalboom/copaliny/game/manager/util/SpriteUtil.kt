package com.crystalboom.copaliny.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.crystalboom.copaliny.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          private fun getReg(name: String): TextureRegion = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(name)

          val rubik   = getReg("rubik")

     }

     class All {
          private fun getRegAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val a1     = getRegAll("a1")
          val a2     = getRegAll("a2")
          val b1     = getRegAll("b1")
          val b2     = getRegAll("b2")
          val bd     = getRegAll("bd")
          val bp     = getRegAll("bp")
          val brulik = getRegAll("brulik")
          val c1     = getRegAll("c1")
          val c2     = getRegAll("c2")
          val dms    = getRegAll("dms")
          val exd    = getRegAll("exd")
          val exp    = getRegAll("exp")
          val king   = getRegAll("king")
          val svecha = getRegAll("svecha")
          val svc    = getRegAll("svc")

          val items = List(16) { getRegAll("${it.inc()}") }

          val bg1 = SpriteManager.EnumTexture.b1.data.texture
          val bg2 = SpriteManager.EnumTexture.b2.data.texture
          val bg3 = SpriteManager.EnumTexture.b3.data.texture

          val bgs = listOf(bg1,bg2,bg3)

          val BLIUYTRE = SpriteManager.EnumTexture.BLIUYTRE.data.texture
          val GREMMY   = SpriteManager.EnumTexture.GREMMY.data.texture
          val agret   = SpriteManager.EnumTexture.agret.data.texture

          val hui= SpriteManager.EnumTexture.hui.data.texture
          val sosite= SpriteManager.EnumTexture.sosite.data.texture
     }

}