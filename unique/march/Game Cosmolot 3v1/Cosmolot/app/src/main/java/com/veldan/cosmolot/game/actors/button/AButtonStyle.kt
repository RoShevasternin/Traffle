package com.veldan.cosmolot.game.actors.button

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.veldan.cosmolot.game.manager.SpriteManager
import com.veldan.cosmolot.game.util.TextureEmpty

data class AButtonStyle(
    val default : TextureRegion,
    val pressed : TextureRegion,
    val disabled: TextureRegion? = null,
) {
    
    object Game_1{
        val menu get() = AButtonStyle(
            default = SpriteManager.GameRegion_1.MENU_DEF.region,
            pressed = SpriteManager.GameRegion_1.MENU_PRESS.region,
        )
        val betMax get() = AButtonStyle(
            default = SpriteManager.GameRegion_1.MAX_BET_DEF.region,
            pressed = SpriteManager.GameRegion_1.MAX_BET_PRESS.region,
        )
        val spin get() = AButtonStyle(
            default = SpriteManager.GameRegion_1.SPIN_DEF.region,
            pressed = SpriteManager.GameRegion_1.SPIN_PRESS.region,
        )
        val music get() = AButtonStyle(
            default = SpriteManager.GameRegion_1.MUSIC_YES.region,
            pressed = SpriteManager.GameRegion_1.MUSIC_NO.region,
        )
    }

    object Game_2 {
        val menu get() = AButtonStyle(
            default = SpriteManager.GameRegion_2.MENU_DEF.region,
            pressed = SpriteManager.GameRegion_2.MENU_PRESS.region,
        )
        val music get() = AButtonStyle(
            default = SpriteManager.GameRegion_2.MUSIC_YES.region,
            pressed = SpriteManager.GameRegion_2.MUSIC_NO.region,
        )
        val spin get() = AButtonStyle(
            default = SpriteManager.GameRegion_2.SPIN_DEF.region,
            pressed = SpriteManager.GameRegion_2.SPIN_PRESS.region,
        )
        val auto get() = AButtonStyle(
            default  = SpriteManager.GameRegion_2.AUTO_DEF.region,
            pressed  = SpriteManager.GameRegion_2.AUTO_PRESS.region,
            disabled = SpriteManager.GameRegion_2.AUTO_DIS.region,
        )
    }

    object Game_3 {
        val menu get() = AButtonStyle(
            default = SpriteManager.GameRegion_3.MENU_DEF.region,
            pressed = SpriteManager.GameRegion_3.MENU_PRESS.region,
        )
        val music get() = AButtonStyle(
            default = SpriteManager.GameRegion_3.MUSIC_YES.region,
            pressed = SpriteManager.GameRegion_3.MUSIC_NO.region,
        )
        val spin get() = AButtonStyle(
            default = TextureEmpty,
            pressed = SpriteManager.GameRegion_3.SPIN_PRESS.region,
        )
        val auto get() = AButtonStyle(
            default  = TextureEmpty,
            pressed  = SpriteManager.GameRegion_3.AUTO_PRESS.region,
            disabled = SpriteManager.GameRegion_3.AUTO_DIS.region,
        )
    }
}