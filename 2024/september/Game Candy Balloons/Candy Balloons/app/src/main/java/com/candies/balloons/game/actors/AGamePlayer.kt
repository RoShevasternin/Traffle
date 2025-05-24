package com.candies.balloons.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.utils.GColor
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.advanced.AdvancedGroup
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.font.FontParameter
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AGamePlayer(
    override val screen: AdvancedScreen,
    val region: TextureRegion,
) : AdvancedGroup() {

    var steps = 0

    override fun addActorsOnGroup() {
        addAndFillActor(Image(region))
    }

}