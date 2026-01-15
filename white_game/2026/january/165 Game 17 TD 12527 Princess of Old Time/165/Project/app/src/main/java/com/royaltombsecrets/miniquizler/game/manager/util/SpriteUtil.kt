package com.royaltombsecrets.miniquizler.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.royaltombsecrets.miniquizler.game.manager.SpriteManager

class SpriteUtil {

     class Splash {
          private fun getReg(name: String): TextureRegion = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(name)

          val l_left     = getReg("l_left")
          val l_right    = getReg("l_right")
          val princessa  = getReg("princessa")

          val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
          val BACKGROUND_V = SpriteManager.EnumTexture.BACKGROUND_V.data.texture
     }

     class All {
          private fun getRegAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegItems(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEMS.data.atlas.findRegion(name)

          val ex_df   = getRegAll("ex_df")
          val ex_ps   = getRegAll("ex_ps")
          val ga_df   = getRegAll("ga_df")
          val ga_ps   = getRegAll("ga_ps")
          val left    = getRegAll("left")
          val mn_df   = getRegAll("mn_df")
          val mn_ps   = getRegAll("mn_ps")
          val msc     = getRegAll("msc")
          val msc_prs = getRegAll("msc_prs")
          val prpl    = getRegAll("prpl")
          val rc_df   = getRegAll("rc_df")
          val rc_ps   = getRegAll("rc_ps")
          val snd     = getRegAll("snd")
          val snd_prs = getRegAll("snd_prs")
          val top     = getRegAll("top")
          val vc_df   = getRegAll("vc_df")
          val vc_ps   = getRegAll("vc_ps")
          val yellow  = getRegAll("yellow")
          val a1      = getRegAll("a1")
          val a2      = getRegAll("a2")

          val brilliants = List(5) { getRegAll("${it.inc()}") }
          val colls      = List(9) { getRegItems("coll${it.inc()}") }
          val grays      = List(9) { getRegItems("coll${it.inc()}_gray") }

          val BLUE   = SpriteManager.EnumTexture.BLUE.data.texture
          val PURPLE = SpriteManager.EnumTexture.PURPLE.data.texture
          val RED    = SpriteManager.EnumTexture.RED.data.texture

          private val FRAME1 = SpriteManager.EnumTexture.FRAME1.data.texture
          private val FRAME2 = SpriteManager.EnumTexture.FRAME2.data.texture
          private val FRAME3 = SpriteManager.EnumTexture.FRAME3.data.texture
          private val FRAME4 = SpriteManager.EnumTexture.FRAME4.data.texture
          private val FRAME5 = SpriteManager.EnumTexture.FRAME5.data.texture
          private val FRAME6 = SpriteManager.EnumTexture.FRAME6.data.texture
          private val FRAME7 = SpriteManager.EnumTexture.FRAME7.data.texture
          private val FRAME8 = SpriteManager.EnumTexture.FRAME8.data.texture
          private val FRAME9 = SpriteManager.EnumTexture.FRAME9.data.texture

          val agrun  = SpriteManager.EnumTexture.agrun.data.texture

          val frames = listOf(
               FRAME1, FRAME2, FRAME3, FRAME4, FRAME5,
               FRAME6, FRAME7, FRAME8, FRAME9,
          )
     }

}