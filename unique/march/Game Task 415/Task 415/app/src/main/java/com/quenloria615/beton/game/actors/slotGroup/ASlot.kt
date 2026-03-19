package com.quenloria615.beton.game.actors.slotGroup

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.quenloria615.beton.game.utils.advanced.AdvancedGroup
import com.quenloria615.beton.game.utils.advanced.AdvancedScreen
import com.quenloria615.beton.game.utils.runGDX
import kotlinx.coroutines.CompletableDeferred
import com.quenloria615.beton.game.actors.slotGroup.Layout.Slot as LS
import com.quenloria615.beton.game.actors.slotGroup.Layout.SlotGroup as LSG

class ASlot(
    override val screen: AdvancedScreen,
    val listItemIntermediate: List<TextureRegion>,
    val interpolation: Interpolation = Interpolation.fastSlow,
): AdvancedGroup() {

    companion object {
        const val SLOT_ITEM_VISIBLE_COUNT      = 3
        const val SLOT_ITEM_INTERMEDIATE_COUNT = 14
    }

    private val intermediateSlotItemImageList = List(SLOT_ITEM_INTERMEDIATE_COUNT) { Image() }
    private val winSlotItemImageList          = List(SLOT_ITEM_VISIBLE_COUNT) { Image() }
    private val fakeSlotItemImageList         = List(SLOT_ITEM_VISIBLE_COUNT) { Image() }

    var listSlotItemWin = listOf<SlotItem>()
        set(value) {
            field = value
            value.onEachIndexed { index, slotItem -> winSlotItemImageList[index].drawable = TextureRegionDrawable(slotItem.region) }
        }

    override fun addActorsOnGroup() {
        addSlotItemList()
    }


    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun addSlotItemList() {
        //intermediateSlotItemImageList.first().color.a = 0f
        //intermediateSlotItemImageList.last().color.a  = 0f

        var newY = LS.slot.y
        (fakeSlotItemImageList.reversed() + intermediateSlotItemImageList + winSlotItemImageList.reversed()).onEach { slotItemImage ->
            addActor(slotItemImage)
            slotItemImage.apply {
                drawable = TextureRegionDrawable(listItemIntermediate.random())
                with(LS.slot) {
                    setBounds(x, newY, w, h)
                    newY += h + vs
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun reset() {
        winSlotItemImageList.onEachIndexed { index, image -> fakeSlotItemImageList[index].drawable = image.drawable }
        y = LSG.slot.y
    }

    suspend fun spin(time: Float) = CompletableDeferred<Boolean>().also { continuation ->
        runGDX {
            addAction(Actions.sequence(
                Actions.moveTo(x, LS.endY , time, interpolation),
                Actions.run {
                    //screen.game.soundUtil.apply { play(boom) }
                    reset()
                    continuation.complete(true)
                }
            ))
        }
    }.await()
}