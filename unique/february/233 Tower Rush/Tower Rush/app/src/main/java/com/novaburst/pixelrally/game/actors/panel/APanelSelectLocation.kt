package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.novaburst.pixelrally.game.actors.button.AButton
import com.novaburst.pixelrally.game.actors.button.AImageButton
import com.novaburst.pixelrally.game.actors.button.ATextButton
import com.novaburst.pixelrally.game.data.DataLocation
import com.novaburst.pixelrally.game.utils.GLOBAL_listDataLocation
import com.novaburst.pixelrally.game.utils.GameColor
import com.novaburst.pixelrally.game.utils.advanced.AdvancedGroup
import com.novaburst.pixelrally.game.utils.advanced.AdvancedScreen
import com.novaburst.pixelrally.game.utils.font.FontParameter
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelSelectLocation(override val screen: AdvancedScreen): AdvancedGroup() {

    private val textBtnPlay = "PLAY"

    private val parameter82 = FontParameter().setCharacters(textBtnPlay).setSize(82)

    private val font82 = screen.fontGenerator_Regular.generateFont(parameter82)

    private val ls82 = Label.LabelStyle(font82, GameColor.black_09)

    var currentLocationIndex = 0
        private set

    var currentDataLocation = GLOBAL_listDataLocation[currentLocationIndex]
        private set

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_SELECT_LOCATION)
    private val btnPlay  = AButton(screen, AButton.Type.Play)
    private val btnLeft  = AImageButton(screen, gdxGame.assetsAll.left)
    private val btnRight = AImageButton(screen, gdxGame.assetsAll.right)

    var blockPlay: () -> Unit = {}
    var blockNext: (DataLocation) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addBtnPlay()
        addBtnLeftRight()

        blockNext(currentDataLocation)
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnPlay() {
        addActor(btnPlay)
        btnPlay.setBounds(257f, 198f, 263f, 196f)
        btnPlay.setOnClickListener {
            btnPlay.disable()
            btnLeft.disable()
            btnRight.disable()
            
            blockPlay()
        }
    }

    private fun addBtnLeftRight() {
        addActors(btnLeft, btnRight)
        btnLeft.apply {
            setBounds(92f, 245f, 139f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(546f, 245f, 139f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    // Logic --------------------------------------------------------------------------

    private fun handlerLeft() {
        if (currentLocationIndex - 1 >= 0) {
            currentLocationIndex -= 1
        } else {
            currentLocationIndex = GLOBAL_listDataLocation.lastIndex
        }

        nextLocation()
    }

    private fun handlerRight() {
        if (currentLocationIndex + 1 <= GLOBAL_listDataLocation.lastIndex) {
            currentLocationIndex += 1
        } else {
            currentLocationIndex = 0
        }

        nextLocation()
    }

    private fun nextLocation() {
        currentDataLocation = GLOBAL_listDataLocation[currentLocationIndex]
        blockNext(currentDataLocation)
    }

    fun disableBtnPlay() {
        btnPlay.disable()
    }
    fun enableBtnPlay() {
        btnPlay.enable()
    }

}