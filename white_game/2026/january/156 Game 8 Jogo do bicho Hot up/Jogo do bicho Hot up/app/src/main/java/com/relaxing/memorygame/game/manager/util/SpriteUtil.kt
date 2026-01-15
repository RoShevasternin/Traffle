package com.relaxing.memorygame.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.relaxing.memorygame.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          protected fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.SPLASH.data.atlas.findRegion(name)

          val BABA_LEFT       = getRegion("baba_left")
          val BABA_RIGHT      = getRegion("baba_right")
          val BULL            = getRegion("bull")
          val CROCODILE       = getRegion("crocodile")
          val GRASS           = getRegion("grass")
          val LOAD_BACKGROUND = getRegion("load_background")
          val LOAD_LOADER     = getRegion("load_loader")
          val PEACOCK         = getRegion("peacock")

          val BACGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
          val LOAD_MASK = SpriteManager.EnumTexture.LOAD_MASK.data.texture

     }

     class GameAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.GAME.data.atlas.findRegion(name)

          val ARM             = getRegion("arm")
          val ARM_PANEL       = getRegion("arm_panel")
          val BTN_MENU        = getRegion("btn_menu")
          val COIN_PANEL      = getRegion("coin_panel")
          val GAME_PANEL      = getRegion("game_panel")
          val MUSIC_DEF       = getRegion("music_def")
          val MUSIC_NO        = getRegion("music_no")
          val OFF             = getRegion("off")
          val ON              = getRegion("on")
          val PAUSE           = getRegion("pause")
          val PLAY            = getRegion("play")
          val TILE            = getRegion("tile")
          val TIME            = getRegion("time")
          val TIME_BACKGROUND = getRegion("time_background")
          val TIME_LOADER     = getRegion("time_loader")

          val ANIMALS = List(19) { getRegion("${it.inc()}") }

          val LOSE      = SpriteManager.EnumTexture.LOSE.data.texture
          val MENU      = SpriteManager.EnumTexture.MENU.data.texture
          val RULES     = SpriteManager.EnumTexture.RULES.data.texture
          val SETTINGS  = SpriteManager.EnumTexture.SETTINGS.data.texture
          val TIME_MASK = SpriteManager.EnumTexture.TIME_MASK.data.texture
          val WIN       = SpriteManager.EnumTexture.WIN.data.texture

     }

}