package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.actors.APuzzleGroup
import com.pixe.lkicko.perlin.game.actors.TmpGroup
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedGroup
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.font.FontParameter
import com.pixe.lkicko.perlin.game.utils.runGDX
import com.pixe.lkicko.perlin.util.OneTime
import com.pixe.lkicko.perlin.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var IS_AGAIN = false
            private set
    }

    private val fontParameter = FontParameter()
    private val font80        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + ":x").setSize(80))
    private val font120       = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(120))

    private val ls80  = LabelStyle(font80, Color.WHITE)
    private val ls120 = LabelStyle(font120, GColor.black)

    private val texturePuzzle = game.all.listP[PlayScreen.STATIC_counterLVL - 1]

    private val countUseTime = game.dataStore.bonusTime
    private val countUseX3   = game.dataStore.bonusX3

    // Actors
    private val imgMenu     = Image(game.all.menu_btn)
    private val areaMenu    = Actor()
    private val lblTime     = Label("00:00", ls80)
    private val lblUseTime  = Label("x$countUseTime", ls80)
    private val lblUseX3    = Label("x$countUseX3", ls80)
    private val imgPicture  = Image(game.all.lupa_picture)
    private val aPicture    = Actor()
    private val imgPuzzle   = Image(texturePuzzle)

    private val tmpGameGroup = TmpGroup(this)
    private val imgBonus     = Image(game.all.panel_use)
    private val aPuzzleGroup = APuzzleGroup(this, texturePuzzle)


    private var isShowedPicture = false
    private var isStopTimer     = false

    private var tMinutes   = 5
    private val oneTimeWin = OneTime()

    override fun show() {
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLblTime()
        addImgPuzzle()

        addAndFillActor(tmpGameGroup)
        tmpGameGroup.apply {
            addImgBonus()
            addAPuzzleGroup()
        }

        addMenu()
        addAPicture()
        addUseBtn()

    }

    // stageUI -------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(818f, 1551f, 264f, 365f)
        imgMenu.disable()

        addActor(areaMenu)
        areaMenu.setBounds(833f, 1811f, 218f, 90f)

        areaMenu.setOnClickListener(game.soundUtil) {
            root.animHide(TIME_ANIM) {
                game.navigationManager.clearBackStack()
                game.navigationManager.navigate(MenuScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addLblTime() {
        addActor(lblTime)
        lblTime.setBounds(433f, 1822f, 249f, 69f)

        coroutine?.launch {
            var tSeconds = 0

            while (isActive && isStopTimer.not()) {
                delay(1_000)

                // Зменшуємо секунди
                if (tSeconds == 0) {
                    if (tMinutes == 0) {
                        runGDX {
                            lblTime.setText("00:00") // Якщо таймер досяг 0, показуємо 00:00 і зупиняємо
                            loss()
                        }
                        break
                    } else {
                        tMinutes--
                        tSeconds = 59
                    }
                } else {
                    tSeconds--
                }

                // Форматуємо хвилини і секунди з двома символами
                val formattedTime = String.format("%02d:%02d", tMinutes, tSeconds)
                runGDX { lblTime.setText(formattedTime) }
            }
        }
    }

    private fun AdvancedStage.addAPicture() {
        addActor(imgPicture)
        imgPicture.setBounds(384f, -2f, 537f, 312f)

        addActor(aPicture)
        aPicture.setBounds(399f, 44f, 291f, 262f)

        aPicture.setOnClickListener(game.soundUtil) {
            if (isShowedPicture) {
                isShowedPicture     = false
                imgPicture.drawable = TextureRegionDrawable(game.all.lupa_picture)
                imgPuzzle.animHide(0.25f)
                tmpGameGroup.animShow(0.25f)
            } else {
                isShowedPicture     = true
                imgPicture.drawable = TextureRegionDrawable(game.all.lupa_back)
                tmpGameGroup.animHide(0.25f)
                imgPuzzle.animShow(0.25f)
            }
        }
    }

    private fun AdvancedStage.addImgPuzzle() {
        addActor(imgPuzzle)
        imgPuzzle.setBounds(132f, 346f, 816f, 1360f)
        imgPuzzle.disable()
        imgPuzzle.animHide()
    }

    private fun AdvancedStage.addUseBtn() {
        val aUseTime = Actor()
        val aUseX3   = Actor()

        addActors(aUseTime, aUseX3)

        aUseTime.apply {
            setBounds(75f, 27f, 222f, 92f)
            setOnClickListener {
                game.soundUtil.apply { play(buy, 0.5f) }

                if (game.dataStore.bonusTime >= 1) {
                    game.dataStore.updateBonusTime { it - 1 }
                    tMinutes += 1
                    lblUseTime.setText(game.dataStore.bonusTime)
                } else {
                    game.soundUtil.apply { play(fail) }
                }
            }
        }
        aUseX3.apply {
            setBounds(776f, 27f, 222f, 92f)
            setOnClickListener {
                game.soundUtil.apply { play(buy, 0.5f) }

                if (game.dataStore.bonusX3 >= 1) {
                    game.dataStore.updateBonusX3 { it - 1 }
                    aPuzzleGroup.useX3()
                    lblUseX3.setText(game.dataStore.bonusX3)
                } else {
                    game.soundUtil.apply { play(fail) }
                }
            }
        }
    }

    // tmpGameGroup -------------------------------------------------------------------

    private fun AdvancedGroup.addImgBonus() {
        addActor(imgBonus)
        imgBonus.setBounds(0f, -2f, 1080f, 335f)

        addActors(lblUseTime, lblUseX3)
        lblUseTime.setBounds(208f, 130f, 91f, 100f)
        lblUseX3.setBounds(741f, 130f, 86f, 100f)
    }

    private fun AdvancedGroup.addAPuzzleGroup() {
        addActor(aPuzzleGroup)
        aPuzzleGroup.setBounds(132f, 346f, 816f, 1360f)

        aPuzzleGroup.blockPuzzleCompleted = {
            oneTimeWin.use {
                isStopTimer = true
                log("WIN")
                victory()
            }
        }
    }

    // Logic ------------------------------------------------------------------------------

    private fun victory() {
        game.soundUtil.apply { play(football_win, 0.7f) }

        stageUI.root.disable()
        topStageBack.addAndFillActor(Image(drawerUtil.getRegion(GColor.black08)))

        val resultPX   = 100 * PlayScreen.STATIC_counterLVL
        game.dataStore.apply {
            updatePX { it + resultPX }
            if (IS_AGAIN.not()) {
                updateLVL { if (it < 25) it + 1 else it }
                updateIsCompleteLevelByIndex(PlayScreen.STATIC_counterLVL - 1) { true }
            }
        }


        val imgPanel   = Image(game.all.Victory)
        val lblVictory = Label(resultPX.toString(), ls120)
        val aMenu  = Actor()
        val aNext  = Actor()
        val aAgain = Actor()

        topStageUI.addAndFillActor(imgPanel)
        topStageUI.addActors(lblVictory, aMenu, aNext, aAgain)

        lblVictory.setBounds(426f, 960f, 197f, 150f)
        aMenu.apply {
            setBounds(399f, 743f, 421f, 174f)
            setOnClickListener(game.soundUtil) {
                IS_AGAIN = false

                topStageBack.root.animHide(TIME_ANIM)
                topStageUI.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        aNext.apply {
            setBounds(465f, 546f, 421f, 174f)
            setOnClickListener(game.soundUtil) {
                topStageBack.root.animHide(TIME_ANIM)
                topStageUI.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    IS_AGAIN = false
                    if (PlayScreen.STATIC_counterLVL < 25) {
                        PlayScreen.STATIC_counterLVL += 1
                        game.navigationManager.navigate(GameScreen::class.java.name)
                    }
                }
            }
        }
        aAgain.apply {
            setBounds(526f, 356f, 421f, 174f)
            setOnClickListener(game.soundUtil) {
                topStageBack.root.animHide(TIME_ANIM)
                topStageUI.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    IS_AGAIN = true
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }

    private fun loss() {
        game.soundUtil.apply { play(fail, 0.7f) }

        stageUI.root.disable()
        topStageBack.addAndFillActor(Image(drawerUtil.getRegion(GColor.black08)))

        val imgPanel   = Image(game.all.Loss)
        val lblLoss    = Label("0", ls120)
        val aMenu      = Actor()
        val aAgain     = Actor()

        topStageUI.addAndFillActor(imgPanel)
        topStageUI.addActors(lblLoss, aMenu, aAgain)

        lblLoss.setBounds(426f, 960f, 197f, 150f)
        aMenu.apply {
            setBounds(399f, 743f, 421f, 174f)
            setOnClickListener(game.soundUtil) {
                topStageBack.root.animHide(TIME_ANIM)
                topStageUI.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.clearBackStack()
                    game.navigationManager.navigate(MenuScreen::class.java.name)
                }
            }
        }
        aAgain.apply {
            setBounds(461f, 533f, 421f, 174f)
            setOnClickListener(game.soundUtil) {
                topStageBack.root.animHide(TIME_ANIM)
                topStageUI.root.animHide(TIME_ANIM)
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }


}