package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.font.FontParameter

class ShopScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font60        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(60))

    private val ls60      = LabelStyle(font60, GColor.black)
    private val ls60White = LabelStyle(font60, GColor.white)

    private val listItem  = listOf(
        game.all.plus_time,
        game.all.plus_pazzle,
    )
    private val listCountItem  get() = listOf(
        game.dataStore.bonusTime,
        game.dataStore.bonusX3,
    )
    private var currentItemIndex = 0

    // Actors
    private val aBack    = Actor()
    private val lblPX    = Label(game.dataStore.px.toString(), ls60)
    private val lblCount = Label(game.dataStore.bonusTime.toString(), ls60White)
    private val aLeft    = Actor()
    private val aRight   = Actor()
    private val aBuy     = Actor()

    private val imgItem = Image(listItem[currentItemIndex])

    override fun show() {
        setUIBackground(game.all.background_3)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLblPX()
        addBack()
        addLblCount()
        addImgItem()
        addLeftAndRight()
        addBuy()

        val aPX = Actor()
        addActor(aPX)
        aPX.apply {
            setBounds(680f, 1581f, 402f, 135f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(CoinsScreen::class.java.name, ShopScreen::class.java.name)
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

    private fun AdvancedStage.addLblCount() {
        addActor(lblCount)
        lblCount.setBounds(70f, 1205f, 35f, 41f)
    }

    private fun AdvancedStage.addImgItem() {
        addActor(imgItem)
        imgItem.setBounds(203f, 994f, 662f, 550f)
    }

    private fun AdvancedStage.addLeftAndRight() {
        addActors(aLeft, aRight)
        aLeft.setBounds(48f, 932f, 218f, 90f)
        aRight.setBounds(737f, 1107f, 218f, 90f)

        aLeft.setOnClickListener(game.soundUtil) {
            if (currentItemIndex - 1 >= 0) {
                currentItemIndex -= 1
                imgItem.drawable = TextureRegionDrawable(listItem[currentItemIndex])
                lblCount.setText(listCountItem[currentItemIndex])
            }
        }
        aRight.setOnClickListener(game.soundUtil) {
            if (currentItemIndex + 1 <= 1) {
                currentItemIndex += 1
                imgItem.drawable = TextureRegionDrawable(listItem[currentItemIndex])
                lblCount.setText(listCountItem[currentItemIndex])
            }
        }
    }

    private fun AdvancedStage.addBuy() {
        addActor(aBuy)
        aBuy.setBounds(366f, 774f, 421f, 174f)

        aBuy.setOnClickListener {
            if (game.dataStore.px >= 1500) {
                game.soundUtil.apply { play(buy, 0.5f) }

                game.dataStore.updatePX { it - 1500 }
                lblPX.setText(game.dataStore.px)
                when(currentItemIndex) {
                    0 -> {
                        game.dataStore.updateBonusTime { it + 1 }
                        lblCount.setText(game.dataStore.bonusTime)
                    }
                    1 -> {
                        game.dataStore.updateBonusX3 { it + 1 }
                        lblCount.setText(game.dataStore.bonusX3)
                    }
                }
            } else game.soundUtil.apply { play(fail) }
        }
    }



}