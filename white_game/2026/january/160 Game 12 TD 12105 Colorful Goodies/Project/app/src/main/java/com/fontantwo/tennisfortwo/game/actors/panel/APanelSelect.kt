package com.fontantwo.tennisfortwo.game.actors.panel

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fontantwo.tennisfortwo.game.actors.button.AButton
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedGroup
import com.fontantwo.tennisfortwo.game.utils.advanced.AdvancedScreen

class APanelSelect(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var LEFT_INDEX  = 0
        var RIGHT_INDEX = 0
    }

    private val leftRegions  = screen.game.assetsAll.rocketLeftList
    private val rightRegions = screen.game.assetsAll.rocketRightList

    private val cosmoStolImg = Image(screen.game.assetsAll.COSMO_STOL)
    private val vsImg        = Image(screen.game.assetsAll.vs)
    private val leftImg      = Image(leftRegions[LEFT_INDEX])
    private val rightImg     = Image(rightRegions[RIGHT_INDEX])
    private val leftUpBtn    = AButton(screen, AButton.Static.Type.Up)
    private val leftDownBtn  = AButton(screen, AButton.Static.Type.Down)
    private val rightUpBtn   = AButton(screen, AButton.Static.Type.Up)
    private val rightDownBtn = AButton(screen, AButton.Static.Type.Down)

    val goImg   = Image(screen.game.assetsAll.GO)
    val backBtn = AButton(screen, AButton.Static.Type.Back)

    override fun addActorsOnGroup() {
        addStolAndVs()
        addBack()

        addLeft()
        addRight()

        addGo()
    }

    // Actors ------------------------------------------------------------------------

    private fun addStolAndVs() {
        addActors(cosmoStolImg, vsImg)
        cosmoStolImg.setBounds(90f, 152f, 1227f, 615f)
        vsImg.setBounds(618f, 354f, 171f, 211f)
    }

    private fun addBack() {
        addActor(backBtn)
        backBtn.setBounds(1407f, 22f, 133f, 126f)
    }

    private fun addGo() {
        addActor(goImg)
        goImg.setBounds(652f, 253f, 105f, 68f)
    }

    private fun addLeft() {
        addActors(leftImg, leftUpBtn, leftDownBtn)
        leftImg.setBounds(308f, 361f, 197f, 197f)
        leftUpBtn.apply {
            setBounds(348f, 604f, 118f, 54f)
            setOnClickListener {
                if ((LEFT_INDEX+1) <= leftRegions.lastIndex) LEFT_INDEX += 1 else LEFT_INDEX = 0
                leftImg.drawable = TextureRegionDrawable(leftRegions[LEFT_INDEX])
            }
        }
        leftDownBtn.apply {
            setBounds(348f, 260f, 118f, 54f)
            setOnClickListener {
                if ((LEFT_INDEX-1) >= 0) LEFT_INDEX -= 1 else LEFT_INDEX = leftRegions.lastIndex
                leftImg.drawable = TextureRegionDrawable(leftRegions[LEFT_INDEX])
            }
        }
    }

    private fun addRight() {
        addActors(rightImg, rightUpBtn, rightDownBtn)
        rightImg.setBounds(902f, 361f, 197f, 197f)
        rightUpBtn.apply {
            setBounds(941f, 604f, 118f, 54f)
            setOnClickListener {
                if ((RIGHT_INDEX+1) <= rightRegions.lastIndex) RIGHT_INDEX += 1 else RIGHT_INDEX = 0
                rightImg.drawable = TextureRegionDrawable(rightRegions[RIGHT_INDEX])
            }
        }
        rightDownBtn.apply {
            setBounds(941f, 260f, 118f, 54f)
            setOnClickListener {
                if ((RIGHT_INDEX-1) >= 0) RIGHT_INDEX -= 1 else RIGHT_INDEX = rightRegions.lastIndex
                rightImg.drawable = TextureRegionDrawable(rightRegions[RIGHT_INDEX])
            }
        }
    }

}