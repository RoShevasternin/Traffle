package com.candies.balloons.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.checkbox.ACheckBox
import com.candies.balloons.game.utils.*
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.font.FontParameter
import kotlinx.coroutines.launch

class WonScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val players = listOf(
        game.all._1,
        game.all._2,
        game.all._3,
        game.all._4,
    )

    private val fontParameter = FontParameter()
    private val font61        = fontGenerator_BALOO_CYRILLIC.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(61))
    private val font133       = fontGenerator_BARLOW_BLACK.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(133))

    private val ls61  = Label.LabelStyle(font61, GColor.blue)
    private val ls133 = Label.LabelStyle(font133, Color.WHITE)

    private val btnMenu      = AButton(this, AButton.Static.Type.Dom)
    private val btnRestart   = AButton(this, AButton.Static.Type.Restart)
    private val imgTitle     = Image(game.all.won)
    private val imgWinPlayer = Image(players[GameScreen.WIN_INDEX_REG])

    private val lblPlayer = Label("Player ${GameScreen.WIN_INDEX_PLAYER.inc()}", ls61)
    private val lblCount  = Label("${GameScreen.WIN_BALL_COUNT}", ls133)


    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.DARK.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            game.soundUtil.apply { play(won, 0.7f) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgTitle()
                addMenu()
                addRestart()
                addPlayer()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(201f, 208f, 225f, 221f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addRestart() {
        addActor(btnRestart)
        btnRestart.apply {
            setBounds(654f, 214f, 225f, 215f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(GameScreen::class.java.name) }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(84f, 288f, 913f, 1476f)
    }

    private fun AdvancedStage.addPlayer() {
        addActor(imgWinPlayer)
        imgWinPlayer.setBounds(364f, 739f, 351f, 390f)
        addActors(lblPlayer, lblCount)
        lblPlayer.apply {
            setAlignment(Align.center)
            setBounds(450f, 1276f, 216f, 113f)
        }
        lblCount.apply {
            setAlignment(Align.center)
            setBounds(587f, 1560f, 151f, 245f)
        }
    }

}