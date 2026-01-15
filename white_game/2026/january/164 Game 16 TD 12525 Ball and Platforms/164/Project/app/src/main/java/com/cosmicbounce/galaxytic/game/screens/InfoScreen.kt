package com.cosmicbounce.galaxytic.game.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.cosmicbounce.galaxytic.R
import com.cosmicbounce.galaxytic.appContext
import com.cosmicbounce.galaxytic.game.LibGDXGame
import com.cosmicbounce.galaxytic.game.actors.AButton
import com.cosmicbounce.galaxytic.game.actors.AInfo
import com.cosmicbounce.galaxytic.game.actors.AInfoGroup
import com.cosmicbounce.galaxytic.game.utils.HEIGHT_UI
import com.cosmicbounce.galaxytic.game.utils.TIME_ANIM
import com.cosmicbounce.galaxytic.game.utils.WIDTH_UI
import com.cosmicbounce.galaxytic.game.utils.actor.animHide
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedScreen
import com.cosmicbounce.galaxytic.game.utils.advanced.AdvancedStage
import com.cosmicbounce.galaxytic.game.utils.region
import com.cosmicbounce.galaxytic.game.utils.runGDX
import com.cosmicbounce.galaxytic.game.utils.toMS
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnNazad = AButton(this, AButton.Static.Type.Nazad)
    private val imgTitle = Image(game.all.infd)
    private val infoList = List(7) { AInfo(this, game.all.items[it], appContext.resources.getStringArray(R.array.title)[it], appContext.resources.getStringArray(R.array.text)[it]) }
    private val info     = AInfoGroup(this, infoList)
    private val scroll   = ScrollPane(info)

    override fun show() {
        setBackBackground(game.all.backgrounds.random().region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                goAsteroids()

                addNazad()
                addImgTitle()
                addScroll()
            }
            launch { btnNazad.animByNY(TIME_ANIM, 25f, 1027f) }
            launch { imgTitle.animByNY(TIME_ANIM, 124f, 860f) }
            launch { scroll.animByNY(TIME_ANIM, 28f, 0f) }
        }
    }

    private fun AdvancedStage.addNazad() {
        addActor(btnNazad)
        btnNazad.apply {
            setBounds(WIDTH_UI, 1027f, 116f, 64f)
            setOnClickListener {
                animHideScreen {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(WIDTH_UI, 860f, 388f, 132f)
    }

    private fun AdvancedStage.addScroll() {
        addActor(scroll)
        scroll.setBounds(28f, -733f, 580f, 733f)
    }

    // Anim ------------------------------------------------------------------------

    private fun animHideScreen(block: () -> Unit) {
        coroutine?.launch {
            launch { scroll.animByNY_From(TIME_ANIM, 28f, -733f) }
            launch { btnNazad.animByNY_From(TIME_ANIM, WIDTH_UI, 1027f) }
            launch { imgTitle.animByNY_From(TIME_ANIM, WIDTH_UI, 860f) }

            delay(TIME_ANIM.toMS)
            runGDX { block() }
        }
    }

    // Asteroids ------------------------------------------------------------------------

    private fun goAsteroids() {
        val aLeft  = Image(game.all.left)
        val aRight = Image(game.all.right)
        stageUI.addActors(aLeft, aRight)
        aLeft.setBounds(300f, HEIGHT_UI -300, 287f, 283f)
        aRight.setBounds(WIDTH_UI, HEIGHT_UI, 287f, 283f)


        val startYInterval = (538..849)
        val endYInterval   = (0..487)
        val timeInterval   = (70..300)

        fun startLeft() {
            aLeft.setPosition(-300f, startYInterval.random().toFloat())

            aLeft.apply {
                clearActions()
                addAction(Actions.sequence(
                    Actions.moveTo(WIDTH_UI, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startLeft() }
                ))
            }
        }

        fun startRight() {
            aRight.setPosition(WIDTH_UI, startYInterval.random().toFloat())

            aRight.apply {
                clearActions()
                addAction(Actions.sequence(
                    Actions.moveTo(-300f, endYInterval.random().toFloat(), timeInterval.random() / 100f),
                    Actions.run { startRight() }
                ))
            }
        }

        startLeft()
        startRight()


    }

}