package com.portalend.fruitomaner.game.screens

import com.portalend.fruitomaner.game.LibGDXGame
import com.portalend.fruitomaner.game.utils.advanced.AdvancedScreen
import com.portalend.fruitomaner.game.utils.advanced.AdvancedStage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.portalend.fruitomaner.game.actors.AButton
import com.portalend.fruitomaner.game.actors.AMap
import com.portalend.fruitomaner.game.utils.*
import com.portalend.fruitomaner.game.utils.actor.animHideSuspend
import com.portalend.fruitomaner.game.utils.actor.animShowSuspend
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapScreen(override val game: LibGDXGame) : AdvancedScreen() {

    // Actor
    private val btnBack = AButton(this, AButton.Static.Type.Back)
    private val mapa    = AMap(this)
    private val scroll  = ScrollPane(mapa)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addScroll()

                mapa.block = {
                    animHideScreen {
                        game.navigationManager.navigate(GameScreen::class.java.name, MapScreen::class.java.name)
                    }
                }
            }

            launch { scroll.animShowSuspend(TIME_ANIM) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnBack.animShowSuspend(TIME_ANIM) }
        }
    }

    private fun AdvancedStage.addBack() {
        addActors(btnBack)
        btnBack.apply {
            color.a = 0f
            setBounds(246f, 75f, 376f, 170f)
            setOnClickListener {
                animHideScreen { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addScroll() {
        addActors(scroll)
        scroll.apply {
            color.a = 0f
            setBounds(90f, 286f, 689f, 1172f)
        }
    }

    // Anim ------------------------------------------------------------------------

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { scroll.animHideSuspend(TIME_ANIM) }
            delay(TIME_ANIM.toMS / 2)
            launch { btnBack.animHideSuspend(TIME_ANIM) }

            delay((TIME_ANIM+0.25f).toMS)
            runGDX { block() }
        }
    }

}