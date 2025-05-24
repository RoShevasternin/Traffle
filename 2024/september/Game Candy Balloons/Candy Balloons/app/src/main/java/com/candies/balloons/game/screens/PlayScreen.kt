package com.candies.balloons.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.ASelectPlayer
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.font.FontParameter
import com.candies.balloons.game.utils.region
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.launch

class PlayScreen(override val game: LibGDXGame) : AdvancedScreen() {

    data class SelectedPlayer(val num: Int, val indexReg: Int)

    companion object {
        var selectedList = mutableListOf<SelectedPlayer>()
    }

    private val fontParameter = FontParameter()
    private val font311       = fontGenerator_BALOO_CYRILLIC.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(311))

    private val ls311 = Label.LabelStyle(font311, Color.BLACK)

    private val btnMenu  = AButton(this, AButton.Static.Type.Dom)
    private val btnPlay  = AButton(this, AButton.Static.Type.Play)
    private val imgPanel = Image(game.all.num_of_player)
    private val imgList  = List(4) { ASelectPlayer(this, it.inc()) }

    private var counter    = 1
    private val lblCounter = Label(counter.toString(), ls311)

    override fun show() {
        selectedList.clear()
        stageUI.root.animHide()
        setBackBackground(game.splash.DARK.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgPanel()
                addPlay()
                addPlayers()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(880f, 1724f, 157f, 154f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(280f, 1230f, 520f, 548f)

        addActor(lblCounter)
        lblCounter.apply {
            setBounds(460f, 1304f, 161f, 279f)
            setAlignment(Align.center)
        }

        val left  = Actor()
        val right = Actor()
        addActors(left, right)
        left.apply {
            setBounds(298f, 1272f, 163f, 365f)
            setOnClickListener(game.soundUtil) {
                if (counter > 1) {
                    counter--
                    lblCounter.setText(counter)
                    changePlayers()
                }
            }
        }
        right.apply {
            setBounds(619f, 1272f, 163f, 365f)
            setOnClickListener(game.soundUtil) {
                if (counter < 4) {
                    counter++
                    lblCounter.setText(counter)
                    changePlayers()
                }
            }
        }
    }

    private fun AdvancedStage.addPlay() {
        addActor(btnPlay)
        btnPlay.apply {
            setBounds(307f, 106f, 492f, 231f)
            setOnClickListener {
                imgList.filter { it.isSelect }.onEach {
                    selectedList.add(SelectedPlayer(it.count, it.indexReg))
                }

                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(GameScreen::class.java.name) }
                }
            }
        }
    }

    private fun AdvancedStage.addPlayers() {
        val pos = listOf(
            Vector2(163f, 797f),
            Vector2(569f, 797f),
            Vector2(163f, 372f),
            Vector2(569f, 372f),
        )
        imgList.onEachIndexed { index, aSelectPlayer ->
            if (index != 0) aSelectPlayer.color.a = 0f
            if (index == 0) aSelectPlayer.isSelect = true

            addActor(aSelectPlayer)
            aSelectPlayer.setBounds(pos[index].x, pos[index].y, 348f, 366f)
        }
    }

    // Logic ----------------------------------------------------------------------------------------

    private fun changePlayers() {
        imgList.onEachIndexed { index, img ->
            if (index.inc() <= counter) {
                img.animShow(0.15f)
                img.isSelect = true
            } else {
                img.animHide(0.15f)
                img.isSelect = false
            }
        }
    }

}