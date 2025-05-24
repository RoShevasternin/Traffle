package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.enable
import com.pixe.lkicko.perlin.game.utils.font.FontParameter
import com.pixe.lkicko.perlin.util.log

class AwardsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val text = "COMPLETE LEVEL "

    private val fontParameter = FontParameter()
    private val font70        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(70))
    private val font60        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(60))

    private val ls60  = LabelStyle(font60, GColor.black)
    private val ls70 = LabelStyle(font70, GColor.black)

    private val listBonus = List(25) { 1000 + (100 * it) }

    private var currentLVL = 1

    // Actors
    private val aBack    = Actor()
    private val lblPX    = Label(game.dataStore.px.toString(), ls60)
    private val lblLVL   = Label(text + currentLVL, ls70)
    private val lblBonus = Label(listBonus[0].toString(), ls60)
    private val aLeft    = Actor()
    private val aRight   = Actor()
    private val aGet     = Image(game.all.get_award)

    private val imgClosed = Image(game.all.closed)

    override fun show() {
        setUIBackground(game.all.background_7)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addLblPX()
        addLblLVL()
        addLblBonus()
        addLeftAndRight()
        addImgGet()

        addActor(imgClosed)
        imgClosed.apply {
            setBounds(373f, 1024f, 360f, 474f)
            if (game.dataStore.getIsCompleteLevelByIndex(0)) this.animHide()
        }

        val aPX = Actor()
        addActor(aPX)
        aPX.apply {
            setBounds(680f, 1581f, 402f, 135f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(CoinsScreen::class.java.name, AwardsScreen::class.java.name)
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

    private fun AdvancedStage.addLblLVL() {
        addActor(lblLVL)
        lblLVL.setBounds(291f, 1091f, 422f, 176f)
        lblLVL.wrap = true
    }

    private fun AdvancedStage.addLblBonus() {
        addActor(lblBonus)
        lblBonus.setBounds(528f, 942f, 152f, 69f)
    }

    private fun AdvancedStage.addLeftAndRight() {
        addActors(aLeft, aRight)
        aLeft.setBounds(88f, 947f, 218f, 90f)
        aRight.setBounds(775f, 1107f, 218f, 90f)

        aLeft.setOnClickListener(game.soundUtil) {
            if (currentLVL - 1 >= 1) {
                currentLVL -= 1
                lblLVL.setText(text + currentLVL)
                lblBonus.setText(listBonus[currentLVL-1])

                log("d = ${currentLVL - 1} | ${game.dataStore.getIsCompleteLevelByIndex(currentLVL-1)} | ${game.dataStore.getIsGetBonusLevelByIndex(currentLVL-1).not()}")

                if (game.dataStore.getIsCompleteLevelByIndex(currentLVL-1)) {
                    imgClosed.animHide(TIME_ANIM)

                    if (game.dataStore.getIsGetBonusLevelByIndex(currentLVL-1).not()) {
                        aGet.apply {
                            this.enable()
                            this.animShow(TIME_ANIM)
                        }
                    } else {
                        aGet.apply {
                            this.disable()
                            this.animHide(TIME_ANIM)
                        }
                    }
                }
            }
        }
        aRight.setOnClickListener(game.soundUtil) {
            if (currentLVL + 1 <= 25) {
                currentLVL += 1
                lblLVL.setText(text + currentLVL)
                lblBonus.setText(listBonus[currentLVL-1])

                if (game.dataStore.getIsCompleteLevelByIndex(currentLVL-1).not()) {
                    imgClosed.animShow(TIME_ANIM)
                    aGet.apply {
                        this.disable()
                        this.animHide(TIME_ANIM)
                    }
                } else {
                    if (game.dataStore.getIsGetBonusLevelByIndex(currentLVL-1).not()) {
                        aGet.apply {
                            this.enable()
                            this.animShow(TIME_ANIM)
                        }
                    } else {
                        aGet.apply {
                            this.disable()
                            this.animHide(TIME_ANIM)
                        }
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addImgGet() {
        addActor(aGet)
        aGet.setBounds(412f, 475f, 667f, 448f)
        if (game.dataStore.getIsCompleteLevelByIndex(0).not()) {
            aGet.animHide()
            aGet.disable()
        } else {
            if (game.dataStore.getIsGetBonusLevelByIndex(0)) {
                aGet.animHide()
                aGet.disable()
            }
        }

        aGet.setOnClickListener(game.soundUtil) {
            game.soundUtil.apply { play(buy, 0.5f) }

            game.dataStore.updatePX { it + listBonus[currentLVL - 1] }
            lblPX.setText(game.dataStore.px)

            game.dataStore.updateIsGetBonusLevelByIndex(currentLVL - 1) { true }

            aGet.apply {
                disable()
                animHide(TIME_ANIM)
            }
        }
    }

}