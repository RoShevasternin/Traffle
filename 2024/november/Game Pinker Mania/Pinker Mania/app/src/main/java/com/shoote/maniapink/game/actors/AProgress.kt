package com.shoote.maniapink.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shoote.maniapink.game.actors.shader.AMaskGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedGroup
import com.shoote.maniapink.game.utils.advanced.AdvancedScreen
import com.shoote.maniapink.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 797f

    private val assets = screen.game.all

    private val imgProgressBack  = Image(assets.prog_back)
    private val imgProgress      = Image(assets.prog)
    private val mask             = AMaskGroup(screen, assets.mask)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(imgProgressBack)
        addActor(mask)
        mask.setBounds(7f,15f,797f,38f)
        mask.addAndFillActor(imgProgress)

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    imgProgress.x = (percent * onePercentX) - LENGTH
                }
            }
        }
    }

}