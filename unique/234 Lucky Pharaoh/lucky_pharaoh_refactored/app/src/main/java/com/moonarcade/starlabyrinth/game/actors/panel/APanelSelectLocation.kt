/*
 * Refactored Application Module
 * Build: 284EB232
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.moonarcade.starlabyrinth.game.actors.button.ClickableElement
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.data.LocationData
import com.moonarcade.starlabyrinth.game.utils.GLOBAL_listDataLocation
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelSelectLocation(override val screen: BaseScreen): BaseGroup() {

    private val textBtnPlay = "PLAY"

    private val parameter82 = FontConfiguration().setCharacters(textBtnPlay).setSize(82)

    private val font82 = screen.fontGenerator_Regular.generateFont(parameter82)

    private val ls82 = Label.LabelStyle(font82, ColorScheme.black_09)

    var presentLocationIndex = 0
        private set

    var presentDataLocation = GLOBAL_listDataLocation[presentLocationIndex]
        private set

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SELECT_LOCATION)
    private val btnPlay = ClickableElement(screen, ClickableElement.Type.Play)
    private val btnLeft = GraphicButton(screen, gdxGame.assetsAll.left)
    private val btnRight = GraphicButton(screen, gdxGame.assetsAll.right)

    var blockPlay: () -> Unit = {}
    var blockNext: (LocationData) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addBtnPlay()
        addBtnLeftRight()

        blockNext(presentDataLocation)
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnPlay() {
        addActor(btnPlay)
        btnPlay.setBounds(215f, 128f, 282f, 196f)
        btnPlay.setOnClickListener {
            btnPlay.disable()
            btnLeft.disable()
            btnRight.disable()
            
            blockPlay()
        }
    }

    // Internal processing
    private fun addBtnLeftRight() {
        addActors(btnLeft, btnRight)
        btnLeft.apply {
            setBounds(59f, 175f, 139f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(513f, 175f, 139f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    // Logic --------------------------------------------------------------------------

    // Primary method handler
    private fun handlerLeft() {
        if (presentLocationIndex - 1 >= 0) {
            presentLocationIndex -= 1
        } else {
            presentLocationIndex = GLOBAL_listDataLocation.lastIndex
        }

        nextLocation()
    }

    private fun handlerRight() {
        if (presentLocationIndex + 1 <= GLOBAL_listDataLocation.lastIndex) {
            presentLocationIndex += 1
        } else {
            presentLocationIndex = 0
        }

        nextLocation()
    }

    private fun nextLocation() {
        presentDataLocation = GLOBAL_listDataLocation[presentLocationIndex]
        blockNext(presentDataLocation)
    }

    fun disableBtnPlay() {
        btnPlay.disable()
    }
    fun enableBtnPlay() {
        btnPlay.enable()
    }

}