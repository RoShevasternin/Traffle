/*
 * Refactored Application Module
 * Build: 73DB838B
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.progress.AProgressLvl
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame
import com.moonarcade.starlabyrinth.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class APanelMain(override val screen: BaseScreen): BaseGroup() {

    private val amountGold = gdxGame.ds_Gold.flow.value
    private val amountGems = gdxGame.ds_Gems.flow.value
    private val amountLevel = gdxGame.ds_Level.flow.value

    private val parameter = FontConfiguration()
        .setCharacters(FontConfiguration.CharType.NUMBERS)
        .setSize(48)

    private val font48 = screen.fontGenerator_Regular.generateFont(parameter)

    private val ls48 = LabelStyle(font48, ColorScheme.white_FE)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_MAIN)
    private val lblGold = Label(amountGold.toString(), ls48)
    private val lblGems = Label(amountGems.toString(), ls48)
    private val lblLvl = Label(amountLevel.toString(), ls48)
    private val progressLvl = AProgressLvl(screen)

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
        lblGold.setBounds(107f, 145f, 121f, 45f)
        lblGems.setBounds(351f, 145f, 119f, 45f)
        lblLvl.setBounds(106f, 81f, 60f, 44f)

        lblLvl.setAlignment(Align.center)

        coroutine?.launch {
            launch {
                var oldValueGold = amountGold
                var diff = 0
                var stepSize = 0

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
                var oldValueGems = amountGems
                var diff = 0
                var stepSize = 0

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
        progressLvl.setBounds(230f, 77f, 254f, 29f)

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