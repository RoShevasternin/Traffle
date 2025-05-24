package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.utils.actor.PosSize
import com.colderonetrains.battlesskates.game.utils.actor.setBounds
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AFailed(override val screen: AdvancedScreen) : AdvancedGroup() {

    private val listType = listOf(
        ACheckBox.Type.SKATE_1,
        ACheckBox.Type.SKATE_2,
        ACheckBox.Type.SKATE_3,
        ACheckBox.Type.SKATE_4,
        ACheckBox.Type.SKATE_5,
    )

    private val imgIcon = Image(gdxGame.assetsAll.FAILED)
    private val btnNext = AButton(screen, AButton.Type.NextPlayer)

    private val listBoxSKATE = List(5) { ACheckBox(screen, listType[it]) }

    var blockNextPlayer = {}

    override fun addActorsOnGroup() {
        addActors(imgIcon, btnNext)
        imgIcon.setBounds(41f, 1076f, 998f, 413f)
        btnNext.setBounds(332f, 607f, 416f, 102f)

        btnNext.setOnClickListener { blockNextPlayer() }

        val listPosSize = listOf(
            PosSize(186f, 884f, 126f, 115f),
            PosSize(330f, 883f, 116f, 117f),
            PosSize(454f, 881f, 155f, 140f),
            PosSize(613f, 883f, 125f, 122f),
            PosSize(782f, 884f, 103f, 115f),
        )
        listBoxSKATE.forEachIndexed { index, box ->
            addActor(box)
            box.disable()
            box.setBounds(listPosSize[index])
        }
    }

    fun show_S_K_A_T_E(count: Int) {
        listBoxSKATE.forEach { it.uncheck() }
        listBoxSKATE.take(count).forEach { it.check() }
    }

}