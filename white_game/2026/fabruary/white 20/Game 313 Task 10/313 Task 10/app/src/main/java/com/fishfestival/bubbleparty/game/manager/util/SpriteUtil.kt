package com.fishfestival.bubbleparty.game.manager.util

import com.fishfestival.bubbleparty.game.manager.SpriteManager
import com.fishfestival.bubbleparty.game.utils.TextureEmpty

class SpriteUtil {

    class Loader {
        //val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val LOADER     = SpriteManager.EnumTexture.LOADER.data.texture
    }

    class All {

        private val _1      = SpriteManager.EnumTexture._1.data.texture
        private val _2      = SpriteManager.EnumTexture._2.data.texture
        private val _3      = SpriteManager.EnumTexture._3.data.texture
        private val _4      = SpriteManager.EnumTexture._4.data.texture
        private val _5      = SpriteManager.EnumTexture._5.data.texture
        private val RAINBOW = SpriteManager.EnumTexture.RAINBOW.data.texture

        val listBall = listOf(_1, _2, _3, _4, _5, RAINBOW)

        val B_BLUR        = SpriteManager.EnumTexture.B_BLUR.data.texture
        val B_DEF         = SpriteManager.EnumTexture.B_DEF.data.texture
        val B_LOSE        = SpriteManager.EnumTexture.B_LOSE.data.texture
        val B_WIN         = SpriteManager.EnumTexture.B_WIN.data.texture
        val BACK_DEF      = SpriteManager.EnumTexture.BACK_DEF.data.texture
        val BACK_PRESS    = SpriteManager.EnumTexture.BACK_PRESS.data.texture
        val BOMB          = SpriteManager.EnumTexture.BOMB.data.texture
        val BOTTOM        = SpriteManager.EnumTexture.BOTTOM.data.texture
        val BROGRESS_BACK = SpriteManager.EnumTexture.BROGRESS_BACK.data.texture
        val FISH          = SpriteManager.EnumTexture.FISH.data.texture
        val GUN           = SpriteManager.EnumTexture.GUN.data.texture
        val LIDER         = SpriteManager.EnumTexture.LIDER.data.texture
        val LOSE          = SpriteManager.EnumTexture.LOSE.data.texture
        val MASK          = SpriteManager.EnumTexture.MASK.data.texture
        val MD            = SpriteManager.EnumTexture.MD.data.texture
        val MP            = SpriteManager.EnumTexture.MP.data.texture
        val PANEL         = SpriteManager.EnumTexture.PANEL.data.texture
        val PROGRESS      = SpriteManager.EnumTexture.PROGRESS.data.texture
        val RULES         = SpriteManager.EnumTexture.RULES.data.texture
        val SD            = SpriteManager.EnumTexture.SD.data.texture
        val SP            = SpriteManager.EnumTexture.SP.data.texture
        val STAR          = SpriteManager.EnumTexture.STAR.data.texture
        val STARS         = SpriteManager.EnumTexture.STARS.data.texture
        val WIN           = SpriteManager.EnumTexture.WIN.data.texture

    }

}