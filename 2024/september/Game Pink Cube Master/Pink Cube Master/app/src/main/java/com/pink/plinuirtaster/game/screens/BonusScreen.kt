package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.pink.plinuirtaster.R
import com.pink.plinuirtaster.appContext
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.GColor
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.font.FontParameter
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class BonusScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setBorder(2f, GColor.stroke2)
    private val font35        = fontGenerator_RubikWetPaint.generateFont(fontParameter.setSize(35).setSpaceY(35))

    private val ls35blue = Label.LabelStyle(font35, GColor.blue)
    private val ls35pink = Label.LabelStyle(font35, GColor.pink2)

    private val btnBeck     = AButton(this, AButton.Static.Type.Back)
    private val btnComplete = AButton(this, AButton.Static.Type.Completed)
    private val imgPanel    = Image(game.all.PANEL)
    private val imgBonus    = Image(game.all.BONUS)

    private val randomIndex = (0..19).random()

    private val lblBlue = Label("\"" + appContext.resources.getStringArray(R.array.blues)[randomIndex] + "\"", ls35blue)
    private val lblPink = Label(appContext.resources.getStringArray(R.array.pinks)[randomIndex], ls35pink)

    override fun show() {
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addImgCard()
                addCompleted()
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

    private fun AdvancedStage.addCompleted() {
        addActor(btnComplete)
        btnComplete.apply {
            setBounds(240f,164f,600f,180f)
            setOnClickListener(null) {
                runGDX { game.soundUtil.apply { play(WIN, 0.7f) } }
                
                stageUI.root.animHideScreen(TIME_ANIM) {
                    NamesScreen.UserData_List[ScoreScreen.CurrentPlayerIndex].uScore += (2..10).random()
                    if (ScoreScreen.CurrentPlayerIndex + 1 == NamesScreen.UserData_List.size) ScoreScreen.CurrentPlayerIndex = 0 else ScoreScreen.CurrentPlayerIndex += 1

                    game.navigationManager.also { nm ->
                        nm.navigate(ScoreScreen::class.java.name)
                        nm.clearBackStack()
                        nm.backStack.add(MenuScreen::class.java.name)
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addImgCard() {
        addActors(imgPanel, imgBonus, lblBlue, lblPink)
        imgPanel.setBounds(175f,507f,731f,1188f)
        imgBonus.setBounds(313f,1475f,433f,145f)
        lblBlue.apply {
            wrap = true
            setAlignment(Align.center)
            setBounds(196f,1125f,689f,179f)
        }
        lblPink.apply {
            wrap = true
            setAlignment(Align.center)
            setBounds(196f,565f,689f,532f)
        }
    }

}