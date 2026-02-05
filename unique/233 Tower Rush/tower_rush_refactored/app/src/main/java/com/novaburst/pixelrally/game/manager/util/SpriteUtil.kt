/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.novaburst.pixelrally.game.manager.TextureController

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = TextureController.EnumAtlas.LOADER.data.atlas.findRegion(name)

         val circle = getRegion("circle")
         val fruit   = getRegion("fruit")
         val gorilla = getRegion("gorilla")
         val light = getRegion("light")
         val loading = getRegion("loading")
         val sevebs = getRegion("sevebs")

          val BACKGROUND_0 = TextureController.EnumTexture.L_BACKGROUND_0.data.texture

     }

     class All {
         private fun getRegionAll(name: String): TextureRegion = TextureController.EnumAtlas.ALL.data.atlas.findRegion(name)
         private fun getRegionAvatar(name: String): TextureRegion = TextureController.EnumAtlas.AVATAR.data.atlas.findRegion(name)
         // Function implementation
         private fun getRegionItem_1(name: String): TextureRegion = TextureController.EnumAtlas.ITEM_1.data.atlas.findRegion(name)
         private fun getRegionItem_2(name: String): TextureRegion = TextureController.EnumAtlas.ITEM_2.data.atlas.findRegion(name)

         // atlas All ------------------------------------------------------------------------------

         val arrow = getRegionAll("arrow")
         val btn_def = getRegionAll("btn_def")
         val btn_press = getRegionAll("btn_press")
         val gear = getRegionAll("gear")
         val progress_lvl = getRegionAll("progress_lvl")
         val spin_press   = getRegionAll("spin_press")
         val spin_def = getRegionAll("spin_def")
         val gem = getRegionAll("gem")
         val right = getRegionAll("right")
         val left         = getRegionAll("left")
         val buyed        = getRegionAll("buyed")
         val price_gems   = getRegionAll("price_gems")
         val box_def = getRegionAll("box_def")
         val box_check = getRegionAll("box_check")
         val minus = getRegionAll("minus")
         val plus = getRegionAll("plus")

         val new_btn_back_def   = getRegionAll("new_btn_back_def")
         val new_btn_back_press = getRegionAll("new_btn_back_press")
         val new_play_def = getRegionAll("new_play_def")
         val new_play_press = getRegionAll("new_play_press")

         val minus_def   = getRegionAll("minus_def")
         val minus_press = getRegionAll("minus_press")
         val plus_def = getRegionAll("plus_def")
         val plus_press = getRegionAll("plus_press")

         // atlas Avatar ------------------------------------------------------------------------------

         val avatar = getRegionAvatar("avatar")

         val listAvatar = List(16) { getRegionAvatar("avatar_${it.inc()}") }

         // atlas Items ------------------------------------------------------------------------------
         val jackpot = getRegionItem_1("jackpot")

         val listItem_1 = List(10) { getRegionItem_1("item_1_${it.inc()}") }
         val listItem_2 = List(10) { getRegionItem_1("item_2_${it.inc()}") }
         val listItem_3 = List(10) { getRegionItem_1("item_3_${it.inc()}") }
         val listItem_4 = List(10) { getRegionItem_2("item_4_${it.inc()}") }
         val listItem_5 = List(10) { getRegionItem_2("item_5_${it.inc()}") }
         val listItem_6 = List(10) { getRegionItem_2("item_6_${it.inc()}") }
         val listItem_7 = List(10) { getRegionItem_2("item_7_${it.inc()}") }

         // textures ------------------------------------------------------------------------------

         val BACKGROUND_1 = TextureController.EnumTexture.BACKGROUND_1.data.texture
         val BACKGROUND_2 = TextureController.EnumTexture.BACKGROUND_2.data.texture
         val BACKGROUND_3 = TextureController.EnumTexture.BACKGROUND_3.data.texture
         val BACKGROUND_4 = TextureController.EnumTexture.BACKGROUND_4.data.texture
         val BACKGROUND_5 = TextureController.EnumTexture.BACKGROUND_5.data.texture
         val BACKGROUND_6 = TextureController.EnumTexture.BACKGROUND_6.data.texture
         val BACKGROUND_7 = TextureController.EnumTexture.BACKGROUND_7.data.texture
         val BACKGROUND_8 = TextureController.EnumTexture.BACKGROUND_8.data.texture

         val listBackground = listOf(
             BACKGROUND_2, BACKGROUND_3, BACKGROUND_4,
             BACKGROUND_5, BACKGROUND_6, BACKGROUND_7, BACKGROUND_8,
         )

         val MASK_LVL_PROGRESS = TextureController.EnumTexture.MASK_LVL_PROGRESS.data.texture
         val SHEEN                     = TextureController.EnumTexture.SHEEN.data.texture
         val ROULETTE_CURSOR           = TextureController.EnumTexture.ROULETTE_CURSOR.data.texture
         val ROULETTE                  = TextureController.EnumTexture.ROULETTE.data.texture
         val LOCATION_IS_BLOCKED       = TextureController.EnumTexture.LOCATION_IS_BLOCKED.data.texture
         val NEW_ROULETTE_BACK = TextureController.EnumTexture.NEW_ROULETTE_BACK.data.texture

         val PANEL_MAIN                = TextureController.EnumTexture.PANEL_MAIN.data.texture
         val PANEL_ROULETTE = TextureController.EnumTexture.PANEL_ROULETTE.data.texture
         val PANEL_SEVENS              = TextureController.EnumTexture.PANEL_SEVENS.data.texture
         val PANEL_MENU                = TextureController.EnumTexture.PANEL_MENU.data.texture
         val PANEL_ROULETTE_WATCH_ADD  = TextureController.EnumTexture.PANEL_ROULETTE_WATCH_ADD.data.texture
         val PANEL_ROULETTE_SPIN_PRICE = TextureController.EnumTexture.PANEL_ROULETTE_SPIN_PRICE.data.texture
         val PANEL_SELECT_AVATAR       = TextureController.EnumTexture.PANEL_SELECT_AVATAR.data.texture
         val PANEL_NICKNAME            = TextureController.EnumTexture.PANEL_NICKNAME.data.texture
         val PANEL_AVATAR              = TextureController.EnumTexture.PANEL_AVATAR.data.texture
         val PANEL_ACHIEVEMENT         = TextureController.EnumTexture.PANEL_ACHIEVEMENT.data.texture
         val INAPP_1K_GEMS = TextureController.EnumTexture.INAPP_1K_GEMS.data.texture
         val INAPP_10K_GOLD            = TextureController.EnumTexture.INAPP_10K_GOLD.data.texture
         val INAPP_100_GEMS = TextureController.EnumTexture.INAPP_100_GEMS.data.texture
         val STORE                     = TextureController.EnumTexture.STORE.data.texture
         val PANEL_SHOP_SELECTOR = TextureController.EnumTexture.PANEL_SHOP_SELECTOR.data.texture
         val PANEL_ONE_PUZZLE = TextureController.EnumTexture.PANEL_ONE_PUZZLE.data.texture
         val PANEL_GALLERY             = TextureController.EnumTexture.PANEL_GALLERY.data.texture
         val PANEL_SETTINGS_BOTTOM     = TextureController.EnumTexture.PANEL_SETTINGS_BOTTOM.data.texture
         val PANEL_SETTINGS = TextureController.EnumTexture.PANEL_SETTINGS.data.texture
         val PANEL_SELECT_LOCATION = TextureController.EnumTexture.PANEL_SELECT_LOCATION.data.texture
         val PANEL_MAX_JACKPOT         = TextureController.EnumTexture.PANEL_MAX_JACKPOT.data.texture
         val PANEL_LOCATION = TextureController.EnumTexture.PANEL_LOCATION.data.texture
         val PANEL_INCREASE_JACKPOT = TextureController.EnumTexture.PANEL_INCREASE_JACKPOT.data.texture
         val PANEL_MAX_BET = TextureController.EnumTexture.PANEL_MAX_BET.data.texture
         val PANEL_SLOTS               = TextureController.EnumTexture.PANEL_SLOTS.data.texture
         val PANEL_BET                 = TextureController.EnumTexture.PANEL_BET.data.texture

         private val PUZZLE_1 = TextureController.EnumTexture.PUZZLE_1.data.texture
         private val PUZZLE_2  = TextureController.EnumTexture.PUZZLE_2.data.texture
         private val PUZZLE_3  = TextureController.EnumTexture.PUZZLE_3.data.texture
         private val PUZZLE_4 = TextureController.EnumTexture.PUZZLE_4.data.texture
         private val PUZZLE_5 = TextureController.EnumTexture.PUZZLE_5.data.texture
         private val PUZZLE_6 = TextureController.EnumTexture.PUZZLE_6.data.texture
         private val PUZZLE_7 = TextureController.EnumTexture.PUZZLE_7.data.texture
         private val PUZZLE_8 = TextureController.EnumTexture.PUZZLE_8.data.texture
         private val PUZZLE_9  = TextureController.EnumTexture.PUZZLE_9.data.texture
         private val PUZZLE_10 = TextureController.EnumTexture.PUZZLE_10.data.texture

         private val PERSONAGE_1 = TextureController.EnumTexture.PERSONAGE_1.data.texture
         private val PERSONAGE_2 = TextureController.EnumTexture.PERSONAGE_2.data.texture
         private val PERSONAGE_3 = TextureController.EnumTexture.PERSONAGE_3.data.texture
         private val PERSONAGE_4 = TextureController.EnumTexture.PERSONAGE_4.data.texture
         private val PERSONAGE_5 = TextureController.EnumTexture.PERSONAGE_5.data.texture
         private val PERSONAGE_6 = TextureController.EnumTexture.PERSONAGE_6.data.texture
         private val PERSONAGE_7 = TextureController.EnumTexture.PERSONAGE_7.data.texture

         val SHAPE_1 = TextureController.EnumTexture.SHAPE_1.data.texture
         val SHAPE_2 = TextureController.EnumTexture.SHAPE_2.data.texture
         val SHAPE_3 = TextureController.EnumTexture.SHAPE_3.data.texture
         val SHAPE_4 = TextureController.EnumTexture.SHAPE_4.data.texture
         val SHAPE_5 = TextureController.EnumTexture.SHAPE_5.data.texture

         val listPuzzle = listOf(
             PUZZLE_1, PUZZLE_2, PUZZLE_3, PUZZLE_4, PUZZLE_5,
             PUZZLE_6, PUZZLE_7, PUZZLE_8, PUZZLE_9, PUZZLE_10,
         )
         val listPersonage = listOf(PERSONAGE_1, PERSONAGE_2, PERSONAGE_3, PERSONAGE_4, PERSONAGE_5, PERSONAGE_6, PERSONAGE_7)
     }

}