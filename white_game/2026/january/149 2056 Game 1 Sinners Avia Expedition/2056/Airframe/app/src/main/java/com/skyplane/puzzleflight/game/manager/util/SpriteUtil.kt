package com.skyplane.puzzleflight.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.skyplane.puzzleflight.game.manager.SpriteManager

class SpriteUtil {

     class LoadAssets {
          val background = SpriteManager.EnumTexture.background.data.texture
          val load       = SpriteManager.EnumTexture.load.data.texture
          val mask       = SpriteManager.EnumTexture.mask.data.texture
          val plane      = SpriteManager.EnumTexture.plane.data.texture
          val sss        = SpriteManager.EnumTexture.sss.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val deff  = getRegion("deff")
          val item  = getRegion("item")
          val pass  = getRegion("pass")
          val timer = getRegion("timer")

          val listPuzzle = List(8) { getRegion("${it.inc()}") }

          val Fail     = SpriteManager.EnumTexture.Fail.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val panel    = SpriteManager.EnumTexture.panel.data.texture
          val puzzle   = SpriteManager.EnumTexture.puzzle.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture
          val Win      = SpriteManager.EnumTexture.Win.data.texture
     }

}