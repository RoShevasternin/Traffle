package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.actors.AButton
import com.swee.ttrio.comb.game.actors.ALabel
import com.swee.ttrio.comb.game.utils.GColor
import com.swee.ttrio.comb.game.utils.TIME_ANIM
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.font.FontParameter
import com.swee.ttrio.comb.game.utils.region
import com.swee.ttrio.comb.game.utils.runGDX
import kotlinx.coroutines.launch
import kotlin.random.Random

class WinsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val playerDataList = GameScreen.PlayerDataList?.sortedByDescending { it.gamePlayer.score }!!

    private val fontParameter = FontParameter()
    private val font28        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(28))
    private val font15        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(15))
    private val font11        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(11))

    private val font47        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(47))
    private val font35        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(35))

    private val ls28 = Label.LabelStyle(font28, Color.WHITE)
    private val ls15 = Label.LabelStyle(font15, Color.WHITE)
    private val ls11 = Label.LabelStyle(font11, Color.WHITE)

    private val ls47 = Label.LabelStyle(font47, GColor.number)
    private val ls35 = Label.LabelStyle(font35, GColor.number)

    private val btnMenu    = AButton(this, AButton.Static.Type.Menu)
    private val btnSett    = AButton(this, AButton.Static.Type.Sett)
    private val btnRestart = AButton(this, AButton.Static.Type.Restart)
    private val imgWINS    = Image(game.all.WINS)

    private val lsNameList  = listOf(ls15, ls11, ls11)
    private val lsScoreList = listOf(ls47, ls35, ls35)

    private val lblWinPlayer = Label(playerDataList.first().gamePlayer.playerName, ls28)
    private val iconList     = List(playerDataList.size) { Image(playerDataList[it].gamePlayer.playerRegion) }
    private val nameList     = List(playerDataList.size) { Label(playerDataList[it].gamePlayer.playerName, lsNameList[it]) }
    private val scoreList    = List(playerDataList.size) { Label(playerDataList[it].gamePlayer.score.toString(), lsScoreList[it]) }

    override fun show() {
        game.soundUtil.apply { play(super_win_game) }

        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_2.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addRestart()
                addSett()
                addImgWINS()
                addPlayers()

                addLblWin()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(84f,71f,86f,86f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addRestart() {
        addActor(btnRestart)
        btnRestart.apply {
            setBounds(218f,71f,86f,86f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
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
                    game.navigationManager.navigate(SettingScreen::class.java.name, WinsScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgWINS() {
        addActor(imgWINS)
        imgWINS.setBounds(0f,211f,390f,527f)
    }

    private fun AdvancedStage.addLblWin() {
        addActor(lblWinPlayer)
        lblWinPlayer.setAlignment(Align.center)
        lblWinPlayer.setBounds(221f,638f,89f,44f)
    }

    private fun AdvancedStage.addPlayers() {
        val posIconList = listOf(
            Vector2(76f,545f),
            Vector2(105f,408f),
            Vector2(105f,264f),
        )
        val sizeIconList = listOf(90f, 68f, 68f)

        val posNameList = listOf(
            Vector2(99f,532f),
            Vector2(122f,397f),
            Vector2(122f,254f),
        )
        val sizeNameList = listOf(
            Vector2(48f,18f),
            Vector2(36f,14f),
            Vector2(36f,14f),
        )
        val posScoreList = listOf(
            Vector2(268f,529f),
            Vector2(250f,395f),
            Vector2(250f,252f),
        )
        val sizeScoreList = listOf(
            Vector2(42f,58f),
            Vector2(32f,44f),
            Vector2(32f,44f),
        )

        repeat(playerDataList.size) {
            nameList[it].setAlignment(Align.center)
            scoreList[it].setAlignment(Align.center)

            addActors(iconList[it], nameList[it], scoreList[it])
            iconList[it].setBounds(posIconList[it].x, posIconList[it].y, sizeIconList[it], sizeIconList[it])
            nameList[it].setBounds(posNameList[it].x, posNameList[it].y, sizeNameList[it].x, sizeNameList[it].y)
            scoreList[it].setBounds(posScoreList[it].x, posScoreList[it].y, sizeScoreList[it].x, sizeScoreList[it].y)
        }
    }

}