/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors.panel

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.label.TextLabel
import com.novaburst.pixelrally.game.data.DataLocation
import com.novaburst.pixelrally.game.utils.ColorPalette
import com.novaburst.pixelrally.game.utils.actor.setBounds
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.font.TypefaceConfig
import com.novaburst.pixelrally.game.utils.gdxGame

class APanelLocation(override val screen: DisplayScreen): ComponentGroup() {

    private val parameter110 = TypefaceConfig().setCharacters(TypefaceConfig.CharType.NUMBERS.chars + "%").setSize(110)
    private val parameter70 = TypefaceConfig().setCharacters(TypefaceConfig.CharType.ALL).setSize(70)

    private val font110 = screen.fontGenerator_Bold.generateFont(parameter110)
    private val font70  = screen.fontGenerator_Regular.generateFont(parameter70)

    private val ls110 = LabelStyle(font110, ColorPalette.black_26)
    private val ls70  = LabelStyle(font70, ColorPalette.black_26)

    private val imgPanel     = Image(gdxGame.assetsAll.PANEL_LOCATION)
    private val lblName      = TextLabel(screen, "", ls70)
    private val lblPercent   = Label("3%", ls110)
    private val imgPersonage = Image()

    // Handler method
    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblName()
        addLblPercent()
        addImgPersonage()
    }

    // Actors ------------------------------------------------------------------------

    // Handler method
    private fun addLblName() {
        addActor(lblName)
        lblName.setBounds(367f, 460f, 483f, 65f)
        lblName.label.setAlignment(Align.center)
        lblName.setOrigin(Align.center)
        //lblName.rotation = -3f
    }

    private fun addLblPercent() {
        addActor(lblPercent)
        lblPercent.setBounds(423f, 203f, 132f, 93f)
        lblPercent.setAlignment(Align.center)
    }

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

    // Processing logic
    fun update(dataLocation: DataLocation) {
        lblName.label.setText(dataLocation.nName)

        updatePercent(gdxGame.ds_LevelJeckpot.flow.value[dataLocation.index])

        imgPersonage.apply {
            drawable = TextureRegionDrawable(gdxGame.assetsAll.listPersonage[dataLocation.index])
            setBounds(dataLocation.persPosSize)
        }
    }

    // Processing logic
    fun updatePercent(levelJackpot: Int) {
        val percent = (levelJackpot + 3)
        lblPercent.setText("$percent%")
    }

}