package com.moonarcade.starlabyrinth.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.moonarcade.starlabyrinth.game.actors.AOnePuzzle
import com.moonarcade.starlabyrinth.game.actors.AScrollPane
import com.moonarcade.starlabyrinth.game.actors.autoLayout.AHorizontalGroup
import com.moonarcade.starlabyrinth.game.actors.button.AImageButton
import com.moonarcade.starlabyrinth.game.actors.button.ATextButton
import com.moonarcade.starlabyrinth.game.actors.shader.ASaturationPuzzleGroup
import com.moonarcade.starlabyrinth.game.utils.GameColor
import com.moonarcade.starlabyrinth.game.utils.actor.animDelay
import com.moonarcade.starlabyrinth.game.utils.actor.disable
import com.moonarcade.starlabyrinth.game.utils.advanced.AdvancedGroup
import com.moonarcade.starlabyrinth.game.utils.advanced.AdvancedScreen
import com.moonarcade.starlabyrinth.game.utils.font.FontParameter
import com.moonarcade.starlabyrinth.game.utils.gdxGame

class APanelGallery(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPuzzle = listOf(
        Puzzle("\"Pharaoh's Golden Gaze\"",   gdxGame.assetsAll.listPuzzle[0], 1000,  10 , 1 ),
        Puzzle("\" The Explorer's Big Win\"", gdxGame.assetsAll.listPuzzle[1], 2000,  20 , 2 ),
        Puzzle("\"Sacred Scarab Jackpot\"",   gdxGame.assetsAll.listPuzzle[2], 3000,  30 , 3 ),
        Puzzle("\"Golden Sphinx's Riddle\"",  gdxGame.assetsAll.listPuzzle[3], 4000,  40 , 4 ),
        Puzzle("\"Anubis Weighs the Gold\"",  gdxGame.assetsAll.listPuzzle[4], 5000,  50 , 5 ),
        Puzzle("\"Treasure Chest Frenzy\"",   gdxGame.assetsAll.listPuzzle[5], 6000,  60 , 6 ),
        Puzzle("\"Pyramid of Spins\"",        gdxGame.assetsAll.listPuzzle[6], 7000,  70 , 7 ),
        Puzzle("\"Golden Queen's Bounty\"",   gdxGame.assetsAll.listPuzzle[7], 8000,  80 , 8 ),
        Puzzle("\"Lucky Cat in the Ruins\"",  gdxGame.assetsAll.listPuzzle[8], 9000,  90 , 9 ),
        Puzzle("\"The Final Golden Piece\"",  gdxGame.assetsAll.listPuzzle[9], 10000, 100, 10),
    )

    private data class Puzzle(
        val nName         : String,
        val texture       : Texture,
        val awardGold     : Int,
        val awardGems     : Int,
        val onePuzzlePrice: Int,
    )

    private val parameter42 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(42)
    private val parameter52 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(52)
    private val parameter62 = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(62)

    private val font42 = screen.fontGenerator_Regular.generateFont(parameter42)
    private val font52 = screen.fontGenerator_Regular.generateFont(parameter52)
    private val font62 = screen.fontGenerator_Regular.generateFont(parameter62)

    private val lsB_42 = Label.LabelStyle(font42, GameColor.black_09)
    private val lsW_42 = Label.LabelStyle(font42, GameColor.white_FE)
    private val ls52   = Label.LabelStyle(font52, GameColor.black_09)
    private val lsW_62 = Label.LabelStyle(font62, Color.WHITE)
    private val lsG_62 = Label.LabelStyle(font62, GameColor.green_CC)

    private var currentPuzzleIndex = 0
    private var currentPuzzle      = listPuzzle[currentPuzzleIndex]

    private val imgPanel = Image(gdxGame.assetsAll.PANEL_GALLERY)
    private val btnGet   = ATextButton(screen, "GET", ls52)
    private val lblGet   = Label("${currentPuzzle.awardGold} coins and ${currentPuzzle.awardGems} gems", lsW_42)
    private val lblName  = Label(currentPuzzle.nName, lsG_62)
    private val btnLeft  = AImageButton(screen, gdxGame.assetsAll.left)
    private val btnRight = AImageButton(screen, gdxGame.assetsAll.right)

    private val imgPuzzle       = Image(currentPuzzle.texture)
    private val saturationGroup = ASaturationPuzzleGroup(screen)

    private val list_9_AOnePuzzle = List(9) { AOnePuzzle(screen, it, lsW_62, lsB_42) }
    private val horizontalGroup   = AHorizontalGroup(screen, 117f, isWrapHorizontal = true)
    private val scroll            = AScrollPane(horizontalGroup)

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
                dataPuzzle[currentPuzzleIndex].isGetedAward = true
                dataPuzzle
            }

            gdxGame.ds_Gold.update { it + currentPuzzle.awardGold }
            gdxGame.ds_Gems.update { it + currentPuzzle.awardGems }
        }

        gdxGame.ds_Puzzle.flow.value[currentPuzzleIndex].also { dataPuzzle ->
            if (dataPuzzle.isGetedAward.not() && dataPuzzle.listGetedPuzzleIndex.size == 9) btnGet.enable()
            else btnGet.disable()
        }
    }

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

        saturationGroup.updateActiveIndices(gdxGame.ds_Puzzle.flow.value[currentPuzzleIndex].listGetedPuzzleIndex)
    }

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
                        dataPuzzle[currentPuzzleIndex].listGetedPuzzleIndex.add(index)
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
        if (currentPuzzleIndex - 1 >= 0) {
            currentPuzzleIndex -= 1
        } else {
            currentPuzzleIndex = listPuzzle.lastIndex
        }

        updatePuzzle()
    }

    private fun handlerRight() {
        if (currentPuzzleIndex + 1 <= listPuzzle.lastIndex) {
            currentPuzzleIndex += 1
        } else {
            currentPuzzleIndex = 0
        }

        updatePuzzle()
    }

    private fun updatePuzzle() {
        currentPuzzle = listPuzzle[currentPuzzleIndex]

        imgPuzzle.drawable = TextureRegionDrawable(currentPuzzle.texture)
        lblGet.setText("${currentPuzzle.awardGold} coins and ${currentPuzzle.awardGems} gems")
        lblName.setText(currentPuzzle.nName)

        gdxGame.ds_Puzzle.flow.value[currentPuzzleIndex].also { dataPuzzle ->
            if (dataPuzzle.isGetedAward.not() && dataPuzzle.listGetedPuzzleIndex.size == 9) btnGet.enable()
            else btnGet.disable()
        }

        saturationGroup.updateActiveIndices(gdxGame.ds_Puzzle.flow.value[currentPuzzleIndex].listGetedPuzzleIndex)
        updateAOnePuzzle()
    }

    private fun updateAOnePuzzle() {
        val onePuzzlePrice       = currentPuzzle.onePuzzlePrice
        val listGetedPuzzleIndex = gdxGame.ds_Puzzle.flow.value[currentPuzzleIndex]

        splitTextureInto9Regions(currentPuzzle.texture).onEachIndexed { index, region ->
            list_9_AOnePuzzle[index].also { aOnePuzzle ->
                aOnePuzzle.updatePuzzle(onePuzzlePrice, region)

                if (listGetedPuzzleIndex.listGetedPuzzleIndex.contains(index)) aOnePuzzle.disableBtnGet() else aOnePuzzle.enableBtnGet()
            }
        }
    }

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