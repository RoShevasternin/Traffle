package com.monkeystreet.roadracejungle.game.manager.util

import com.monkeystreet.roadracejungle.game.manager.SpriteManager
import com.monkeystreet.roadracejungle.game.utils.TextureEmpty

class SpriteUtil {

    class Loader {
        //val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val LOADER     = SpriteManager.EnumTexture.LOADER.data.texture
    }

    class All {

        private val _1 = SpriteManager.EnumTexture._1.data.texture
        private val _2 = SpriteManager.EnumTexture._2.data.texture
        private val _3 = SpriteManager.EnumTexture._3.data.texture
        private val _4 = SpriteManager.EnumTexture._4.data.texture
        private val _5 = SpriteManager.EnumTexture._5.data.texture
        private val _6 = SpriteManager.EnumTexture._6.data.texture

        val listSett = listOf(_1, _2, _3, _4, _5, _6)

        val A                 = SpriteManager.EnumTexture.A.data.texture
        val B                 = SpriteManager.EnumTexture.B.data.texture
        val AI_TURN           = SpriteManager.EnumTexture.AI_TURN.data.texture
        val ARROW             = SpriteManager.EnumTexture.ARROW.data.texture
        val BACKGROUND        = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val BACKGROUND_GAME   = SpriteManager.EnumTexture.BACKGROUND_GAME.data.texture
        val BACKGROUND_LEADER = SpriteManager.EnumTexture.BACKGROUND_LEADER.data.texture
        val BTNS              = SpriteManager.EnumTexture.BTNS.data.texture
        val CIRCLE_A          = SpriteManager.EnumTexture.CIRCLE_A.data.texture
        val CIRCLE_B          = SpriteManager.EnumTexture.CIRCLE_B.data.texture
        val FINISH            = SpriteManager.EnumTexture.FINISH.data.texture
        val HTP_1             = SpriteManager.EnumTexture.HTP_1.data.texture
        val HTP_2             = SpriteManager.EnumTexture.HTP_2.data.texture
        val LEADERBOARD       = SpriteManager.EnumTexture.LEADERBOARD.data.texture
        val MINI              = SpriteManager.EnumTexture.MINI.data.texture
        val MN_DEF            = SpriteManager.EnumTexture.MN_DEF.data.texture
        val MN_PRESS          = SpriteManager.EnumTexture.MN_PRESS.data.texture
        val MOVING            = SpriteManager.EnumTexture.MOVING.data.texture
        val NX_DEF            = SpriteManager.EnumTexture.NX_DEF.data.texture
        val NX_PRESS          = SpriteManager.EnumTexture.NX_PRESS.data.texture
        val PANEL             = SpriteManager.EnumTexture.PANEL.data.texture
        val POINT             = SpriteManager.EnumTexture.POINT.data.texture
        val REST_DEF          = SpriteManager.EnumTexture.REST_DEF.data.texture
        val REST_PRESS        = SpriteManager.EnumTexture.REST_PRESS.data.texture
        val SHAKE             = SpriteManager.EnumTexture.SHAKE.data.texture
        val START             = SpriteManager.EnumTexture.START.data.texture
        val WIN               = SpriteManager.EnumTexture.WIN.data.texture
        val LOSE              = SpriteManager.EnumTexture.LOSE.data.texture


    }

}