package com.slotscity.official.game.actors.treasure_snipes.slot5x3

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import kotlinx.coroutines.CompletableDeferred
import com.slotscity.official.game.actors.slots.SlotItem
import com.slotscity.official.game.utils.Layout
import com.slotscity.official.game.utils.advanced.AdvancedGroup
import com.slotscity.official.game.utils.advanced.AdvancedScreen
import com.slotscity.official.game.utils.runGDX
import com.slotscity.official.game.utils.Layout.TreasureSnipes.Slot as LS

class Slot(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        const val SLOT_ITEM_VISIBLE_COUNT      = 3
        const val SLOT_ITEM_INTERMEDIATE_COUNT = 16
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
        intermediateSlotItemImageList.first().color.a = 0f
        intermediateSlotItemImageList.last().color.a  = 0f

        var newY = LS.slot.y
        (fakeSlotItemImageList.reversed() + intermediateSlotItemImageList + winSlotItemImageList.reversed()).onEach { slotItemImage ->
            addActor(slotItemImage)
            slotItemImage.apply {
                drawable = TextureRegionDrawable(screen.game.treasureSnipesAssets.listItem.random())
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
        y = Layout.TreasureSnipes.SlotGroup.slot.y
    }

    suspend fun spin(time: Float) = CompletableDeferred<Boolean>().also { continuation ->
        runGDX {
            addAction(Actions.sequence(
                Actions.moveTo(x, LS.endY , time, Interpolation.sineOut),
                Actions.run {
                    screen.game.soundUtil.apply { play(boom) }
                    reset()
                    continuation.complete(true)
                }
            ))
        }
    }.await()
}