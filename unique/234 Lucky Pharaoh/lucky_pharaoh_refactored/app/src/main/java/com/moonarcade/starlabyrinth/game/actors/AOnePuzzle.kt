/*
 * Refactored Application Module
 * Build: 6CD60D36
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class AOnePuzzle(
    override val screen: BaseScreen,
    val index: Int,

    ls62: Label.LabelStyle,
    ls42: Label.LabelStyle,
): BaseGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_ONE_PUZZLE)
    private val lblPrice = Label("", ls62)
    private val btnGet = TextualButton(screen, "GET", ls42)
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

    // Internal processing
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