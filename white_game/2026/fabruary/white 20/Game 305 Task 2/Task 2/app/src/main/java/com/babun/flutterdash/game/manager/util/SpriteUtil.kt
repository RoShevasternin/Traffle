package com.babun.flutterdash.game.manager.util

import com.babun.flutterdash.game.manager.SpriteManager
import com.babun.flutterdash.game.utils.TextureEmpty

class SpriteUtil {

    class Loader {
        val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val LOADER     = SpriteManager.EnumTexture.LOADER.data.texture
    }

    class All {

        private val _1 = SpriteManager.EnumTexture._1.data.texture
        private val _2 = SpriteManager.EnumTexture._2.data.texture
        private val _3 = SpriteManager.EnumTexture._3.data.texture
        private val _4 = SpriteManager.EnumTexture._4.data.texture
        private val _5 = SpriteManager.EnumTexture._5.data.texture
        private val _6 = SpriteManager.EnumTexture._6.data.texture

        val listSett = listOf(TextureEmpty, _1, _2, _3, _4, _5, _6)

        val BABKA             = SpriteManager.EnumTexture.BABKA.data.texture
        val BACKGROUND_RESULT = SpriteManager.EnumTexture.BACKGROUND_RESULT.data.texture
        val BK_DEF            = SpriteManager.EnumTexture.BK_DEF.data.texture
        val BK_PRESS          = SpriteManager.EnumTexture.BK_PRESS.data.texture
        val BOT               = SpriteManager.EnumTexture.BOT.data.texture
        val GAME_BABA         = SpriteManager.EnumTexture.GAME_BABA.data.texture
        val GLOW              = SpriteManager.EnumTexture.GLOW.data.texture
        val HART_CHECK        = SpriteManager.EnumTexture.HART_CHECK.data.texture
        val HART_DEF          = SpriteManager.EnumTexture.HART_DEF.data.texture
        val PANEL_SCORE       = SpriteManager.EnumTexture.PANEL_SCORE.data.texture
        val PAUSE             = SpriteManager.EnumTexture.PAUSE.data.texture
        val PL_DEF            = SpriteManager.EnumTexture.PL_DEF.data.texture
        val PL_PRESS          = SpriteManager.EnumTexture.PL_PRESS.data.texture
        val PLAY              = SpriteManager.EnumTexture.PLAY.data.texture
        val RESULT            = SpriteManager.EnumTexture.RESULT.data.texture
        val RL_DEF            = SpriteManager.EnumTexture.RL_DEF.data.texture
        val RL_PRESS          = SpriteManager.EnumTexture.RL_PRESS.data.texture
        val RULES             = SpriteManager.EnumTexture.RULES.data.texture
        val SETTINGS          = SpriteManager.EnumTexture.SETTINGS.data.texture
        val ST_DEF            = SpriteManager.EnumTexture.ST_DEF.data.texture
        val ST_PRESS          = SpriteManager.EnumTexture.ST_PRESS.data.texture
        val TOP               = SpriteManager.EnumTexture.TOP.data.texture


    }

}