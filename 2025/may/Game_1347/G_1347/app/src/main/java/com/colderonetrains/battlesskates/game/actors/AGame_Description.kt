package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.actor.setOnClickListener
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AGame_Description(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font46        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(46))
    private val font51        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(51))
    private val font39        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(39))
    private val font36        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(36))

    private val ls46 = Label.LabelStyle(font46, Color.WHITE)
    private val ls51 = Label.LabelStyle(font51, Color.WHITE)
    private val ls39 = Label.LabelStyle(font39, GameColor.white_D9)
    private val ls36 = Label.LabelStyle(font36, GameColor.white_F4)

    private val imgPanel = Image(gdxGame.assetsAll.YOUR_TURN)

    private val lblPlayerName  = Label("", ls46)
    private val lblTitle       = Label("", ls51)
    private val lblDescription = Label("", ls39)
    private val listLblStep    = List(5) { Label("", ls36) }

    private val imgIcon = Image()

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgIcon()
        addLbls()
    }

    private fun addImgIcon() {
        addActor(imgIcon)
        imgIcon.setBounds(30f, 747f, 375f, 375f)
    }

    private fun addLbls() {
        addActors(lblPlayerName, lblTitle, lblDescription)
        lblPlayerName.setBounds(523f, 1271f, 348f, 53f)
        lblTitle.setBounds(454f, 1064f, 113f, 59f)
        lblDescription.setBounds(454f, 833f, 470f, 184f)

        lblPlayerName.setAlignment(Align.center)
        lblDescription.wrap = true
        lblDescription.setAlignment(Align.topLeft)

        var ny = 567f
        listLblStep.forEach { lbl ->
            addActor(lbl)
            lbl.setBounds(30f, ny, 935f, 41f)
            lbl.wrap = true

            ny -= 86 + 41
        }
    }

    // Logic --------------------------------------------------------

    fun update(
        playerName: String,
        dataTrick : DataTrick,
        iconRegion: Texture,
    ) {
        imgIcon.drawable = TextureRegionDrawable(iconRegion)
        lblPlayerName.setText(playerName)
        lblTitle.setText(dataTrick.nName)
        lblDescription.setText(dataTrick.description)

        listLblStep.forEachIndexed { index, lbl ->
            lbl.setText(dataTrick.listStep[index])
        }
    }



}