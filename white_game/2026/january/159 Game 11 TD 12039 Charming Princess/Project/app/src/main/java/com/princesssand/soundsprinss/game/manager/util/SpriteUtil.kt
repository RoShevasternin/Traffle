package com.princesssand.soundsprinss.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.princesssand.soundsprinss.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          val loader = SpriteManager.EnumTexture.loader.data.texture
     }

     class All {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.all.data.atlas.findRegion(name)
          private fun getPath(name: String): NinePatch = SpriteManager.EnumAtlas.all.data.atlas.createPatch(name)

          val levels_panel = getPath("levels_panel")
          val tutorial     = getPath("tutorial")

          val back_def     = getRegion("back_def")
          val back_press   = getRegion("back_press")
          val ball         = getRegion("ball")
          val butons       = getRegion("butons")
          val ext_def      = getRegion("ext_def")
          val ext_press    = getRegion("ext_press")
          val lvl_def      = getRegion("lvl_def")
          val lvl_dis      = getRegion("lvl_dis")
          val lvl_press    = getRegion("lvl_press")
          val ms_sn        = getRegion("ms_sn")
          val prog         = getRegion("prog")
          val prog_back    = getRegion("prog_back")
          val record_def   = getRegion("record_def")
          val record_press = getRegion("record_press")
          val yellow_frame = getRegion("yellow_frame")
          val save         = getRegion("save")
          val hand         = getRegion("hand")

          val fg_def   = getRegion("fg_def")
          val fg_press = getRegion("fg_press")

          val items = List(6) { getRegion("${it + 2}") }

          val panel    = SpriteManager.EnumTexture.panel.data.texture
          val princess = SpriteManager.EnumTexture.princess.data.texture
          val progress = SpriteManager.EnumTexture.progress.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val blures   = SpriteManager.EnumTexture.blures.data.texture
          val borders  = SpriteManager.EnumTexture.borders.data.texture
          val game     = SpriteManager.EnumTexture.game.data.texture
          val b_music  = SpriteManager.EnumTexture.b_music.data.texture
          val b_sound  = SpriteManager.EnumTexture.b_sound.data.texture
          val b_back   = SpriteManager.EnumTexture.b_back.data.texture
          val bb_def   = SpriteManager.EnumTexture.bb_def.data.texture
          val bb_press   = SpriteManager.EnumTexture.bb_press.data.texture

     }

}