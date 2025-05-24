package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.GameColor
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.font.FontParameter
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.launch

class APanelItemGame(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(50)

    private val font50 = screen.fontGenerator_Karantina.generateFont(parameter)

    private val ls50 = LabelStyle(font50, GameColor.da)

    private val listLbl = List(3) { Label("", ls50) }

    var blockK2 = {}
    var blockK5 = {}
    var blockK1 = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.assetsAll.game_items))
        addLbls()
        addBtns()
    }

    private fun addBtns() {
        var nx      = 147f
        val listBtn = List(3) { AButton(screen, AButton.Type.Use) }
        listBtn.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx, 14f, 181f, 116f)
            nx += (187 + 181)

            actor.setOnClickListener {
                actor.disable()
                when(index) {
                    0 -> {
                        screen.game.dataStoreK2Util.update { item ->
                            if (item > 0) {
                                screen.game.soundUtil.apply { play(use_bonus) }
                                blockK2()
                                item - 1
                            } else {
                                screen.game.soundUtil.apply { play(fail_use_bonus) }
                                item
                            }
                        }
                    }
                    1 -> {
                        screen.game.dataStoreK5Util.update { item ->
                            if (item > 0) {
                                screen.game.soundUtil.apply { play(use_bonus) }
                                blockK5()
                                item - 1
                            } else {
                                screen.game.soundUtil.apply { play(fail_use_bonus) }
                                item
                            }
                        }
                    }
                    2 -> {
                        screen.game.dataStoreK1Util.update { item ->
                            if (item > 0) {
                                screen.game.soundUtil.apply { play(use_bonus) }
                                blockK1()
                                item - 1
                            } else {
                                screen.game.soundUtil.apply { play(fail_use_bonus) }
                                item
                            }
                        }
                    }
                }

                actor.enable()
            }

        }
    }

    private fun addLbls() {
        var nx = 228f

        listLbl.onEachIndexed { index, lbl ->
            addActor(lbl)
            lbl.setBounds(nx, 115f, 14f, 51f)
            nx += (355 + 14)

            coroutine?.launch {
                when(index) {
                    0 -> { screen.game.dataStoreK2Util.k2Flow.collect { runGDX { lbl.setText(it) } } }
                    1 -> { screen.game.dataStoreK5Util.k5Flow.collect { runGDX { lbl.setText(it) } } }
                    2 -> { screen.game.dataStoreK1Util.k1Flow.collect { runGDX { lbl.setText(it) } } }
                }

            }
        }
    }

}