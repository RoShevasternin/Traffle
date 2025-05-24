package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.actor.setOnClickListener
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class ACategory(override val screen: AdvancedScreen): AdvancedGroup() {

    private var currentIndex = 0

    private val listCategory = screen.game.assetsAll.listSport

    private val imgCategory = Image(listCategory[currentIndex])
    private val btnLeft     = AButton(screen, AButton.Type.Left)
    private val btnRight    = AButton(screen, AButton.Type.Right)
    private val btnPlay     = AButton(screen, AButton.Type.Play)

    var blockPlay: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addImgCategory()
        addBtnLeft()
        addBtnRight()
        addBtnPlay()
    }

    private fun addImgCategory() {
        addActor(imgCategory)
        imgCategory.setBounds(0f, 120f, 1026f, 1153f)
    }

    private fun addBtnLeft() {
        addActor(btnLeft)
        btnLeft.setBounds(74f, 34f, 140f, 140f)
        btnLeft.setOnClickListener {
            if (currentIndex - 1 >= 0) {
                currentIndex--
                imgCategory.drawable = TextureRegionDrawable(listCategory[currentIndex])
            }
        }
    }

    private fun addBtnRight() {
        addActor(btnRight)
        btnRight.setBounds(819f, 38f, 140f, 140f)
        btnRight.setOnClickListener {
            if (currentIndex + 1 <= 3) {
                currentIndex++
                imgCategory.drawable = TextureRegionDrawable(listCategory[currentIndex])
            }
        }
    }

    private fun addBtnPlay() {
        addActor(btnPlay)
        btnPlay.setBounds(333f, 0f, 368f, 216f)
        btnPlay.setOnClickListener { blockPlay(currentIndex) }
    }

}