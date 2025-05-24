package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.GColor
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.actor.setOnClickListener
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.font.FontParameter
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class ScoreScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var CurrentPlayerIndex = 0
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setBorder(3f, GColor.stroke)
    private val font48        = fontGenerator_RubikWetPaint.generateFont(fontParameter.setSize(48))

    private val ls48 = Label.LabelStyle(font48, GColor.pink)

    private val btnBeck  = AButton(this, AButton.Static.Type.Back)
    private val imgPanel = Image(game.all.PANEL_9)
    private val imgCube  = Image(game.all.CUBE)
    private val imgCurrent = Image(game.all.CURRENT)

    private val lblNamesList = List(NamesScreen.UserData_List.size) { Label(NamesScreen.UserData_List[it].uName, ls48) }
    private val lblScoreList = List(NamesScreen.UserData_List.size) { Label(NamesScreen.UserData_List[it].uScore.toString(), ls48) }

    override fun show() {
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addImgPanel()
                addImgCube()
                addUserDataList()
                addImgCurrent()
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBeck)
        btnBeck.apply {
            setBounds(50f,1795f,140f,76f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        val coff        = (NamesScreen.UserData_List.size - 2)
        val addedHeight = 72f * coff
        val panelY      = 1441f - addedHeight
        val panelH      = 255f + addedHeight

        addActor(imgPanel)
        imgPanel.setBounds(50f, panelY, 980f, panelH)
    }

    private fun AdvancedStage.addImgCube() {
        addActor(imgCube)
        imgCube.setBounds(195f,294f,727f,748f)
        imgCube.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(Actions.forever(Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.slowFast),
                    Actions.scaleBy(scale, scale, 0.5f, Interpolation.fastSlow),
                ))
            )
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(ShakeScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addUserDataList() {
        var ny = 1574f
        lblNamesList.onEachIndexed { index, labelName ->
            val lblScore = lblScoreList[index]
            addActors(labelName, lblScore)

            lblScore.setAlignment(Align.right)

            labelName.setBounds(160f,ny,346f,76f)
            lblScore.setBounds(827f,ny,96f,76f)

            ny -= 76f
        }
    }

    private fun AdvancedStage.addImgCurrent() {
        val ny = listOf(1590f, 1515f, 1440f, 1365f, 1290f, 1215f)[CurrentPlayerIndex]

        addActor(imgCurrent)
        imgCurrent.setBounds(97f, ny,43f,43f)
        imgCurrent.apply {
            setOrigin(Align.center)
            val scale = 0.2f
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-scale, -scale, 0.5f, Interpolation.slowFast),
                Actions.scaleBy(scale, scale, 0.5f, Interpolation.fastSlow),
            )))
        }
    }




}