package com.princesssand.soundsprinss.game.actors.button

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.princesssand.soundsprinss.game.utils.GameColor
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen

class AButtonLVL(
    override val screen: AdvancedScreen,
    val labelStyleLvL  : LabelStyle,
    val labelStyleCount: LabelStyle,
    val lvl   : Int,
    val result: Int,
    val target: Int
): AButton(screen, Static.Type.LVL) {

    private val lvlLbl   = Label("$lvl", labelStyleLvL)
    private val countLbl = Label("$result/$target", labelStyleCount)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLvlLbl()
        addCountLbl()

        lock()
    }

    // Add Actor
    private fun addLvlLbl() {
        addActor(lvlLbl)
        lvlLbl.apply {
            setBounds(0f, 65f, 199f, 104f)
            setAlignment(Align.center)
        }
    }

    private fun addCountLbl() {
        addActor(countLbl)
        countLbl.apply {
            setBounds(0f, 4f, 199f, 43f)
            setAlignment(Align.center)
        }
    }

    // Logic

    fun lock() {
        disable()
        lvlLbl.color.set(GameColor.disable)
        countLbl.color.set(GameColor.disable)
    }

    fun unlock() {
        enable()
        lvlLbl.color.set(Color.WHITE)
        countLbl.color.set(Color.WHITE)
    }

}