package com.catchroyal.oceancatcher.actors

import com.catchroyal.oceancatcher.advanced.AdvancedGroup

class Countdown : AdvancedGroup() {

//    private val background = Image(SpriteManager.blur_background)
//    private val item = Image()
//    private val flowIndex = MutableStateFlow(0)
//
//
//
//    override fun sizeChanged() {
//        if (width > 0 && height > 0) {
//            addAndFillActor(background)
//            addActors(getActors())
//        }
//    }
//
//
//
//    private fun getActors() = listOf<Actor>(
//        setUpItems(),
//    )
//
//
//
//    private fun setUpItems() = item.apply {
//        setBoundsFigmaY(START_ITEM_X, START_ITEM_Y, START_ITEM_W, START_ITEM_H)
//    }
//
//
//
//    suspend fun go() = CompletableDeferred<Boolean>().also { continuation ->
//        with(item) {
//            setOrigin(Align.center)
//            flowIndex.take(4).collect { index ->
//                if (index < 3)
//                drawable = TextureRegionDrawable(SpriteManager.startList[index])
//                addAction(Actions.sequence(
//                        Actions.parallel(
//                            Actions.scaleTo(5f, 5f, 1f),
//                            Actions.fadeOut(1f),
//                        ),
//                        Actions.run { if (index != 2) {
//                                addAction(Actions.parallel(
//                                        Actions.scaleTo(1f, 1f),
//                                        Actions.fadeIn(0f),
//                                    ),) }
//                            flowIndex.tryEmit(flowIndex.value.inc())
//                        }
//                    ))
//            }
//            this@Start.apply {
//                isVisible = false
//                touchable = Touchable.disabled
//            }
//        }
//        continuation.complete(true)
//    }.await()

}