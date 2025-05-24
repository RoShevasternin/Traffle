package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.screens.StartScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen

class AGamePlayer(
    override val screen: AdvancedScreen,
    playerData: StartScreen.PlayerData,
    ls36: LabelStyle,
    ls21: LabelStyle,
): AdvancedGroup() {

    private val assets = screen.game.all

    private val imgPanel  = Image(assets.PLAYER_GAME)
    private val imgPlayer = Image(assets.playerList[playerData.regionIndex])
    private val lblName   = Label(playerData.name, ls21)
    private val lblScore  = Label("0", ls36)

    val playerName   = playerData.name
    val playerRegion = assets.playerList[playerData.regionIndex]

    var score = 0
        set(value) {
            lblScore.setText(value)
            field = value
        }

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgPlayer()
        addLblName()
        addLblScore()
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addImgPlayer() {
        addActor(imgPlayer)
        imgPlayer.setBounds(30f,35f,109f,109f)
    }

    private fun AdvancedGroup.addLblName() {
        addActor(lblName)
        lblName.setAlignment(Align.center)
        lblName.setBounds(50f, 7.5f, 69f, 25f)
    }

    private fun AdvancedGroup.addLblScore() {
        addActor(lblScore)
        lblScore.setAlignment(Align.center)
        lblScore.setBounds(85f, 145f, 32f, 44f)
    }

}