package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.screens.MenuScreen
import com.viade.bepuzzle.game.utils.actor.animHide
import com.viade.bepuzzle.game.utils.actor.animShow
import com.viade.bepuzzle.game.utils.actor.disable
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class ASelectCard(override val screen: AdvancedScreen): AdvancedGroup() {

    private var currentIndex = 0
    private val levelOpened  = screen.game.dataStoreLevelOpenedUtil.levelOpenedFlow.value[MenuScreen.CATEGORY_INDEX]

    private val listLVL  = screen.game.assetsAll.listLVL
    private val listCard = screen.game.assetsAll.listCard[MenuScreen.CATEGORY_INDEX]

    private val imgLVL      = Image(listLVL[currentIndex])
    private val imgCard     = Image(listCard[currentIndex])
    private val btnLeft     = AButton(screen, AButton.Type.Left)
    private val btnRight    = AButton(screen, AButton.Type.Right)
    private val btnPlay     = AButton(screen, AButton.Type.Play)
    private val imgClosed   = Image(screen.game.assetsAll.locked)

    var blockPlay: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addImgLVL()
        addImgCard()
        addBtnLeft()
        addBtnRight()
        addBtnPlay()
        addImgClosed()
    }

    private fun addImgLVL() {
        addActor(imgLVL)
        imgLVL.setBounds(252f, 1256f, 541f, 455f)
    }

    private fun addImgCard() {
        addActor(imgCard)
        imgCard.setBounds(0f, 245f, 768f, 1026f)
        imgClosed.disable()
    }

    private fun addImgClosed() {
        addActor(imgClosed)
        imgClosed.setBounds(0f, 368f, 684f, 735f)
        imgClosed.color.a = 0f
    }

    private fun addBtnLeft() {
        addActor(btnLeft)
        btnLeft.setBounds(83f, 34f, 140f, 140f)
        btnLeft.setOnClickListener {
            if (currentIndex - 1 >= 0) {
                currentIndex--
                imgLVL.drawable  = TextureRegionDrawable(listLVL[currentIndex])
                imgCard.drawable = TextureRegionDrawable(listCard[currentIndex])
            }
            checkIsClosed()
        }
    }

    private fun addBtnRight() {
        addActor(btnRight)
        btnRight.setBounds(828f, 38f, 140f, 140f)
        btnRight.setOnClickListener {
            if (currentIndex + 1 <= 5) {
                currentIndex++
                imgLVL.drawable = TextureRegionDrawable(listLVL[currentIndex])
                imgCard.drawable = TextureRegionDrawable(listCard[currentIndex])
            }
            checkIsClosed()
        }
    }

    private fun addBtnPlay() {
        addActor(btnPlay)
        btnPlay.setBounds(342f, 0f, 368f, 216f)
        btnPlay.setOnClickListener { blockPlay(currentIndex) }
    }

    // Logic --------------------------------------------------------------------------------

    private fun checkIsClosed() {
        if(levelOpened >= (currentIndex + 1)) {
            imgClosed.animHide(0.25f)
            btnPlay.enable()
        } else {
            imgClosed.animShow(0.25f)
            btnPlay.disable()
        }

    }

}