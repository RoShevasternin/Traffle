package com.slotscity.official.game.screens.games

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.slotscity.official.game.LibGDXGame
import com.slotscity.official.game.actors.ABottomPanel_SweetBonanza
import com.slotscity.official.game.actors.button.AButton
import com.slotscity.official.game.actors.checkbox.ACheckBox
import com.slotscity.official.game.actors.sweet_bonanza.slot6x5.SlotGroup
import com.slotscity.official.game.utils.TIME_ANIM_ALPHA
import com.slotscity.official.game.utils.actor.animHide
import com.slotscity.official.game.utils.actor.animShow
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.advanced.AdvancedStage
import com.slotscity.official.game.utils.region

class SweetBonanzaScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val assets        = game.allAssets
    private val assetsBonanza = game.sweetBonanzaAssets

    // Actor
    private val btnBack     = AButton(this, AButton.Static.Type.BACK)
    private val slotGroup   = SlotGroup(this)
    private val doubleImg   = Image(assetsBonanza.double)
    private val doubleCBox  = ACheckBox(this, ACheckBox.Static.SweetBonanzaType.ON_OFF)
    private val textImg     = Image(assetsBonanza.title)
    private val panelImg    = Image(assetsBonanza.SLOT_GROUP)
    private val bottomPanel = ABottomPanel_SweetBonanza(this)

    override fun show() {
        stageBack.root.animHide()
        stageUI.root.animHide()
        setBackBackground(game.sweetBonanzaAssets.BACKGROUND.region)
        super.show()
        stageBack.root.animShow(TIME_ANIM_ALPHA) { stageUI.root.animShow(TIME_ANIM_ALPHA) }

        game.musicUtil.apply {
            coff  = 0.25f
            music = game.sweetBonanzaMusic.MUSIC.apply { isLooping = true }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addPanel()
        addSlotGroup()
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
        panelImg.setBounds(435f, 147f, 1275f, 875f)
    }

    private fun AdvancedStage.addSlotGroup() {
        addActor(slotGroup)
        slotGroup.setBounds(445f, 163f, 1250f, 823f)
    }

    private fun AdvancedStage.addDouble() {
        addActors(doubleImg, doubleCBox)
        doubleImg.setBounds(49f, 350f, 269f, 380f)
        doubleCBox.setBounds(72f, 363f, 215f, 75f)
    }

    private fun AdvancedStage.addTextImg() {
        addActor(textImg)
        textImg.setBounds(627f, 1022f, 897f, 43f)
    }

    private fun AdvancedStage.addBottomPanel() {
        addActor(bottomPanel)
        bottomPanel.setBounds(0f, 0f, 1920f, 129f)

        bottomPanel.spinBlock = { slotGroup.spin() }
    }

}