package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.actor.disable
import com.colderonetrains.battlesskates.game.utils.actor.enable
import com.colderonetrains.battlesskates.game.utils.actor.setOnClickListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame

class ARandom_Description(
    override val screen: AdvancedScreen,
    val iconTexture: Texture,
    val dataTrick  : DataTrick,
    val levelType  : LevelType,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font33        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(33))
    private val font36        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(36))
    private val font46        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(46))

    private val ls33 = Label.LabelStyle(font33, GameColor.white_F4)
    private val ls36 = Label.LabelStyle(font36, GameColor.white_D9)
    private val ls46 = Label.LabelStyle(font46, Color.WHITE)

    private val imgPanel = Image(gdxGame.assetsAll.RANDOM_PANEL)

    private val lblTitle       = Label(dataTrick.nName, ls46)
    private val lblDescription = Label(dataTrick.description, ls36)

    private val imgIcon = Image(iconTexture)

    private val imgPanelStep = Image(gdxGame.assetsAll.STEP3)
    private val imgTint      = Image(gdxGame.assetsAll.TINT)
    private val listLblStep  = List(3) { Label(dataTrick.listStep[it], ls33) }

    private val btnLearn = AButton(screen, AButton.Type.Learn)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgIcon()
        addImgPanelStep()
        addLbls()
        addImgTint()
        addBtnLearn()
    }

    private fun addImgIcon() {
        addActor(imgIcon)
        imgIcon.setBounds(32f, 623f, 342f, 342f)
    }

    private fun addImgPanelStep() {
        addActor(imgPanelStep)
        imgPanelStep.setBounds(32f, 117f, 168f, 317f)
    }

    private fun addLbls() {
        addActors(lblTitle, lblDescription)
        lblTitle.setBounds(418f, 912f, 103f, 53f)
        lblDescription.setBounds(418f, 623f, 429f, 246f)
        lblDescription.wrap = true
        lblDescription.setAlignment(Align.topLeft)

        var ny = 338f
        listLblStep.forEach { lbl ->
            addActor(lbl)
            lbl.setBounds(32f, ny, 852f, 38f)
            lbl.wrap = true

            ny -= 100 + 38
        }
    }

    private fun addImgTint() {
        addActor(imgTint)
        imgTint.setBounds(0f, 0f, 908f, 238f)
    }

    private fun addBtnLearn() {
        addActor(btnLearn)
        btnLearn.setBounds(634f, 34f, 234f, 71f)
        btnLearn.setOnClickListener {
            AMainTrickDescription.iconTexture = this.iconTexture
            AMainTrickDescription.dataTrick   = this.dataTrick
            AMainTrickDescription.levelType   = this.levelType

            screen.hideScreen {
                gdxGame.navigationManager.navigate(TrickDescriptionScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}