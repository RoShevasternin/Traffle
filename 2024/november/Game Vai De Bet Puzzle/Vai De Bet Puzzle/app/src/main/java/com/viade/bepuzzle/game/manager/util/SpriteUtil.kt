package com.viade.bepuzzle.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.viade.bepuzzle.game.manager.SpriteManager

class SpriteUtil {

     class Loader {
          val background = SpriteManager.EnumTexture.L_background.data.texture
          val basketball = SpriteManager.EnumTexture.L_basketball.data.texture
          val football   = SpriteManager.EnumTexture.L_football.data.texture
          val hokey      = SpriteManager.EnumTexture.L_hokey.data.texture
          val loading    = SpriteManager.EnumTexture.L_loading.data.texture
          val sports     = SpriteManager.EnumTexture.L_sports.data.texture
          val tennis     = SpriteManager.EnumTexture.L_tennis.data.texture

          private val circle1 = SpriteManager.EnumTexture.L_1.data.texture
          private val circle2 = SpriteManager.EnumTexture.L_2.data.texture
          private val circle3 = SpriteManager.EnumTexture.L_3.data.texture
          private val circle4 = SpriteManager.EnumTexture.L_4.data.texture
          private val circle5 = SpriteManager.EnumTexture.L_5.data.texture
          private val circle6 = SpriteManager.EnumTexture.L_6.data.texture
          private val circle7 = SpriteManager.EnumTexture.L_7.data.texture
          private val circle8 = SpriteManager.EnumTexture.L_8.data.texture

          val listCircles = listOf(circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8)

     }

     class All {
          private fun get9Path(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          // 9 path ------------------------------------------------------------------------------

          val top_panel = get9Path("top_panel")

          // textures ------------------------------------------------------------------------------

          val left_def           = SpriteManager.EnumTexture.A_left_def.data.texture
          val left_press         = SpriteManager.EnumTexture.A_left_press.data.texture
          val menu               = SpriteManager.EnumTexture.A_menu.data.texture
          val menu_def           = SpriteManager.EnumTexture.A_menu_def.data.texture
          val menu_press         = SpriteManager.EnumTexture.A_menu_press.data.texture
          val plus_coin_def      = SpriteManager.EnumTexture.A_plus_coin_def.data.texture
          val plus_coin_press    = SpriteManager.EnumTexture.A_plus_coin_press.data.texture
          val right_def          = SpriteManager.EnumTexture.A_right_def.data.texture
          val right_press        = SpriteManager.EnumTexture.A_right_press.data.texture
          val sports             = SpriteManager.EnumTexture.A_sports.data.texture
          val back_def           = SpriteManager.EnumTexture.A_back_def.data.texture
          val back_press         = SpriteManager.EnumTexture.A_back_press.data.texture
          val check              = SpriteManager.EnumTexture.A_check.data.texture
          val level_of_diff      = SpriteManager.EnumTexture.A_level_of_diff.data.texture
          val locked             = SpriteManager.EnumTexture.A_locked.data.texture
          val play_def           = SpriteManager.EnumTexture.A_play_def.data.texture
          val play_press         = SpriteManager.EnumTexture.A_play_press.data.texture
          val one_def            = SpriteManager.EnumTexture.A_one_def.data.texture
          val one_press          = SpriteManager.EnumTexture.A_one_press.data.texture
          val panel_coins        = SpriteManager.EnumTexture.A_panel_coins.data.texture
          val privacy_def        = SpriteManager.EnumTexture.A_privacy_def.data.texture
          val privacy_press      = SpriteManager.EnumTexture.A_privacy_press.data.texture
          val settings           = SpriteManager.EnumTexture.A_settings.data.texture
          val two_def            = SpriteManager.EnumTexture.A_two_def.data.texture
          val two_press          = SpriteManager.EnumTexture.A_two_press.data.texture
          val web_def            = SpriteManager.EnumTexture.A_web_def.data.texture
          val web_press          = SpriteManager.EnumTexture.A_web_press.data.texture
          val zero_def           = SpriteManager.EnumTexture.A_zero_def.data.texture
          val zero_press         = SpriteManager.EnumTexture.A_zero_press.data.texture
          val plus_coin_simple   = SpriteManager.EnumTexture.A_plus_coin_simple.data.texture
          val k1_def             = SpriteManager.EnumTexture.A_k1_def.data.texture
          val k1_press           = SpriteManager.EnumTexture.A_k1_press.data.texture
          val k2_def             = SpriteManager.EnumTexture.A_k2_def.data.texture
          val k2_press           = SpriteManager.EnumTexture.A_k2_press.data.texture
          val k5_def             = SpriteManager.EnumTexture.A_k5_def.data.texture
          val k5_press           = SpriteManager.EnumTexture.A_k5_press.data.texture
          val panel_shop         = SpriteManager.EnumTexture.A_panel_shop.data.texture
          val gallery_empty      = SpriteManager.EnumTexture.A_gallery_empty.data.texture
          val gold               = SpriteManager.EnumTexture.A_gold.data.texture
          val box_pause          = SpriteManager.EnumTexture.A_box_pause.data.texture
          val box_play           = SpriteManager.EnumTexture.A_box_play.data.texture
          val fail               = SpriteManager.EnumTexture.A_fail.data.texture
          val game_gray          = SpriteManager.EnumTexture.A_game_gray.data.texture
          val game_items         = SpriteManager.EnumTexture.A_game_items.data.texture
          val home_def           = SpriteManager.EnumTexture.A_home_def.data.texture
          val home_press         = SpriteManager.EnumTexture.A_home_press.data.texture
          val use_def            = SpriteManager.EnumTexture.A_use_def.data.texture
          val use_press          = SpriteManager.EnumTexture.A_use_press.data.texture
          val win                = SpriteManager.EnumTexture.A_win.data.texture
          val aword              = SpriteManager.EnumTexture.A_aword.data.texture
          val get_def            = SpriteManager.EnumTexture.A_get_def.data.texture
          val get_press          = SpriteManager.EnumTexture.A_get_press.data.texture
          val geted              = SpriteManager.EnumTexture.A_geted.data.texture

          private val star_easy   = SpriteManager.EnumTexture.A_star_easy.data.texture
          private val star_hard   = SpriteManager.EnumTexture.A_star_hard.data.texture
          private val star_medium = SpriteManager.EnumTexture.A_star_medium.data.texture

          private val sport_1 = SpriteManager.EnumTexture.A_sport_1.data.texture
          private val sport_2 = SpriteManager.EnumTexture.A_sport_2.data.texture
          private val sport_3 = SpriteManager.EnumTexture.A_sport_3.data.texture
          private val sport_4 = SpriteManager.EnumTexture.A_sport_4.data.texture

          private val lvl_1 = SpriteManager.EnumTexture.A_lvl_1.data.texture
          private val lvl_2 = SpriteManager.EnumTexture.A_lvl_2.data.texture
          private val lvl_3 = SpriteManager.EnumTexture.A_lvl_3.data.texture
          private val lvl_4 = SpriteManager.EnumTexture.A_lvl_4.data.texture
          private val lvl_5 = SpriteManager.EnumTexture.A_lvl_5.data.texture
          private val lvl_6 = SpriteManager.EnumTexture.A_lvl_6.data.texture

          private val card_1_1 = SpriteManager.EnumTexture.A_card_1_1.data.texture
          private val card_1_2 = SpriteManager.EnumTexture.A_card_1_2.data.texture
          private val card_1_3 = SpriteManager.EnumTexture.A_card_1_3.data.texture
          private val card_1_4 = SpriteManager.EnumTexture.A_card_1_4.data.texture
          private val card_1_5 = SpriteManager.EnumTexture.A_card_1_5.data.texture
          private val card_1_6 = SpriteManager.EnumTexture.A_card_1_6.data.texture
          private val card_2_1 = SpriteManager.EnumTexture.A_card_2_1.data.texture
          private val card_2_2 = SpriteManager.EnumTexture.A_card_2_2.data.texture
          private val card_2_3 = SpriteManager.EnumTexture.A_card_2_3.data.texture
          private val card_2_4 = SpriteManager.EnumTexture.A_card_2_4.data.texture
          private val card_2_5 = SpriteManager.EnumTexture.A_card_2_5.data.texture
          private val card_2_6 = SpriteManager.EnumTexture.A_card_2_6.data.texture
          private val card_3_1 = SpriteManager.EnumTexture.A_card_3_1.data.texture
          private val card_3_2 = SpriteManager.EnumTexture.A_card_3_2.data.texture
          private val card_3_3 = SpriteManager.EnumTexture.A_card_3_3.data.texture
          private val card_3_4 = SpriteManager.EnumTexture.A_card_3_4.data.texture
          private val card_3_5 = SpriteManager.EnumTexture.A_card_3_5.data.texture
          private val card_3_6 = SpriteManager.EnumTexture.A_card_3_6.data.texture
          private val card_4_1 = SpriteManager.EnumTexture.A_card_4_1.data.texture
          private val card_4_2 = SpriteManager.EnumTexture.A_card_4_2.data.texture
          private val card_4_3 = SpriteManager.EnumTexture.A_card_4_3.data.texture
          private val card_4_4 = SpriteManager.EnumTexture.A_card_4_4.data.texture
          private val card_4_5 = SpriteManager.EnumTexture.A_card_4_5.data.texture
          private val card_4_6 = SpriteManager.EnumTexture.A_card_4_6.data.texture

          private val photo_1_1 = SpriteManager.EnumTexture.A_photo_1_1.data.texture
          private val photo_1_2 = SpriteManager.EnumTexture.A_photo_1_2.data.texture
          private val photo_1_3 = SpriteManager.EnumTexture.A_photo_1_3.data.texture
          private val photo_1_4 = SpriteManager.EnumTexture.A_photo_1_4.data.texture
          private val photo_1_5 = SpriteManager.EnumTexture.A_photo_1_5.data.texture
          private val photo_1_6 = SpriteManager.EnumTexture.A_photo_1_6.data.texture
          private val photo_2_1 = SpriteManager.EnumTexture.A_photo_2_1.data.texture
          private val photo_2_2 = SpriteManager.EnumTexture.A_photo_2_2.data.texture
          private val photo_2_3 = SpriteManager.EnumTexture.A_photo_2_3.data.texture
          private val photo_2_4 = SpriteManager.EnumTexture.A_photo_2_4.data.texture
          private val photo_2_5 = SpriteManager.EnumTexture.A_photo_2_5.data.texture
          private val photo_2_6 = SpriteManager.EnumTexture.A_photo_2_6.data.texture
          private val photo_3_1 = SpriteManager.EnumTexture.A_photo_3_1.data.texture
          private val photo_3_2 = SpriteManager.EnumTexture.A_photo_3_2.data.texture
          private val photo_3_3 = SpriteManager.EnumTexture.A_photo_3_3.data.texture
          private val photo_3_4 = SpriteManager.EnumTexture.A_photo_3_4.data.texture
          private val photo_3_5 = SpriteManager.EnumTexture.A_photo_3_5.data.texture
          private val photo_3_6 = SpriteManager.EnumTexture.A_photo_3_6.data.texture
          private val photo_4_1 = SpriteManager.EnumTexture.A_photo_4_1.data.texture
          private val photo_4_2 = SpriteManager.EnumTexture.A_photo_4_2.data.texture
          private val photo_4_3 = SpriteManager.EnumTexture.A_photo_4_3.data.texture
          private val photo_4_4 = SpriteManager.EnumTexture.A_photo_4_4.data.texture
          private val photo_4_5 = SpriteManager.EnumTexture.A_photo_4_5.data.texture
          private val photo_4_6 = SpriteManager.EnumTexture.A_photo_4_6.data.texture

          private val listCategory_1 = listOf(card_1_1, card_1_2, card_1_3, card_1_4, card_1_5, card_1_6)
          private val listCategory_2 = listOf(card_2_1, card_2_2, card_2_3, card_2_4, card_2_5, card_2_6)
          private val listCategory_3 = listOf(card_3_1, card_3_2, card_3_3, card_3_4, card_3_5, card_3_6)
          private val listCategory_4 = listOf(card_4_1, card_4_2, card_4_3, card_4_4, card_4_5, card_4_6)

          private val listPhoto_1 = listOf(photo_1_1, photo_1_2, photo_1_3, photo_1_4, photo_1_5, photo_1_6)
          private val listPhoto_2 = listOf(photo_2_1, photo_2_2, photo_2_3, photo_2_4, photo_2_5, photo_2_6)
          private val listPhoto_3 = listOf(photo_3_1, photo_3_2, photo_3_3, photo_3_4, photo_3_5, photo_3_6)
          private val listPhoto_4 = listOf(photo_4_1, photo_4_2, photo_4_3, photo_4_4, photo_4_5, photo_4_6)

          val listSport = listOf(sport_1, sport_2, sport_3, sport_4)
          val listLVL   = listOf(lvl_1, lvl_2, lvl_3, lvl_4, lvl_5, lvl_6)
          val listStars = listOf(star_easy, star_medium, star_hard)

          val listCard  = listOf(listCategory_1, listCategory_2, listCategory_3, listCategory_4)
          val listPhoto = listOf(listPhoto_1, listPhoto_2, listPhoto_3, listPhoto_4)
     }

}