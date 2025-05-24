package com.colderonetrains.battlesskates.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.colderonetrains.battlesskates.game.actors.button.AButton
import com.colderonetrains.battlesskates.game.data.DataTrick
import com.colderonetrains.battlesskates.game.dataStore.DataDidItTrick
import com.colderonetrains.battlesskates.game.dataStore.LevelType
import com.colderonetrains.battlesskates.game.screens.TrickDescriptionScreen
import com.colderonetrains.battlesskates.game.utils.Block
import com.colderonetrains.battlesskates.game.utils.GameColor
import com.colderonetrains.battlesskates.game.utils.TIME_ANIM_SCREEN
import com.colderonetrains.battlesskates.game.utils.actor.animDelay
import com.colderonetrains.battlesskates.game.utils.actor.animHide
import com.colderonetrains.battlesskates.game.utils.actor.animShow
import com.colderonetrains.battlesskates.game.utils.actor.setBounds
import com.colderonetrains.battlesskates.game.utils.advanced.AdvancedMainGroup
import com.colderonetrains.battlesskates.game.utils.font.FontParameter
import com.colderonetrains.battlesskates.game.utils.gdxGame

class AMainTrickDescription(override val screen: TrickDescriptionScreen): AdvancedMainGroup() {

    companion object {
        @Suppress("GDXKotlinStaticResource")
        var iconTexture: Texture? = null

        var dataTrick: DataTrick? = null
        var levelType: LevelType? = null
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font33        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(33))
    private val font36        = screen.fontGenerator_GTWalsheimPro_Light.generateFont(fontParameter.setSize(36))
    private val font46        = screen.fontGenerator_GTWalsheimPro_Bold.generateFont(fontParameter.setSize(46))

    private val ls33 = Label.LabelStyle(font33, GameColor.white_F4)
    private val ls36 = Label.LabelStyle(font36, GameColor.white_D9)
    private val ls46 = Label.LabelStyle(font46, Color.WHITE)

    private val lblTitle       = Label(dataTrick!!.nName, ls46)
    private val lblDescription = Label(dataTrick!!.description, ls36)

    private val btnBack = AButton(screen, AButton.Type.Back)
    private val imgIcon = Image(iconTexture)

    private val imgPanelStep = Image(gdxGame.assetsAll.TRACK_CATALOG_PANEL)
    private val listLblStep  = List(5) { Label(dataTrick!!.listStep[it], ls33) }

    private val btnDidIt   = AButton(screen, AButton.Type.DidIt)
    private val btnBtnBack = AButton(screen, AButton.Type.BtnBack)


    override fun addActorsOnGroup() {
        color.a = 0f

        addBtnBack()
        addImgIcon()
        addImgPanelStep()
        addLbls()
        addBtns()

        animShowMain()
    }

    override fun dispose() {
        super.dispose()

        iconTexture = null
        dataTrick   = null
        levelType   = null
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(20f, 1663f, 124f, 115f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addImgIcon() {
        addActor(imgIcon)
        imgIcon.setBounds(333f, 1400f, 413f, 413f)
    }

    private fun addImgPanelStep() {
        addActor(imgPanelStep)
        imgPanelStep.setBounds(0f, 250f, 1032f, 811f)
    }

    private fun addLbls() {
        addActors(lblTitle, lblDescription)
        lblTitle.setBounds(93f, 1292f, 103f, 53f)
        lblDescription.setBounds(93f, 1103f, 898f, 164f)
        lblDescription.wrap = true
        lblDescription.setAlignment(Align.topLeft)

        var ny = 868f
        listLblStep.forEach { lbl ->
            addActor(lbl)
            lbl.setBounds(93f, ny, 852f, 38f)

            ny -= 100 + 38
        }
    }

    private fun addBtns() {
        if (gdxGame.ds_DataDidItTrick.flow.value.firstOrNull { it.nameTrick == dataTrick!!.nName } != null) {
            btnDidIt.pressAndDisable(true)
        }

        addActor(btnDidIt)
        btnDidIt.setBounds(95f, 114f, 422f, 104f)
        btnDidIt.setOnClickListener {
            btnDidIt.pressAndDisable(true)

            gdxGame.ds_DataDidItTrick.update {
                val mList = it.toMutableList()
                mList.add(DataDidItTrick(levelType!!, dataTrick!!.nName))
                mList
            }

//            screen.hideScreen {
//                gdxGame.navigationManager.back()
//            }
        }

        addActor(btnBtnBack)
        btnBtnBack.setBounds(562f, 114f, 422f, 104f)
        btnBtnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }


    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}