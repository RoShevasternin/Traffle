package com.gorillaz.puzzlegame.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.gorillaz.puzzlegame.game.manager.SpriteManager
import com.gorillaz.puzzlegame.game.utils.gdxGame

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

         val circle  = getRegion("circle")
         val fruit   = getRegion("fruit")
         val gorilla = getRegion("gorilla")
         val light   = getRegion("light")
         val loading = getRegion("loading")
         val sevebs  = getRegion("sevebs")

          val BACKGROUND_0 = SpriteManager.EnumTexture.L_BACKGROUND_0.data.texture

     }

     class All {
         private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
         private fun getRegionAvatar(name: String): TextureRegion = SpriteManager.EnumAtlas.AVATAR.data.atlas.findRegion(name)
         private fun getRegionItem_1(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEM_1.data.atlas.findRegion(name)
         private fun getRegionItem_2(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEM_2.data.atlas.findRegion(name)
         private fun getRegionItem_3(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEM_3.data.atlas.findRegion(name)

         // atlas All ------------------------------------------------------------------------------

         val arrow        = getRegionAll("arrow")
         val btn_def      = getRegionAll("btn_def")
         val btn_press    = getRegionAll("btn_press")
         val gear         = getRegionAll("gear")
         val progress_lvl = getRegionAll("progress_lvl")
         val spin_press   = getRegionAll("spin_press")
         val spin_def     = getRegionAll("spin_def")
         val gem          = getRegionAll("gem")
         val right        = getRegionAll("right")
         val left         = getRegionAll("left")
         val buyed        = getRegionAll("buyed")
         val price_gems   = getRegionAll("price_gems")
         val box_def      = getRegionAll("box_def")
         val box_check    = getRegionAll("box_check")
         val minus        = getRegionAll("minus")
         val plus         = getRegionAll("plus")

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
         val listItem_7 = List(10) { getRegionItem_3("item_7_${it.inc()}") }

         // textures ------------------------------------------------------------------------------

         val BACKGROUND_1 = SpriteManager.EnumTexture.BACKGROUND_1.data.texture
         val BACKGROUND_2 = SpriteManager.EnumTexture.BACKGROUND_2.data.texture
         val BACKGROUND_3 = SpriteManager.EnumTexture.BACKGROUND_3.data.texture
         val BACKGROUND_4 = SpriteManager.EnumTexture.BACKGROUND_4.data.texture
         val BACKGROUND_5 = SpriteManager.EnumTexture.BACKGROUND_5.data.texture
         val BACKGROUND_6 = SpriteManager.EnumTexture.BACKGROUND_6.data.texture
         val BACKGROUND_7 = SpriteManager.EnumTexture.BACKGROUND_7.data.texture
         val BACKGROUND_8 = SpriteManager.EnumTexture.BACKGROUND_8.data.texture

         val listBackground = listOf(
             BACKGROUND_2, BACKGROUND_3, BACKGROUND_4,
             BACKGROUND_5, BACKGROUND_6, BACKGROUND_7, BACKGROUND_8,
         )

         val MASK_LVL_PROGRESS         = SpriteManager.EnumTexture.MASK_LVL_PROGRESS.data.texture
         val SHEEN                     = SpriteManager.EnumTexture.SHEEN.data.texture
         val ROULETTE_CURSOR           = SpriteManager.EnumTexture.ROULETTE_CURSOR.data.texture
         val ROULETTE                  = SpriteManager.EnumTexture.ROULETTE.data.texture
         val LOCATION_IS_BLOCKED       = SpriteManager.EnumTexture.LOCATION_IS_BLOCKED.data.texture

         val PANEL_MAIN                = SpriteManager.EnumTexture.PANEL_MAIN.data.texture
         val PANEL_ROULETTE            = SpriteManager.EnumTexture.PANEL_ROULETTE.data.texture
         val PANEL_SEVENS              = SpriteManager.EnumTexture.PANEL_SEVENS.data.texture
         val PANEL_MENU                = SpriteManager.EnumTexture.PANEL_MENU.data.texture
         val PANEL_ROULETTE_WATCH_ADD  = SpriteManager.EnumTexture.PANEL_ROULETTE_WATCH_ADD.data.texture
         val PANEL_ROULETTE_SPIN_PRICE = SpriteManager.EnumTexture.PANEL_ROULETTE_SPIN_PRICE.data.texture
         val PANEL_SELECT_AVATAR       = SpriteManager.EnumTexture.PANEL_SELECT_AVATAR.data.texture
         val PANEL_NICKNAME            = SpriteManager.EnumTexture.PANEL_NICKNAME.data.texture
         val PANEL_AVATAR              = SpriteManager.EnumTexture.PANEL_AVATAR.data.texture
         val PANEL_ACHIEVEMENT         = SpriteManager.EnumTexture.PANEL_ACHIEVEMENT.data.texture
         val INAPP_1K_GEMS             = SpriteManager.EnumTexture.INAPP_1K_GEMS.data.texture
         val INAPP_10K_GOLD            = SpriteManager.EnumTexture.INAPP_10K_GOLD.data.texture
         val INAPP_100_GEMS            = SpriteManager.EnumTexture.INAPP_100_GEMS.data.texture
         val STORE                     = SpriteManager.EnumTexture.STORE.data.texture
         val PANEL_SHOP_SELECTOR       = SpriteManager.EnumTexture.PANEL_SHOP_SELECTOR.data.texture
         val PANEL_ONE_PUZZLE          = SpriteManager.EnumTexture.PANEL_ONE_PUZZLE.data.texture
         val PANEL_GALLERY             = SpriteManager.EnumTexture.PANEL_GALLERY.data.texture
         val PANEL_SETTINGS_BOTTOM     = SpriteManager.EnumTexture.PANEL_SETTINGS_BOTTOM.data.texture
         val PANEL_SETTINGS            = SpriteManager.EnumTexture.PANEL_SETTINGS.data.texture
         val PANEL_SELECT_LOCATION     = SpriteManager.EnumTexture.PANEL_SELECT_LOCATION.data.texture
         val PANEL_MAX_JACKPOT         = SpriteManager.EnumTexture.PANEL_MAX_JACKPOT.data.texture
         val PANEL_LOCATION            = SpriteManager.EnumTexture.PANEL_LOCATION.data.texture
         val PANEL_INCREASE_JACKPOT    = SpriteManager.EnumTexture.PANEL_INCREASE_JACKPOT.data.texture
         val PANEL_MAX_BET             = SpriteManager.EnumTexture.PANEL_MAX_BET.data.texture
         val PANEL_SLOTS               = SpriteManager.EnumTexture.PANEL_SLOTS.data.texture
         val PANEL_BET                 = SpriteManager.EnumTexture.PANEL_BET.data.texture

         private val PUZZLE_1  = SpriteManager.EnumTexture.PUZZLE_1.data.texture
         private val PUZZLE_2  = SpriteManager.EnumTexture.PUZZLE_2.data.texture
         private val PUZZLE_3  = SpriteManager.EnumTexture.PUZZLE_3.data.texture
         private val PUZZLE_4  = SpriteManager.EnumTexture.PUZZLE_4.data.texture
         private val PUZZLE_5  = SpriteManager.EnumTexture.PUZZLE_5.data.texture
         private val PUZZLE_6  = SpriteManager.EnumTexture.PUZZLE_6.data.texture
         private val PUZZLE_7  = SpriteManager.EnumTexture.PUZZLE_7.data.texture
         private val PUZZLE_8  = SpriteManager.EnumTexture.PUZZLE_8.data.texture
         private val PUZZLE_9  = SpriteManager.EnumTexture.PUZZLE_9.data.texture
         private val PUZZLE_10 = SpriteManager.EnumTexture.PUZZLE_10.data.texture

         private val PERSONAGE_1 = SpriteManager.EnumTexture.PERSONAGE_1.data.texture
         private val PERSONAGE_2 = SpriteManager.EnumTexture.PERSONAGE_2.data.texture
         private val PERSONAGE_3 = SpriteManager.EnumTexture.PERSONAGE_3.data.texture
         private val PERSONAGE_4 = SpriteManager.EnumTexture.PERSONAGE_4.data.texture
         private val PERSONAGE_5 = SpriteManager.EnumTexture.PERSONAGE_5.data.texture
         private val PERSONAGE_6 = SpriteManager.EnumTexture.PERSONAGE_6.data.texture
         private val PERSONAGE_7 = SpriteManager.EnumTexture.PERSONAGE_7.data.texture

         val SHAPE_1 = SpriteManager.EnumTexture.SHAPE_1.data.texture
         val SHAPE_2 = SpriteManager.EnumTexture.SHAPE_2.data.texture
         val SHAPE_3 = SpriteManager.EnumTexture.SHAPE_3.data.texture
         val SHAPE_4 = SpriteManager.EnumTexture.SHAPE_4.data.texture
         val SHAPE_5 = SpriteManager.EnumTexture.SHAPE_5.data.texture

         val listPuzzle = listOf(
             PUZZLE_1, PUZZLE_2, PUZZLE_3, PUZZLE_4, PUZZLE_5,
             PUZZLE_6, PUZZLE_7, PUZZLE_8, PUZZLE_9, PUZZLE_10,
         )
         val listPersonage = listOf(PERSONAGE_1, PERSONAGE_2, PERSONAGE_3, PERSONAGE_4, PERSONAGE_5, PERSONAGE_6, PERSONAGE_7)
     }

}