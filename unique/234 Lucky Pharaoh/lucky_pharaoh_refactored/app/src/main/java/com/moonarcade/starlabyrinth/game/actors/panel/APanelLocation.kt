/*
 * Refactored Application Module
 * Build: 3CD72E69
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.label.TextDisplay
import com.moonarcade.starlabyrinth.game.data.LocationData
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.actor.setBounds
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelLocation(override val screen: BaseScreen): BaseGroup() {

    private val parameter110 = FontConfiguration().setCharacters(FontConfiguration.CharType.NUMBERS.chars + "%").setSize(110)
    private val parameter70 = FontConfiguration().setCharacters(FontConfiguration.CharType.ALL).setSize(70)

    private val font110 = screen.fontGenerator_Bold.generateFont(parameter110)
    private val font70 = screen.fontGenerator_Regular.generateFont(parameter70)

    private val ls110 = LabelStyle(font110, ColorScheme.black_26)
    private val ls70 = LabelStyle(font70, ColorScheme.black_26)

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_LOCATION)
    private val lblName = TextDisplay(screen, "", ls70)
    private val lblPercent = Label("3%", ls110)
    private val imgPersonage = Image()

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblName()
        addLblPercent()
        addImgPersonage()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblName() {
        addActor(lblName)
        lblName.setBounds(661f, 563f, 352f, 65f)
        lblName.label.setAlignment(Align.center)
        lblName.setOrigin(Align.center)
        //lblName.rotation = -3f
    }

    // System operation
    private fun addLblPercent() {
        addActor(lblPercent)
        lblPercent.setBounds(561f, 237f, 132f, 93f)
        lblPercent.setAlignment(Align.center)
    }

    // System operation
    private fun addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.03f, -0.03f, 0.5f, Interpolation.sineIn),
                Actions.scaleTo(1f, 1f, 0.5f, Interpolation.sineOut),
            )
        ))
    }

    // Logic --------------------------------------------------------------------------

    fun update(dataLocation: LocationData) {
        lblName.label.setText(dataLocation.nName)

        updatePercent(gdxGame.ds_LevelJeckpot.flow.value[dataLocation.index])

        imgPersonage.apply {
            drawable = TextureRegionDrawable(gdxGame.assetsAll.collectionPersonage[dataLocation.index])
            setBounds(dataLocation.persPosSize)
        }
    }

    fun updatePercent(levelJackpot: Int) {
        val percent = (levelJackpot + 3)
        lblPercent.setText("$percent%")
    }

}