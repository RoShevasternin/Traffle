package com.fishfestival.bubbleparty.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fishfestival.bubbleparty.game.utils.SizeScaler
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR
import com.fishfestival.bubbleparty.game.utils.gdxGame

class ABomb(override val screen: PARANAMA): PIDAR() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, 141f)

    private val imgBomb = Image(gdxGame.assetsAll.BOMB)

    override fun addActorsOnGroup() {
        addActor(imgBomb)
        imgBomb.setBoundsScaled(2f, 0f, 136f, 175f)
    }

}