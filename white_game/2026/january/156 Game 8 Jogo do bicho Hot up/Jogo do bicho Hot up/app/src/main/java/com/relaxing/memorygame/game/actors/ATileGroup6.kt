package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relaxing.memorygame.game.utils.Layout
import com.relaxing.memorygame.game.utils.actor.setBounds
import com.relaxing.memorygame.game.utils.actor.setOnClickListener
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var tiles6_Remote: MutableList<Layout.LayoutData>? = null

class ATileGroup6(override val screen: AdvancedScreen): AdvancedGroup(), AbsTile {

    private val COUNT  = 8

    private var counter = 0
    private var tile1: ATileImage? = null
    private var tile2: ATileImage? = null

    private var winCounter = 0

    override var winBlock: () -> Unit = {}
    override var pairBlock: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.gameAssets.GAME_PANEL))
        addTiles()
    }

    private fun addTiles() {
        val regions = screen.game.gameAssets.ANIMALS.shuffled().take(COUNT)

        var i = 0
        val tiles = List(COUNT+COUNT) {
            if (it==COUNT) i = COUNT
            ATileGroup.Obj.Tile(it-i, regions[it-i])
        }

        tiles.onEachIndexed { index, tile ->
            ATileImage(screen, tile).also { tileImg ->
                addActor(tileImg)

                tileImg.setBounds(tiles6_Remote?.get(index) ?: Layout.Tile.tiles6[index])

                tileImg.setOnClickListener(screen.game.soundUtil) {
                    if (counter < 2) {
                        counter++
                        tileImg.animShowTile()

                        when(counter) {
                            1 -> tile1 = tileImg
                            2 -> {
                                coroutine?.launch {
                                    delay(1000)
                                    tile2 = tileImg

                                    if (tile1?.tile?.id == tile2?.tile?.id) {
                                        winCounter++
                                        pairBlock()
                                        screen.game.soundUtil.apply { play(PARA) }
                                        if (winCounter == COUNT) winBlock()
                                    } else {
                                        tile1?.animDefault()
                                        tile2?.animDefault()
                                        screen.game.soundUtil.apply { play(NEPARA) }
                                    }

                                    counter = 0
                                }
                            }
                        }

                    }
                }
            }
        }

    }

}