package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage

class GalleryScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private var currentIndex = 0

    // Actors
    private val aBack    = Actor()
    private val aLeft    = Actor()
    private val aRight   = Actor()
    private val imgItem  = Image(game.all.listG[currentIndex])

    override fun show() {
        setUIBackground(game.all.background_8)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addLeftAndRight()
        addImgItem()
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

    private fun AdvancedStage.addLeftAndRight() {
        addActors(aLeft, aRight)
        aLeft.setBounds(468f, 77f, 218f, 90f)
        aRight.setBounds(844f, 77f, 218f, 90f)

        aLeft.setOnClickListener(game.soundUtil) {
            if (currentIndex - 1 >= 0) {
                currentIndex -= 1
                imgItem.drawable = TextureRegionDrawable(game.all.listG[currentIndex])
            }
        }
        aRight.setOnClickListener(game.soundUtil) {
            if (currentIndex + 1 <= 24) {
                currentIndex += 1
                imgItem.drawable = TextureRegionDrawable(game.all.listG[currentIndex])
            }
        }
    }

    private fun AdvancedStage.addImgItem() {
        addActor(imgItem)
        imgItem.setBounds(95f, 170f, 944f, 1608f)
    }

}