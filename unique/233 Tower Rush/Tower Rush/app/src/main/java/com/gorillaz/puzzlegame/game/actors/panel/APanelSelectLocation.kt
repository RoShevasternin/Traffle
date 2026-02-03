package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.gorillaz.puzzlegame.game.actors.button.AImageButton
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.data.DataLocation
import com.gorillaz.puzzlegame.game.utils.GLOBAL_listDataAvatar
import com.gorillaz.puzzlegame.game.utils.GLOBAL_listDataLocation
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

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
    private val btnPlay  = ATextButton(screen, textBtnPlay, ls82)
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
        btnPlay.setBounds(215f, 64f, 282f, 196f)
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
            setBounds(59f, 111f, 139f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(513f, 111f, 139f, 102f)
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