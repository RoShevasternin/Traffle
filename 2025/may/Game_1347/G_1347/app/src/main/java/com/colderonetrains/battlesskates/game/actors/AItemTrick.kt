package com.colderonetrains.battlesskates.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBox
import com.colderonetrains.battlesskates.game.actors.checkbox.ACheckBoxGroup
import com.colderonetrains.battlesskates.game.actors.main.AMainTrickDescription
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.Acts
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedGroup
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedScreen
import com.colderonetrains.battlesskates.game.utils.gdxGame
import com.colderonetrains.battlesskates.util.log

class AItemTrick(
    override val screen: AdvancedScreen,
    val iconTexture: Texture,
    val dataTrick: DataTrick,
    val lsBold46 : Label.LabelStyle,
    val lsLight36: Label.LabelStyle,
    val levelType: LevelType,
): AdvancedGroup() {

    override fun getPrefWidth(): Float { return width }
    override fun getPrefHeight(): Float { return height }

    private val imgPanel = Image(gdxGame.assetsAll.ITEM)
    private val btnLearn = AButton(screen, AButton.Type.Learn)
    private val imgIcon  = Image(iconTexture)

    private val lblTitle       = Label(dataTrick.nName, lsBold46)
    private val lblDescription = Label(shortenText(dataTrick.description), lsLight36)

    private val imgIconCheck = Image(gdxGame.assetsAll.DO_IT)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgIcon()
        addLblTitle()
        addLblDescription()
        addBtnLearn()
        addImgCheck()
    }

    private fun addImgIcon() {
        addActor(imgIcon)
        imgIcon.setBounds(45f, 39f, 208f, 208f)
    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(312f, 191f, 191f, 53f)
    }

    private fun addLblDescription() {
        addActor(lblDescription)
        lblDescription.setBounds(312f, 45f, 547f, 123f)
        lblDescription.wrap = true
        lblDescription.setAlignment(Align.topLeft)
    }

    private fun addBtnLearn() {
        addActor(btnLearn)
        btnLearn.setBounds(699f, 28f, 180f, 55f)
        btnLearn.setOnClickListener {
            AMainTrickDescription.iconTexture = this.iconTexture
            AMainTrickDescription.dataTrick   = this.dataTrick
            AMainTrickDescription.levelType   = this.levelType

            screen.hideScreen {
                gdxGame.navigationManager.navigate(TrickDescriptionScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addImgCheck() {
        addActor(imgIconCheck)
        imgIconCheck.setBounds((lblTitle.x + lblTitle.prefWidth + 40), 193f, 48f, 48f)
        if (gdxGame.ds_DataDidItTrick.flow.value.firstOrNull { it.nameTrick == dataTrick.nName } == null) imgIconCheck.color.a = 0f
    }

    // Logic --------------------------------------------------------------

    private fun shortenText(text: String): String {
        return if (text.length <= 75) {
            text
        } else {
            text.take(75).trimEnd() + "..."
        }
    }

}