package com.sugargalaxy.candyrunio.game.actors.panel

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.sugargalaxy.candyrunio.game.actors.AOnePuzzle
import com.sugargalaxy.candyrunio.game.actors.AScrollPane
import com.sugargalaxy.candyrunio.game.actors.autoLayout.AHorizontalGroup
import com.sugargalaxy.candyrunio.game.actors.button.AImageButton
import com.sugargalaxy.candyrunio.game.actors.button.ATextButton
import com.sugargalaxy.candyrunio.game.actors.shader.ASaturationPuzzleGroup
import com.sugargalaxy.candyrunio.game.utils.GameColor
import com.sugargalaxy.candyrunio.game.utils.actor.animDelay
import com.sugargalaxy.candyrunio.game.utils.actor.disable
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedGroup
import com.sugargalaxy.candyrunio.game.utils.advanced.AdvancedScreen
import com.sugargalaxy.candyrunio.game.utils.font.FontParameter
import com.sugargalaxy.candyrunio.game.utils.gdxGame

class APanelGallery(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listPuzzle = listOf(
        Puzzle("\"The Golden Dragon Pagoda\"",             gdxGame.assetsAll.listPuzzle[0], 1000,  10 , 1 ),
        Puzzle("\"Gate of Eternal Luck\"",                 gdxGame.assetsAll.listPuzzle[1], 2000,  20 , 2 ),
        Puzzle("\"Hyper Color Balloon Valley\"",           gdxGame.assetsAll.listPuzzle[2], 3000,  30 , 3 ),
        Puzzle("\"Electric Sky Drift\"",                   gdxGame.assetsAll.listPuzzle[3], 4000,  40 , 4 ),
        Puzzle("\"Crystal Balloon Atmosphere\"",           gdxGame.assetsAll.listPuzzle[4], 5000,  50 , 5 ),
        Puzzle("\"Festival Sky Explosion\"",               gdxGame.assetsAll.listPuzzle[5], 6000,  60 , 6 ),
        Puzzle("\"Sunburst Balloon Horizon\"",             gdxGame.assetsAll.listPuzzle[6], 7000,  70 , 7 ),
        Puzzle("\"Luminous Cloud Ocean\"",                 gdxGame.assetsAll.listPuzzle[7], 8000,  80 , 8 ),
        Puzzle("\"Turbo Balloon Airflow\"",                gdxGame.assetsAll.listPuzzle[8], 9000,  90 , 9 ),
        Puzzle("\"Victory Sky Realm\"",                    gdxGame.assetsAll.listPuzzle[9], 10000, 100, 10),
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
    private val lsW_42 = Label.LabelStyle(font42, GameColor.black_09)
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
        lblGet.setBounds(609f, 1625f, 244f, 43f)
        lblGet.setAlignment(Align.center)
    }

    private fun addBtnGet() {
        addActor(btnGet)
        btnGet.setBounds(894f, 1607f, 175f, 126f)
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
        saturationGroup.setBounds(165f, 678f, 767f, 767f)
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