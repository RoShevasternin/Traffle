package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.actors.AButton
import com.swee.ttrio.comb.game.actors.ASelectPlayer
import com.swee.ttrio.comb.game.actors.TmpGroup
import com.swee.ttrio.comb.game.utils.GColor
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedGroup
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.font.FontParameter
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch

class StartScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        val PlayerDataList = mutableListOf<PlayerData>()
    }

    data class PlayerData(var regionIndex: Int, var name: String)

    private var playerCount = 1

    private val fontParameter = FontParameter()
    private val font154       = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(154))
    private val font28        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(28))

    private val ls154 = Label.LabelStyle(font154, GColor.number)
    private val ls28  = Label.LabelStyle(font28, Color.WHITE)

    private val btnMenu   = AButton(this, AButton.Static.Type.Menu)
    private val btnSett   = AButton(this, AButton.Static.Type.Sett)
    private val btnStart  = AButton(this, AButton.Static.Type.Start)
    private val imgNumber = Image(game.all.NUMBER)
    private val lblNum    = Label(playerCount.toString(), ls154)
    private val playerList = MutableList(playerCount) { ASelectPlayer(this, ls28, it.inc()) }
    private val tmpGroup   = TmpGroup(this)

    private var currentTopPlayerIndex = 0

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_4.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(tmpGroup)
                addMenu()
                addSett()
                addStart()
                addImgNumber()
                tmpGroup.addPlayerList()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(26f,764f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addSett() {
        addActor(btnSett)
        btnSett.apply {
            setBounds(313f,764f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingScreen::class.java.name, StartScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addStart() {
        addActor(btnStart)
        btnStart.apply {
            setBounds(88f,74f,215f,94f)
            setOnClickListener {
                PlayerDataList.clear()
                playerList.onEach { selectPlayer -> PlayerDataList.add(PlayerData(selectPlayer.regionIndex, selectPlayer.playerName)) }

                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name, StartScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgNumber() {
        addActor(imgNumber)
        imgNumber.setBounds(74f,539f,243f,190f)

        addActor(lblNum)
        lblNum.apply {
            setAlignment(Align.center)
            setBounds(162f,543f,71f,186f)
        }

        val aLeft  = Actor()
        val aRight = Actor()
        addActors(aLeft, aRight)
        aLeft.apply {
            setBounds(65f,572f,67f,128f)
            setOnClickListener {
                game.soundUtil.apply { play(select, 0.4f) }

                playerCount -= 1
                if (playerCount == 0) playerCount = 3
                lblNum.setText(playerCount)
                updatePlayers()
            }
        }
        aRight.apply {
            setBounds(256f,572f,67f,128f)
            setOnClickListener {
                game.soundUtil.apply { play(select, 0.4f) }

                playerCount += 1
                if (playerCount > 3) playerCount = 1
                lblNum.setText(playerCount)
                updatePlayers()
            }
        }
    }

    private fun AdvancedGroup.addPlayerList() {
        playerList.reversed().onEach { aSelectPlayer ->
            addActor(aSelectPlayer)
            aSelectPlayer.setBounds(71f,241f,248f,240f)
        }

        val posList = listOf(
            Vector2(256f,275f),
            Vector2(-17f,275f),
        )
        val minScale = 0.6048f
        val time     = 0.150f

        currentTopPlayerIndex = 0

        playerList.onEachIndexed { index, aSelectPlayer ->
            if (index > 0) {
                aSelectPlayer.setScale(minScale)
                aSelectPlayer.addAction(Actions.moveTo(posList[index-1].x,posList[index-1].y,time))
            }
            aSelectPlayer.setOnClickListener { clickPlayer ->
                game.soundUtil.apply { play(select, 0.4f) }

                if (currentTopPlayerIndex == index) return@setOnClickListener

                playerList[currentTopPlayerIndex].also { topPlayer ->
                    currentTopPlayerIndex = index

                    topPlayer.toBack()
                    clickPlayer.toFront()

                    val clickPlayerX = clickPlayer.x
                    val clickPlayerY = clickPlayer.y

                    topPlayer.addAction(Actions.parallel(
                        Actions.scaleTo(minScale, minScale, time),
                        Actions.moveTo(clickPlayerX, clickPlayerY, time)
                    ))
                    clickPlayer.addAction(Actions.parallel(
                        Actions.scaleTo(1f, 1f, time),
                        Actions.moveTo(71f,241f, time)
                    ))
                }
            }
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun updatePlayers() {
        playerList.onEach { it.remove() }
        playerList.clear()
        repeat(playerCount) { playerList.add(ASelectPlayer(this, ls28, it.inc())) }

        tmpGroup.addPlayerList()
    }

}