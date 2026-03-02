package com.wintergroup.cupcakejump.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.wintergroup.cupcakejump.game.manager.SpriteManager

class SpriteUtil {

    class Loader {
        private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

        val loader = getRegion("loader")

        //val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
    }

    class All {
        private fun getAllRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)


        // atlas All ------------------------------------------------------------------------------

        val btns        = getAllRegion("btns")
        val md          = getAllRegion("md")
        val menu_def    = getAllRegion("menu_def")
        val menu_press  = getAllRegion("menu_press")
        val mp          = getAllRegion("mp")
        val panel       = getAllRegion("panel")
        val pause       = getAllRegion("pause")
        val platform    = getAllRegion("platform")
        val play        = getAllRegion("play")
        val sd          = getAllRegion("sd")
        val shar        = getAllRegion("shar")
        val sp          = getAllRegion("sp")
        val start_def   = getAllRegion("start_def")
        val start_press = getAllRegion("start_press")

        val listCupcake = List(4) { getAllRegion("${it.inc()}") }

        // textures ------------------------------------------------------------------------------
        val BACKGROUND = SpriteManager.EnumTexture.BACKGROUND.data.texture
        val GAME       = SpriteManager.EnumTexture.GAME.data.texture
        val RESULT     = SpriteManager.EnumTexture.RESULT.data.texture
        val RULES      = SpriteManager.EnumTexture.RULES.data.texture
        val SCORE      = SpriteManager.EnumTexture.SCORE.data.texture


    }

}