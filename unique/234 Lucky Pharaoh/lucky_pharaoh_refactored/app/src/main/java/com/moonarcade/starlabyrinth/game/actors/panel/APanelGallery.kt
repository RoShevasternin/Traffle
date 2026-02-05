/*
 * Refactored Application Module
 * Build: 535B51C7
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.AOnePuzzle
import com.moonarcade.starlabyrinth.game.actors.ScrollableContainer
import com.moonarcade.starlabyrinth.game.actors.autoLayout.AHorizontalGroup
import com.moonarcade.starlabyrinth.game.actors.button.GraphicButton
import com.moonarcade.starlabyrinth.game.actors.button.TextualButton
import com.moonarcade.starlabyrinth.game.actors.shader.ColorPuzzleGroup
import com.moonarcade.starlabyrinth.game.utils.ColorScheme
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.BaseScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontConfiguration
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelGallery(override val screen: BaseScreen): BaseGroup() {

    private val collectionPuzzle = listOf(
        Puzzle("\"Pharaoh's Golden Gaze\"",   gdxGame.assetsAll.collectionPuzzle[0], 1000,  10 , 1 ),
        Puzzle("\" The Explorer's Big Win\"", gdxGame.assetsAll.collectionPuzzle[1], 2000,  20 , 2 ),
        Puzzle("\"Sacred Scarab Jackpot\"",   gdxGame.assetsAll.collectionPuzzle[2], 3000,  30 , 3 ),
        Puzzle("\"Golden Sphinx's Riddle\"",  gdxGame.assetsAll.collectionPuzzle[3], 4000,  40 , 4 ),
        Puzzle("\"Anubis Weighs the Gold\"",  gdxGame.assetsAll.collectionPuzzle[4], 5000,  50 , 5 ),
        Puzzle("\"Treasure Chest Frenzy\"",   gdxGame.assetsAll.collectionPuzzle[5], 6000,  60 , 6 ),
        Puzzle("\"Pyramid of Spins\"",        gdxGame.assetsAll.collectionPuzzle[6], 7000,  70 , 7 ),
        Puzzle("\"Golden Queen's Bounty\"",   gdxGame.assetsAll.collectionPuzzle[7], 8000,  80 , 8 ),
        Puzzle("\"Lucky Cat in the Ruins\"",  gdxGame.assetsAll.collectionPuzzle[8], 9000,  90 , 9 ),
        Puzzle("\"The Final Golden Piece\"",  gdxGame.assetsAll.collectionPuzzle[9], 10000, 100, 10),
    )

    private data class Puzzle(
        val nName         : String,
        val texture       : Texture,
        val awardGold     : Int,
        val awardGems     : Int,
        val onePuzzlePrice: Int,
    )

    private val parameter42 = FontConfiguration().setCharacters(FontConfiguration.CharType.ALL).setSize(42)
    private val parameter52 = FontConfiguration().setCharacters(FontConfiguration.CharType.ALL).setSize(52)
    private val parameter62 = FontConfiguration().setCharacters(FontConfiguration.CharType.ALL).setSize(62)

    private val font42 = screen.fontGenerator_Regular.generateFont(parameter42)
    private val font52 = screen.fontGenerator_Regular.generateFont(parameter52)
    private val font62 = screen.fontGenerator_Regular.generateFont(parameter62)

    private val lsB_42 = Label.LabelStyle(font42, ColorScheme.black_09)
    private val lsW_42 = Label.LabelStyle(font42, ColorScheme.white_FE)
    private val ls52 = Label.LabelStyle(font52, ColorScheme.black_09)
    private val lsW_62 = Label.LabelStyle(font62, Color.WHITE)
    private val lsG_62 = Label.LabelStyle(font62, ColorScheme.green_CC)

    private var presentPuzzleIndex = 0
    private var presentPuzzle = collectionPuzzle[presentPuzzleIndex]

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_GALLERY)
    private val btnGet = TextualButton(screen, "GET", ls52)
    private val lblGet = Label("${presentPuzzle.awardGold} coins and ${presentPuzzle.awardGems} gems", lsW_42)
    private val lblName = Label(presentPuzzle.nName, lsG_62)
    private val btnLeft = GraphicButton(screen, gdxGame.assetsAll.left)
    private val btnRight = GraphicButton(screen, gdxGame.assetsAll.right)

    private val imgPuzzle = Image(presentPuzzle.texture)
    private val saturationGroup = ColorPuzzleGroup(screen)

    private val list_9_AOnePuzzle = List(9) { AOnePuzzle(screen, it, lsW_62, lsB_42) }
    private val horizontalGroup = AHorizontalGroup(screen, 117f, isWrapHorizontal = true)
    private val scroll = ScrollableContainer(horizontalGroup)

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLblGet()
        addBtnGet()
        addLblName()
        addPuzzle()
        addScroll()
        addBtnLeftRight()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLblGet() {
        addActor(lblGet)
        lblGet.setBounds(347f, 1540f, 250f, 45f)
        lblGet.setAlignment(Align.center)
    }

    private fun addBtnGet() {
        addActor(btnGet)
        btnGet.setBounds(635f, 1524f, 175f, 126f)
        btnGet.setOnClickListener {
            btnGet.disable()
            gdxGame.ds_Puzzle.update { dataPuzzle ->
                dataPuzzle[presentPuzzleIndex].isGetedAward = true
                dataPuzzle
            }

            gdxGame.ds_Gold.update { it + presentPuzzle.awardGold }
            gdxGame.ds_Gems.update { it + presentPuzzle.awardGems }
        }

        gdxGame.ds_Puzzle.flow.value[presentPuzzleIndex].also { dataPuzzle ->
            if (dataPuzzle.isGetedAward.not() && dataPuzzle.collectionGetedPuzzleIndex.size == 9) btnGet.enable()
            else btnGet.disable()
        }
    }

    // System operation
    private fun addBtnLeftRight() {
        addActors(btnLeft, btnRight)
        btnLeft.apply {
            setBounds(164f, 96f, 139f, 102f)
            setOnClickListener { handlerLeft() }
        }
        btnRight.apply {
            setBounds(784f, 96f, 139f, 102f)
            setOnClickListener { handlerRight() }
        }
    }

    private fun addLblName() {
        addActor(lblName)
        lblName.setBounds(287f, 112f, 513f, 70f)
        lblName.setAlignment(Align.center)
    }

    private fun addPuzzle() {
        addActor(saturationGroup)
        saturationGroup.setBounds(157f, 678f, 767f, 767f)
        saturationGroup.addAndFillActor(imgPuzzle)

        saturationGroup.updateActiveIndices(gdxGame.ds_Puzzle.flow.value[presentPuzzleIndex].collectionGetedPuzzleIndex)
    }

    // System operation
    private fun addScroll() {
        addActor(scroll)
        scroll.setBounds(0f, 153f, 1080f, 388f)
        horizontalGroup.setSize(1080f, 388f)

        list_9_AOnePuzzle.onEachIndexed { index, aOnePuzzle ->
            aOnePuzzle.setSize(614f, 382f)
            horizontalGroup.addActor(aOnePuzzle)

            aOnePuzzle.blockGet = {
                if (gdxGame.ds_Gems.flow.value >= aOnePuzzle.price) {
                    aOnePuzzle.disableBtnGet()

                    gdxGame.ds_Gems.update { it - aOnePuzzle.price }
                    gdxGame.ds_Puzzle.update { dataPuzzle ->
                        dataPuzzle[presentPuzzleIndex].collectionGetedPuzzleIndex.add(index)
                        dataPuzzle
                    }

                    this.animDelay(0.250f) { updatePuzzle() }
                }
            }
        }

        updateAOnePuzzle()
    }


    // Logic --------------------------------------------------------------------------

    private fun handlerLeft() {
        if (presentPuzzleIndex - 1 >= 0) {
            presentPuzzleIndex -= 1
        } else {
            presentPuzzleIndex = collectionPuzzle.lastIndex
        }

        updatePuzzle()
    }

    private fun handlerRight() {
        if (presentPuzzleIndex + 1 <= collectionPuzzle.lastIndex) {
            presentPuzzleIndex += 1
        } else {
            presentPuzzleIndex = 0
        }

        updatePuzzle()
    }

    // Internal processing
    private fun updatePuzzle() {
        presentPuzzle = collectionPuzzle[presentPuzzleIndex]

        imgPuzzle.drawable = TextureRegionDrawable(presentPuzzle.texture)
        lblGet.setText("${presentPuzzle.awardGold} coins and ${presentPuzzle.awardGems} gems")
        lblName.setText(presentPuzzle.nName)

        gdxGame.ds_Puzzle.flow.value[presentPuzzleIndex].also { dataPuzzle ->
            if (dataPuzzle.isGetedAward.not() && dataPuzzle.collectionGetedPuzzleIndex.size == 9) btnGet.enable()
            else btnGet.disable()
        }

        saturationGroup.updateActiveIndices(gdxGame.ds_Puzzle.flow.value[presentPuzzleIndex].collectionGetedPuzzleIndex)
        updateAOnePuzzle()
    }

    // Primary method handler
    private fun updateAOnePuzzle() {
        val onePuzzlePrice = presentPuzzle.onePuzzlePrice
        val collectionGetedPuzzleIndex = gdxGame.ds_Puzzle.flow.value[presentPuzzleIndex]

        splitTextureInto9Regions(presentPuzzle.texture).onEachIndexed { index, region ->
            list_9_AOnePuzzle[index].also { aOnePuzzle ->
                aOnePuzzle.updatePuzzle(onePuzzlePrice, region)

                if (collectionGetedPuzzleIndex.collectionGetedPuzzleIndex.contains(index)) aOnePuzzle.disableBtnGet() else aOnePuzzle.enableBtnGet()
            }
        }
    }

    // Primary method handler
    private fun splitTextureInto9Regions(texture: Texture): List<TextureRegion> {
        val cols = 3
        val rows = 3
        val width = texture.width / cols
        val height = texture.height / rows

        // Розділити текстуру на масив через TextureRegion.split
        val splitRegions = TextureRegion.split(texture, width, height)

        return splitRegions.flatMap { row -> row.toList() }
    }

}