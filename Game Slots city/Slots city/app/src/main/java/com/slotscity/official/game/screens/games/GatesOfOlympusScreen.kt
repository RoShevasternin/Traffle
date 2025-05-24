package com.slotscity.official.game.screens.games

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.ABottomPanel
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.actors.slots.slot6x5.SlotGroup
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.region

class GatesOfOlympusScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val assets = game.allAssets

    // Actor
    private val btnBack     = AButton(this, AButton.Static.Type.BACK)
    private val slotGroup   = SlotGroup(this)
    private val panelImg    = Image(assets.slot_group)
    private val titleImg    = Image(assets.gates_of_olimpus)
    private val gatesImg    = Image(assets.gates)
    private val doubleImg   = Image(assets.double_shans)
    private val doubleCBox  = ACheckBox(this, ACheckBox.Static.Type.ON_OFF)
    private val textImg     = Image(assets.listText.random())
    private val bottomPanel = ABottomPanel(this)

    override fun show() {
        stageBack.root.animHide()
        stageUI.root.animHide()
        setBackBackground(game.allAssets.Gates_of_Olympus.region)
        super.show()
        stageBack.root.animShow(TIME_ANIM_ALPHA) { stageUI.root.animShow(TIME_ANIM_ALPHA) }

        game.musicUtil.apply {
            coff  = 0.25f
            music = MUSIC.apply { isLooping = true }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addPanel()
        addSlotGroup()
        addTitle()
        addGates()
        addDouble()
        addTextImg()
        addBottomPanel()

    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        addActor(btnBack)
        btnBack.apply {
            setBounds(50f, 961f, 95f, 95f)
            setOnClickListener { stageUI.root.animHide(TIME_ANIM_ALPHA) {
                stageBack.root.animHide(TIME_ANIM_ALPHA) { game.navigationManager.back() }
            } }
        }
    }

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(223f, 101f, 1476f, 938f)
    }

    private fun AdvancedStage.addTitle() {
        addActor(titleImg)
        titleImg.setBounds(1672f, 817f, 250f, 129f)
    }

    private fun AdvancedStage.addGates() {
        addActor(gatesImg)
        gatesImg.setBounds(1654f, 256f, 268f, 561f)

        gatesImg.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -155f, 1f, Interpolation.smooth),
            Actions.moveBy(0f, 155f, 1f, Interpolation.smooth),
        )))
    }

    private fun AdvancedStage.addSlotGroup() {
        addActor(slotGroup)
        slotGroup.setBounds(273f, 129f, 1368f, 864f)
    }

    private fun AdvancedStage.addDouble() {
        addActors(doubleImg, doubleCBox)
        doubleImg.setBounds(1f, 486f, 263f, 306f)
        doubleCBox.setBounds(77f, 534f, 105f, 37f)
    }

    private fun AdvancedStage.addTextImg() {
        addActor(textImg)
        textImg.setBounds(270f, 1009f, 1381f, 53f)
        textImg.addAction(Actions.forever(Actions.sequence(
            Actions.delay((5..10).random().toFloat()),
            Actions.run { textImg.drawable = TextureRegionDrawable(assets.listText.random()) }
        )))
    }

    private fun AdvancedStage.addBottomPanel() {
        addActor(bottomPanel)
        bottomPanel.setBounds(0f, 0f, 1920f, 129f)

        bottomPanel.spinBlock = { slotGroup.spin() }
    }

}