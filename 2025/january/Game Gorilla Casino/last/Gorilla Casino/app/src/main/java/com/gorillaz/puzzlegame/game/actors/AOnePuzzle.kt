package com.gorillaz.puzzlegame.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.gorillaz.puzzlegame.game.actors.button.ATextButton
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedGroup
import com.gorillaz.puzzlegame.game.utils.advanced.AdvancedScreen
import com.gorillaz.puzzlegame.game.utils.gdxGame

class AOnePuzzle(
    override val screen: AdvancedScreen,
    val index: Int,

    ls62: Label.LabelStyle,
    ls42: Label.LabelStyle,
): AdvancedGroup() {

    private val imgPanel  = Image(gdxGame.assetsAll.PANEL_ONE_PUZZLE)
    private val lblPrice  = Label("", ls62)
    private val btnGet    = ATextButton(screen, "GET", ls42)
    private val imgPuzzle = Image()

    var price = 0
        private set

    var blockGet = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblPrice()
        addBtnGet()
        addImgPuzzle()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblPrice() {
        addActor(lblPrice)
        lblPrice.apply {
            setBounds(92f, 208f, 118f, 52f)
            setAlignment(Align.center)
        }
    }

    private fun addBtnGet() {
        addActor(btnGet)
        btnGet.apply {
            setBounds(56f, 88f, 151f, 111f)
            setOnClickListener { blockGet() }
        }
    }

    private fun addImgPuzzle() {
        addActor(imgPuzzle)
        imgPuzzle.setBounds(270f, 112f, 230f, 230f)
    }

    // Logic ------------------------------------------------------------------------

    fun updatePuzzle(price: Int, region: TextureRegion) {
        this.price = price
        lblPrice.setText(price)
        imgPuzzle.drawable = TextureRegionDrawable(region)
    }

    fun disableBtnGet() {
        btnGet.disable()
    }

    fun enableBtnGet() {
        btnGet.enable()
    }


}