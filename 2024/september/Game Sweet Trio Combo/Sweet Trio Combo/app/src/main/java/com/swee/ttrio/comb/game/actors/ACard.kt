package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen

class ACard(
    override val screen: AdvancedScreen,
    var type: Static.Type,
) : AdvancedGroup() {

    var randomIndex = (0..9).random()
    var region      = screen.game.all.listCards[type.ordinal][randomIndex]

    val img = Image(region)

    override fun addActorsOnGroup() {
        setOrigin(Align.center)
        addAndFillActor(img)
    }

    object Static {
        enum class Type {
            _1, _2, _3, _4, _5
        }
    }

}