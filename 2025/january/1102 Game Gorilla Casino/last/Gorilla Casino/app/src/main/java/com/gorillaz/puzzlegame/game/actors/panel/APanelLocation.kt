package com.gorillaz.puzzlegame.game.actors.panel

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.label.ALabel
import com.gorillaz.puzzlegame.game.data.DataLocation
import com.gorillaz.puzzlegame.game.dataStore.DS_LevelJackpot
import com.gorillaz.puzzlegame.game.utils.Acts
import com.gorillaz.puzzlegame.game.utils.GameColor
import com.gorillaz.puzzlegame.game.utils.actor.disable
import com.gorillaz.puzzlegame.game.utils.actor.setBounds
import com.gorillaz.puzzlegame.game.utils.actor.setOrigin
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.font.FontParameter
import com.gorillaz.puzzlegame.game.utils.gdxGame

class APanelLocation(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter110 = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "%").setSize(110)
    private val parameter70  = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(70)

    private val font110 = screen.fontGenerator_Bold.generateFont(parameter110)
    private val font70  = screen.fontGenerator_Regular.generateFont(parameter70)

    private val ls110 = LabelStyle(font110, GameColor.black_09)
    private val ls70  = LabelStyle(font70, GameColor.black_09)

    private val imgPanel     = Image(gdxGame.assetsAll.PANEL_LOCATION)
    private val lblName      = ALabel(screen, "", ls70)
    private val lblPercent   = Label("3%", ls110)
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
        lblName.setBounds(537f, 760f, 274f, 71f)
        lblName.label.setAlignment(Align.center)
        lblName.setOrigin(Align.center)
        lblName.rotation = -3f
    }

    private fun addLblPercent() {
        addActor(lblPercent)
        lblPercent.setBounds(559f, 421f, 132f, 93f)
        lblPercent.setAlignment(Align.center)
    }

    private fun addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.05f, -0.05f, 0.5f, Interpolation.sineIn),
                Actions.scaleTo(1f, 1f, 0.5f, Interpolation.sineOut),
            )
        ))
    }

    // Logic --------------------------------------------------------------------------

    fun update(dataLocation: DataLocation) {
        lblName.label.setText(dataLocation.nName)

        updatePercent(gdxGame.ds_LevelJeckpot.flow.value[dataLocation.index])

        imgPersonage.apply {
            drawable = TextureRegionDrawable(gdxGame.assetsAll.listPersonage[dataLocation.index])
            setBounds(dataLocation.persPosSize)
        }
    }

    fun updatePercent(levelJackpot: Int) {
        val percent = (levelJackpot + 3)
        lblPercent.setText("$percent%")
    }

}