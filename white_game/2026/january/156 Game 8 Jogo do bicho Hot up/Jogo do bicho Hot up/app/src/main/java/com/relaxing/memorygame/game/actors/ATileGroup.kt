package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.relaxing.memorygame.game.utils.actor.setOnClickListener
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface AbsTile {
    var winBlock: () -> Unit
    var pairBlock: () -> Unit
}

class ATileGroup(override val screen: AdvancedScreen): AdvancedGroup(), AbsTile {

    private val COUNT  = 6
    private val COLUMN = 4

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
            Obj.Tile(it-i, regions[it-i])
        }

        var nx = 82f
        var ny = 541f

        tiles.onEachIndexed { index, tile ->
            ATileImage(screen, tile).also { tileImg ->
                addActor(tileImg)
                tileImg.setBounds(nx, ny, 168f, 172f)
                nx += (168+51f)
                if (index.inc()%COLUMN==0) {
                    nx = 82f
                    ny -= (31f+172f)
                }

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

    object Obj {
        data class Tile(val id: Int, val region: TextureRegion)
    }

}