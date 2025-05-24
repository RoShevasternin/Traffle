package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.utils.TIME_ANIM
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.actor.setOnClickListener
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.region
import com.baldasari.munish.cards.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CubikScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val imgBlured = Image(game.all.blured)
    private val imgCubik  = Image(game.all.cList.random())

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgBlured()
                addImgCubik()
            }

            launch {
                var isAnim = true
                var timer  = 0
                while (isActive && isAnim) {
                    delay(150)
                    runGDX {
                        imgCubik.drawable = TextureRegionDrawable(game.all.cList.random())
                    }
                    timer += 150
                    if (timer >= 4000) isAnim = false
                }

                runGDX {
                    imgCubik.clearActions()
                    imgCubik.addAction(Actions.rotateTo(0f, 0.2f))
                }
                delay(870)
                runGDX {
                    stageUI.root.animHide(TIME_ANIM) {
                        game.navigationManager.back()
                    }
                }
            }

        }
    }

    private fun AdvancedStage.addImgBlured() {
        addActor(imgBlured)
        imgBlured.setBounds(82f,357f,916f,1198f)
    }

    private fun AdvancedStage.addImgCubik() {
        addActor(imgCubik)
        imgCubik.setBounds(309f,701f,463f,517f)

        imgCubik.setOrigin(Align.center)
        imgCubik.addAction(Actions.forever(Actions.rotateBy(-360f, 1.5f, Interpolation.circle)))
    }

}