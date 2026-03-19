package com.vortemika208.w1n.game.manager.util

import com.vortemika208.w1n.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          val LOADER = SpriteManager.EnumTexture.LOADER.data.texture
     }

    class All {

        private val MENU_1 = SpriteManager.EnumTexture.MENU_1.data.texture
        private val MENU_2 = SpriteManager.EnumTexture.MENU_2.data.texture
        private val MENU_3 = SpriteManager.EnumTexture.MENU_3.data.texture

        val listMenuItem = listOf(MENU_1, MENU_2, MENU_3)

        val BACKGROUND_DEF      = SpriteManager.EnumTexture.BACKGROUND_DEF.data.texture
        val BACKGROUND_SHOP     = SpriteManager.EnumTexture.BACKGROUND_SHOP.data.texture
        val BACKGROUND_SETTINGS = SpriteManager.EnumTexture.BACKGROUND_SETTINGS.data.texture

        val COIN_LEFT             = SpriteManager.EnumTexture.COIN_LEFT.data.texture
        val COIN_RIGHT            = SpriteManager.EnumTexture.COIN_RIGHT.data.texture
        val DAILY_BONUS           = SpriteManager.EnumTexture.DAILY_BONUS.data.texture
        val DAILY_BONUS_600       = SpriteManager.EnumTexture.DAILY_BONUS_600.data.texture
        val LOBBY_DEF             = SpriteManager.EnumTexture.LOBBY_DEF.data.texture
        val LOBBY_PRESS           = SpriteManager.EnumTexture.LOBBY_PRESS.data.texture
        val NOTIFICATION_CHECK    = SpriteManager.EnumTexture.NOTIFICATION_CHECK.data.texture
        val NOTIFICATION_DEF      = SpriteManager.EnumTexture.NOTIFICATION_DEF.data.texture
        val OFF                   = SpriteManager.EnumTexture.OFF.data.texture
        val ON                    = SpriteManager.EnumTexture.ON.data.texture
        val PANEL_COIN            = SpriteManager.EnumTexture.PANEL_COIN.data.texture
        val PANEL_SETTINGS        = SpriteManager.EnumTexture.PANEL_SETTINGS.data.texture
        val PLAY_DEF              = SpriteManager.EnumTexture.PLAY_DEF.data.texture
        val PLAY_PRESS            = SpriteManager.EnumTexture.PLAY_PRESS.data.texture
        val PROGRESS              = SpriteManager.EnumTexture.PROGRESS.data.texture
        val PROGRESS_CIRCLE       = SpriteManager.EnumTexture.PROGRESS_CIRCLE.data.texture
        val PROGRESS_MASK         = SpriteManager.EnumTexture.PROGRESS_MASK.data.texture
        val SETT_DEF              = SpriteManager.EnumTexture.SETT_DEF.data.texture
        val SETT_PRESS            = SpriteManager.EnumTexture.SETT_PRESS.data.texture
        val SETTINGS_GROUP        = SpriteManager.EnumTexture.SETTINGS_GROUP.data.texture
        val SHOP_1K               = SpriteManager.EnumTexture.SHOP_1K.data.texture
        val SHOP_1M               = SpriteManager.EnumTexture.SHOP_1M.data.texture
        val SHOP_DEF              = SpriteManager.EnumTexture.SHOP_DEF.data.texture
        val SHOP_PRESS            = SpriteManager.EnumTexture.SHOP_PRESS.data.texture
        val TRY_YOUR_LUCK         = SpriteManager.EnumTexture.TRY_YOUR_LUCK.data.texture
        val GRAY_COIN_LEFT        = SpriteManager.EnumTexture.GRAY_COIN_LEFT.data.texture
        val GRAY_COIN_RIGHT       = SpriteManager.EnumTexture.GRAY_COIN_RIGHT.data.texture
        val PANEL_ROUETTE         = SpriteManager.EnumTexture.PANEL_ROUETTE.data.texture
        val PANEL_ROULETTE_WIN    = SpriteManager.EnumTexture.PANEL_ROULETTE_WIN.data.texture
        val PANEL_TRY_AGAIN       = SpriteManager.EnumTexture.PANEL_TRY_AGAIN.data.texture
        val ROULETTE              = SpriteManager.EnumTexture.ROULETTE.data.texture
        val MASK                  = SpriteManager.EnumTexture.MASK.data.texture
        val BACKGROUND_ADVENTURES = SpriteManager.EnumTexture.BACKGROUND_ADVENTURES.data.texture
        val BACKGROUND_CHAMPIONS  = SpriteManager.EnumTexture.BACKGROUND_CHAMPIONS.data.texture
        val PANEL_SLOT_GROUP      = SpriteManager.EnumTexture.PANEL_SLOT_GROUP.data.texture
        val PANEL_STAKE           = SpriteManager.EnumTexture.PANEL_STAKE.data.texture
        val SPIN_DEF              = SpriteManager.EnumTexture.SPIN_DEF.data.texture
        val SPIN_PRESS            = SpriteManager.EnumTexture.SPIN_PRESS.data.texture
        val MASK_SLOT_GROUP       = SpriteManager.EnumTexture.MASK_SLOT_GROUP.data.texture
        val COINS                 = SpriteManager.EnumTexture.COINS.data.texture
        val GAIN_DEF              = SpriteManager.EnumTexture.GAIN_DEF.data.texture
        val GAIN_PRESS            = SpriteManager.EnumTexture.GAIN_PRESS.data.texture
        val SHIELD                = SpriteManager.EnumTexture.SHIELD.data.texture
        val BIG_WIN               = SpriteManager.EnumTexture.BIG_WIN.data.texture

        private val _1  = SpriteManager.EnumTexture._1.data.texture
        private val _2  = SpriteManager.EnumTexture._2.data.texture
        private val _3  = SpriteManager.EnumTexture._3.data.texture
        private val _4  = SpriteManager.EnumTexture._4.data.texture
        private val _5  = SpriteManager.EnumTexture._5.data.texture
        private val _6  = SpriteManager.EnumTexture._6.data.texture
        private val _7  = SpriteManager.EnumTexture._7.data.texture
        private val _8  = SpriteManager.EnumTexture._8.data.texture
        private val _9  = SpriteManager.EnumTexture._9.data.texture
        private val _10 = SpriteManager.EnumTexture._10.data.texture
        private val _11 = SpriteManager.EnumTexture._11.data.texture
        private val _12 = SpriteManager.EnumTexture._12.data.texture

        val listSlotItems1 = listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12)

        private val ADV_1  = SpriteManager.EnumTexture.ADV_1.data.texture
        private val ADV_2  = SpriteManager.EnumTexture.ADV_2.data.texture
        private val ADV_3  = SpriteManager.EnumTexture.ADV_3.data.texture
        private val ADV_4  = SpriteManager.EnumTexture.ADV_4.data.texture
        private val ADV_5  = SpriteManager.EnumTexture.ADV_5.data.texture
        private val ADV_6  = SpriteManager.EnumTexture.ADV_6.data.texture
        private val ADV_7  = SpriteManager.EnumTexture.ADV_7.data.texture
        private val ADV_8  = SpriteManager.EnumTexture.ADV_8.data.texture
        private val ADV_9  = SpriteManager.EnumTexture.ADV_9.data.texture
        private val ADV_10 = SpriteManager.EnumTexture.ADV_10.data.texture
        private val ADV_11 = SpriteManager.EnumTexture.ADV_11.data.texture
        private val ADV_12 = SpriteManager.EnumTexture.ADV_12.data.texture

        val listSlotItems2 = listOf(ADV_1, ADV_2, ADV_3, ADV_4, ADV_5, ADV_6, ADV_7, ADV_8, ADV_9, ADV_10, ADV_11, ADV_12)


     }

}