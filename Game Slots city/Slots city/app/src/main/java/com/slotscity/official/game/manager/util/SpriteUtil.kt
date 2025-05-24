package com.slotscity.official.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.slotscity.official.game.manager.SpriteManager
import com.slotscity.official.game.utils.region

class SpriteUtil {

     class LoaderAssets {
          val logo      = SpriteManager.EnumTexture.logo.data.texture
          val logo_mask = SpriteManager.EnumTexture.logo_mask.data.texture
     }

     class AllAssets {
          private fun getRegionGame(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionItems(name: String): TextureRegion = SpriteManager.EnumAtlas.ITEMS.data.atlas.findRegion(name)

          val double_shans      = getRegionGame("double_shans")
          val gates_of_olimpus  = getRegionGame("gates_of_olimpus")
          val info_deff         = getRegionGame("info_deff")
          val info_press        = getRegionGame("info_press")
          val minus_dis         = getRegionGame("minus_dis")
          val minus_press       = getRegionGame("minus_press")
          val off               = getRegionGame("off")
          val on                = getRegionGame("on")
          val pipka_green       = getRegionGame("pipka_green")
          val pipka_red         = getRegionGame("pipka_red")
          val play_deff         = getRegionGame("play_deff")
          val play_press        = getRegionGame("play_press")
          val plus_def          = getRegionGame("plus_def")
          val plus_press        = getRegionGame("plus_press")
          val slot_city_logo    = getRegionGame("slot_city_logo")
          val spin_btn_deff     = getRegionGame("spin_btn_deff")
          val spin_btn_press    = getRegionGame("spin_btn_press")
          val spin_def          = getRegionGame("spin_def")
          val spin_press        = getRegionGame("spin_press")
          val user_icon         = getRegionGame("user_icon")
          val volume_background = getRegionGame("volume_background")
          val volume_progress   = getRegionGame("volume_progress")
          val glow              = getRegionGame("glow")
          val menu_def          = getRegionGame("menu_def")
          val menu_press        = getRegionGame("menu_press")
          val credit            = getRegionGame("credit")
          val v_on              = getRegionGame("v_on")
          val v_off             = getRegionGame("v_off")

          val listText   = List(4) { getRegionGame("text-${it.inc()}") }
          val listVolume = List(4) { getRegionGame("volume-${it.inc()}") }

          val scatter  = getRegionItems("scatter")
          val listItem = mutableListOf<TextureRegion>().apply {
               repeat(26) { add(getRegionItems("${it.inc()}")) }
               add(scatter)
          }

          val Gates_of_Olympus          = SpriteManager.EnumTexture.Gates_of_Olympus.data.texture
          val gates                     = SpriteManager.EnumTexture.gates.data.texture
          val slot_group                = SpriteManager.EnumTexture.slot_group.data.texture
          val slot_group_mask           = SpriteManager.EnumTexture.slot_group_mask.data.texture
          val svechenie                 = SpriteManager.EnumTexture.svechenie.data.texture
          val user_icon_mask            = SpriteManager.EnumTexture.user_icon_mask.data.texture
          val volume_mask               = SpriteManager.EnumTexture.volume_mask.data.texture
          val launcher_svecheni         = SpriteManager.EnumTexture.launcher_svecheni.data.texture
          val launchers                 = SpriteManager.EnumTexture.launchers.data.texture

          val info_0 = SpriteManager.EnumTexture.info_0.data.texture
          val info_1 = SpriteManager.EnumTexture.info_1.data.texture
          val info_2 = SpriteManager.EnumTexture.info_2.data.texture
          val info_3 = SpriteManager.EnumTexture.info_3.data.texture
          val info_4 = SpriteManager.EnumTexture.info_4.data.texture
          val info_5 = SpriteManager.EnumTexture.info_5.data.texture

          val bonus_def  = SpriteManager.EnumTexture.bonus_def.data.texture
          val bonus_dis  = SpriteManager.EnumTexture.bonus_dis.data.texture
          val bonus_glow = SpriteManager.EnumTexture.bonus_glow.data.texture
          val sunduk     = SpriteManager.EnumTexture.sunduk.data.texture
          val svetka     = SpriteManager.EnumTexture.svetka.data.texture

          val listInfo = listOf(
               info_0.region,
               info_1.region,
               info_2.region,
               info_3.region,
               info_4.region,
               info_5.region,
          )
     }

     class CarnavalCatAssets {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.CarnavalCatAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionItems(name: String): TextureRegion = SpriteManager.CarnavalCatAtlas.ITEMS.data.atlas.findRegion(name)

          val autospin_def   = getRegionAll("autospin_def")
          val autospin_dis   = getRegionAll("autospin_dis")
          val autospin_press = getRegionAll("autospin_press")
          val speed_def      = getRegionAll("speed_def")
          val speed_dis      = getRegionAll("speed_dis")
          val speed_press    = getRegionAll("speed_press")
          val spin_def       = getRegionAll("spin_def")
          val spin_dis       = getRegionAll("spin_dis")
          val glow           = getRegionAll("glow")

          val wild     = getRegionItems("wild")
          val listItem = mutableListOf<TextureRegion>().apply {
               repeat(14) { add(getRegionItems("${it.inc()}")) }
               add(wild)
          }

          val BACKGROUND   = SpriteManager.CarnavalCatTexture.BACKGROUND.data.texture
          val STATIC_FIELD = SpriteManager.CarnavalCatTexture.STATIC_FIELD.data.texture
     }

     class TreasureSnipesAssets {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.TreasureSnipesAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionItems(name: String): TextureRegion = SpriteManager.TreasureSnipesAtlas.ITEMS.data.atlas.findRegion(name)

          val glow     = getRegionAll("glow")
          val spin_def = getRegionAll("spin_def")
          val spin_dis = getRegionAll("spin_dis")

          val listItem = List(12) { getRegionItems("${it.inc()}") }

          val BACKGROUND = SpriteManager.TreasureSnipesTexture.BACKGROUND.data.texture
          val SLOT_MASK  = SpriteManager.TreasureSnipesTexture.SLOT_MASK.data.texture
          val PANEL      = SpriteManager.TreasureSnipesTexture.PANEL.data.texture
     }

     class SweetBonanzaAssets {
          private fun getRegionAll(name: String): TextureRegion = SpriteManager.SweetBonanzaAtlas.ALL.data.atlas.findRegion(name)
          private fun getRegionItems(name: String): TextureRegion = SpriteManager.SweetBonanzaAtlas.ITEMS.data.atlas.findRegion(name)

          val double = getRegionAll("double")
          val glow   = getRegionAll("glow")
          val title  = getRegionAll("title")
          val vikl   = getRegionAll("vikl")
          val vkl    = getRegionAll("vkl")

          val listItem = List(20) { getRegionItems("${it.inc()}") }

          val BACKGROUND = SpriteManager.SweetBonanzaTexture.BACKGROUND.data.texture
          val SLOT_GROUP = SpriteManager.SweetBonanzaTexture.SLOT_GROUP.data.texture
     }



}