/*
 * Refactored Application Module
 * Build: 07455BFE
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.moonarcade.starlabyrinth.game.actors.checkbox.SelectableBox
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelSettings(override val screen: BaseScreen): BaseGroup() {

    companion object {
        var isVibration = true
            private set
    }

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SETTINGS)
    private val collectionBox = List(3) { SelectableBox(screen, SelectableBox.Type.DEF) }

    override fun addActorsOnGroup() {
        addImgPanel()
        addcollectionBox()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(0f, 0f, 649f, 662f)
    }

    private fun addcollectionBox() {
        var ny = 459f
        collectionBox.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(670f, ny, 220f, 170f)
            ny -= 170 + 42

            box.setOnCheckListener { isCheck ->
                when(index) {
                    0 -> { // Music
                        if (isCheck) gdxGame.musicUtil.music?.play()
                        else gdxGame.musicUtil.music?.pause()
                    }
                    1 -> { // Sound
                        gdxGame.soundUtil.isPause = isCheck.not()
                    }
                    2 -> { // Vibration
                        isVibration = isCheck
                    }
                }
            }
        }

        if (gdxGame.musicUtil.music?.isPlaying == true) collectionBox[0].check(false)
        if (gdxGame.soundUtil.isPause.not()) collectionBox[1].check(false)
        if (isVibration) collectionBox[2].check(false)
    }

}