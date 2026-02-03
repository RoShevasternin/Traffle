package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.progress.AProgressLvl
import com.gorillaz.puzzlegame.game.screens.GameScreen
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame
import com.gorillaz.puzzlegame.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class APanelMain(override val screen: AdvancedScreen): AdvancedGroup() {

    private val valueGold  = gdxGame.ds_Gold.flow.value
    private val valueGems  = gdxGame.ds_Gems.flow.value
    private val valueLevel = gdxGame.ds_Level.flow.value

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.NUMBERS)
        .setSize(48)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls48 = LabelStyle(font48, GameColor.white_FE)

    private val imgPanel     = Image(gdxGame.assetsAll.PANEL_MAIN)
    private val lblGold      = Label(valueGold.toString(), ls48)
    private val lblGems      = Label(valueGems.toString(), ls48)
    private val lblLvl       = Label(valueLevel.toString(), ls48)
    private val progressLvl  = AProgressLvl(screen)

    // Field
    var isCollectProgress = false

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
        addProgressLvl()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblGold, lblGems, lblLvl)
        lblGold.setBounds(216f, 204f, 121f, 45f)
        lblGems.setBounds(460f, 204f, 119f, 45f)
        lblLvl.setBounds(134f, 123f, 60f, 44f)

        lblLvl.setAlignment(Align.center)

        coroutine?.launch {
            launch {
                var oldValueGold = valueGold
                var diff         = 0
                var stepSize     = 0

                gdxGame.ds_Gold.flow.collect { gold ->
                    diff = (gold - oldValueGold).absoluteValue

                    stepSize = when  {
                        diff in 900..5000    -> 10
                        diff in 5001..10_000 -> 50
                        diff > 10_000               -> 100
                        else -> 1
                    }

                    if (gold > oldValueGold) {
                        while (gold > oldValueGold) {
                            oldValueGold += stepSize
                            runGDX { lblGold.setText(oldValueGold) }
                            delay(5)
                        }
                    } else {
                        while (oldValueGold > gold) {
                            oldValueGold -= stepSize
                            runGDX { lblGold.setText(oldValueGold) }
                            delay(5)
                        }
                    }

                    runGDX { lblGold.setText(gold) }
                }
            }
            launch {
                var oldValueGems = valueGems
                var diff         = 0
                var stepSize     = 0

                gdxGame.ds_Gems.flow.collect { gems ->
                    diff = (gems - oldValueGems).absoluteValue

                    stepSize = when  {
                        diff in 900..5000    -> 10
                        diff in 5001..10_000 -> 50
                        diff > 10_000               -> 100
                        else -> 1
                    }

                    if (gems > oldValueGems) {
                        while (gems > oldValueGems) {
                            oldValueGems += stepSize
                            runGDX { lblGems.setText(oldValueGems) }
                            delay(5)
                        }
                    } else {
                        while (oldValueGems > gems) {
                            oldValueGems -= stepSize
                            runGDX { lblGems.setText(oldValueGems) }
                            delay(5)
                        }
                    }

                    runGDX { lblGems.setText(gems) }
                }
            }
        }
    }

    private fun addProgressLvl() {
        addActors(progressLvl)
        progressLvl.setBounds(228f, 130f, 361f, 23f)

        coroutine?.launch {
            gdxGame.ds_Level.flow.collect { level ->
                if (isCollectProgress) runGDX {
                    progressLvl.animNewLevel {
                        lblLvl.setText(level)
                    }
                }
            }
        }
    }

}