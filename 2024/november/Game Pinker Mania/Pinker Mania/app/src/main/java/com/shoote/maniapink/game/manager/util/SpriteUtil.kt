package com.shoote.maniapink.game.manager.util

import com.shoote.maniapink.game.manager.SpriteManager

class SpriteUtil {

    class Loader {
        val background = SpriteManager.EnumTexture.Loader_Background.data.texture
    }

    class All {
        val Background_1 = SpriteManager.EnumTexture.All_Background_1.data.texture
        val Background_2 = SpriteManager.EnumTexture.All_Background_2.data.texture
        val Orange       = SpriteManager.EnumTexture.All_Orange.data.texture

        val _1 = SpriteManager.EnumTexture.All_1.data.texture
        val _2 = SpriteManager.EnumTexture.All_2.data.texture
        val _3 = SpriteManager.EnumTexture.All_3.data.texture
        val _4 = SpriteManager.EnumTexture.All_4.data.texture
        val _5 = SpriteManager.EnumTexture.All_5.data.texture

        val bomb         = SpriteManager.EnumTexture.All_bomb.data.texture
        val complete     = SpriteManager.EnumTexture.All_complete.data.texture
        val gun          = SpriteManager.EnumTexture.All_gun.data.texture
        val lose         = SpriteManager.EnumTexture.All_lose.data.texture
        val mask         = SpriteManager.EnumTexture.All_mask.data.texture
        val menu         = SpriteManager.EnumTexture.All_menu.data.texture
        val mode         = SpriteManager.EnumTexture.All_mode.data.texture
        val panel        = SpriteManager.EnumTexture.All_panel.data.texture
        val pause        = SpriteManager.EnumTexture.All_pause.data.texture
        val pause_star   = SpriteManager.EnumTexture.All_pause_star.data.texture
        val prog         = SpriteManager.EnumTexture.All_prog.data.texture
        val prog_back    = SpriteManager.EnumTexture.All_prog_back.data.texture
        val rainbow      = SpriteManager.EnumTexture.All_rainbow.data.texture
        val shop         = SpriteManager.EnumTexture.All_shop.data.texture
        val shop_star    = SpriteManager.EnumTexture.All_shop_star.data.texture
        val star         = SpriteManager.EnumTexture.All_star.data.texture
        val stars        = SpriteManager.EnumTexture.All_stars.data.texture

        val shariks = SpriteManager.EnumTexture.All_shariks.data.texture

        val check    = SpriteManager.EnumTexture.All_check.data.texture
        val settings = SpriteManager.EnumTexture.All_settings.data.texture

        val listBall       = listOf(_1, _2, _3, _4, _5, rainbow)
        val listBackground = listOf(Background_1, Background_2)
    }

}