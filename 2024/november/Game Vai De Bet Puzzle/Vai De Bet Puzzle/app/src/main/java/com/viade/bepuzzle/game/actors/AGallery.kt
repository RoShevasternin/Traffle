package com.viade.bepuzzle.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen

class AGallery(override val screen: AdvancedScreen): AdvancedGroup() {

    private var currentIndex = 0

    private val listStars = screen.game.assetsAll.listStars
    private val listPhoto = screen.game.assetsAll.listPhoto

    private val listDataStars = screen.game.dataStoreGalleryStarsUtil.starsFlow.value

    private val texturePhoto: Texture? = if(listDataStars.isNotEmpty()) listPhoto[listDataStars.elementAt(currentIndex).categoryIndex][listDataStars.elementAt(currentIndex).levelIndex] else null
    private val textureStars: Texture? = if (listDataStars.isNotEmpty()) listStars[listDataStars.elementAt(currentIndex).starIndex] else null

    private val imgGold     = Image(screen.game.assetsAll.gold)
    private val imgPhoto    = if (texturePhoto == null) Image() else Image(texturePhoto)
    private val imgStars    = if (textureStars == null) Image() else Image(textureStars)
    private val btnLeft     = AButton(screen, AButton.Type.Left)
    private val btnRight    = AButton(screen, AButton.Type.Right)

    override fun addActorsOnGroup() {
        addImgGold()
        addImgPhoto()
        addImgStars()
        addBtnLeft()
        addBtnRight()
    }

    private fun addImgGold() {
        addActor(imgGold)
        imgGold.setBounds(45f, 297f, 788f, 1358f)
    }

    private fun addImgPhoto() {
        addActor(imgPhoto)
        imgPhoto.setBounds(59f, 311f, 760f, 1330f)
    }

    private fun addImgStars() {
        addActor(imgStars)
        imgStars.setBounds(204f, 0f, 492f, 212f)
    }

    private fun addBtnLeft() {
        addActor(btnLeft)
        btnLeft.setBounds(0f, 87f, 140f, 140f)
        btnLeft.setOnClickListener {
            screen.game.dataStoreGalleryStarsUtil.starsFlow.value.also { listStar ->
                if (listStar.isNotEmpty()) {
                    if ((currentIndex - 1) in listStar.indices) {
                        currentIndex--
                        listStar.elementAt(currentIndex).also { star ->
                            imgStars.drawable = TextureRegionDrawable(listStars[star.starIndex])
                            imgPhoto.drawable = TextureRegionDrawable(listPhoto[star.categoryIndex][star.levelIndex])
                        }
                    }
                }
            }
        }
    }

    private fun addBtnRight() {
        addActor(btnRight)
        btnRight.setBounds(745f, 91f, 140f, 140f)
        btnRight.setOnClickListener {
            screen.game.dataStoreGalleryStarsUtil.starsFlow.value.also { listStar ->
                if (listStar.isNotEmpty()) {
                    if ((currentIndex + 1) in listStar.indices) {
                        currentIndex++
                        listStar.elementAt(currentIndex).also { star ->
                            imgStars.drawable = TextureRegionDrawable(listStars[star.starIndex])
                            imgPhoto.drawable = TextureRegionDrawable(listPhoto[star.categoryIndex][star.levelIndex])
                        }
                    }
                }
            }
        }
    }

}