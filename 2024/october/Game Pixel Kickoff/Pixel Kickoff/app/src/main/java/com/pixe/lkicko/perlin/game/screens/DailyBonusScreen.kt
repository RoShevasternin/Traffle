package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.font.FontParameter
import com.pixe.lkicko.perlin.util.getTodayDay

class DailyBonusScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font60        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(60))
    private val font110       = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(110))

    private val ls60  = LabelStyle(font60, GColor.black)
    private val ls110 = LabelStyle(font110, GColor.black)

    private var currentCountDay = getCurrentCountDay()

    private val bonus = 500 * currentCountDay

    // Actors
    private val aBack    = Actor()
    private val lblPX    = Label(game.dataStore.px.toString(), ls60)
    private val lblBonus = Label(bonus.toString(), ls60)
    private val lblDay   = Label(currentCountDay.toString(), ls110)
    private val imgGet   = Image(game.all.get_bonus)

    override fun show() {
        setUIBackground(game.all.background_4)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLblPX()
        addBack()
        addLblDay()
        addLblBonus()

        if (game.dataStore.isGetBonus.not()) addImgGet()

        val aPX = Actor()
        addActor(aPX)
        aPX.apply {
            setBounds(680f, 1581f, 402f, 135f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(CoinsScreen::class.java.name, DailyBonusScreen::class.java.name)
//                }
//            }
        }
    }

    private fun AdvancedStage.addLblPX() {
        addActor(lblPX)
        lblPX.setBounds(884f,1613f,152f,69f)
    }

    private fun AdvancedStage.addBack() {
        addActor(aBack)
        aBack.setBounds(833f, 1811f, 218f, 90f)

        aBack.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addLblBonus() {
        addActor(lblBonus)
        lblBonus.setBounds(765f, 959f, 152f, 69f)
    }

    private fun AdvancedStage.addLblDay() {
        addActor(lblDay)
        lblDay.setBounds(778f, 1021f, 106f, 138f)
        lblDay.setAlignment(Align.right)
    }

    private fun AdvancedStage.addImgGet() {
        addActor(imgGet)
        imgGet.setBounds(619f, 524f, 461f, 448f)

        val aGet = Actor()
        addActor(aGet)
        aGet.apply {
            setBounds(634f, 784f, 421f, 174f)
            setOnClickListener {
                imgGet.animHide(TIME_ANIM)
                aGet.disable()
                game.soundUtil.apply { play(buy, 0.5f) }
                game.dataStore.updateIsGetBonus { true }
                game.dataStore.updatePX { it + bonus }
                lblPX.setText(game.dataStore.px)
            }
        }
    }

    // Logic -----------------------------------------------------------------

    private fun getCurrentCountDay(): Int {
        if (getTodayDay() != game.dataStore.date) {
            game.dataStore.updateIsGetBonus { false }
            game.dataStore.updateDate { getTodayDay() }
            game.dataStore.updateDay { it + 1 }
        }
        return game.dataStore.day
    }

}