package com.greciao.candyrocket.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.greciao.candyrocket.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val background = SpriteManager.EnumTexture.background.data.texture
          val loading    = SpriteManager.EnumTexture.loading.data.texture
          val mask       = SpriteManager.EnumTexture.mask.data.texture
          val progress   = SpriteManager.EnumTexture.progress.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val heart = getRegion("heart")
          val palet = getRegion("palet")
          val panel = getRegion("panel")
          val shar  = getRegion("shar")

          val airList   = List(3) { getRegion("air${it.inc()}") }
          val moneyList = List(4) { getRegion("money${it.inc()}") }

          val lose     = SpriteManager.EnumTexture.lose.data.texture
          val menu     = SpriteManager.EnumTexture.menu.data.texture
          val rules    = SpriteManager.EnumTexture.rules.data.texture
          val settings = SpriteManager.EnumTexture.settings.data.texture
          val shop     = SpriteManager.EnumTexture.shop.data.texture
          val win      = SpriteManager.EnumTexture.win.data.texture
          val background = SpriteManager.EnumTexture.background.data.texture
          val background_game = SpriteManager.EnumTexture.game_background.data.texture
          val ase = SpriteManager.EnumTexture.ase.data.texture
          val new_l = SpriteManager.EnumTexture.new_l.data.texture
          val new_w = SpriteManager.EnumTexture.new_w.data.texture
     }

}