package com.swee.ttrio.comb.game.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen

class ASelectPlayer(override val screen: AdvancedScreen, ls28: LabelStyle, num: Int): AdvancedGroup() {

    private val assets = screen.game.all

    private val imgPanel  = Image(assets.PLAYER_PANEL)
    private val imgPlayer = Image(assets.playerList.first())
    private val lblName   = Label("Player $num", ls28)

    // Field
    var regionIndex = 0
    var playerName  = "Player $num"

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgPlayer()
        addLblName()
    }

    // Actors ---------------------------------------------------

    private fun AdvancedGroup.addImgPlayer() {
        addActor(imgPlayer)
        imgPlayer.setBounds(38f,41f,171f,171f)

        val aLeft  = Actor()
        val aRight = Actor()
        addActors(aLeft, aRight)
        aLeft.apply {
            setBounds(0f,44f,67f,128f)
            setOnClickListener {
                screen.game.soundUtil.apply { play(select, 0.4f) }

                regionIndex -= 1
                if (regionIndex < 0) regionIndex = 4
                imgPlayer.drawable = TextureRegionDrawable(assets.playerList[regionIndex])
            }
        }
        aRight.apply {
            setBounds(180f,44f,67f,128f)
            setOnClickListener {
                screen.game.soundUtil.apply { play(select, 0.4f) }

                regionIndex += 1
                if (regionIndex > 4) regionIndex = 0
                imgPlayer.drawable = TextureRegionDrawable(assets.playerList[regionIndex])
            }
        }
    }

    private fun AdvancedGroup.addLblName() {
        addActor(lblName)
        lblName.setAlignment(Align.center)
        lblName.setBounds(80f, 9f, 89f, 34f)

        val aLbl = Actor()
        addActor(aLbl)
        aLbl.apply {
            setBounds(0f,0f,248f,54f)
            setOnClickListener {
                screen.game.soundUtil.apply { play(select, 0.4f) }

                Gdx.input.getTextInput(object : TextInputListener {
                    override fun input(name: String?) {
                        playerName = name?.take(10) ?: "Player"
                        lblName.setText(playerName)
                    }

                    override fun canceled() {}
                }, "Enter Name", "", "Name");
            }
        }
    }

}