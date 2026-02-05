/*
 * Auto-generated refactored code
 * Refactoring date: 2026-02-04
 * Engine: LibGDX Framework
 */

package com.novaburst.pixelrally.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.novaburst.pixelrally.game.actors.button.LabelButton
import com.novaburst.pixelrally.game.utils.advanced.ComponentGroup
import com.novaburst.pixelrally.game.utils.advanced.DisplayScreen
import com.novaburst.pixelrally.game.utils.gdxGame

class AOnePuzzle(
    override val screen: DisplayScreen,
    val index: Int,

    ls62: Label.LabelStyle,
    ls42: Label.LabelStyle,
): ComponentGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_ONE_PUZZLE)
    private val lblPrice  = Label("", ls62)
    private val btnGet    = LabelButton(screen, "GET", ls42)
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

    // Core functionality
    private fun addBtnGet() {
        addActor(btnGet)
        btnGet.apply {
            setBounds(56f, 88f, 151f, 111f)
            setOnClickListener { blockGet() }
        }
    }

    // Core functionality
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