package com.relaxing.memorygame.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.relaxing.memorygame.game.screens.TileScreen
import com.relaxing.memorygame.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.relaxing.memorygame.game.utils.actor.animHide
import com.relaxing.memorygame.game.utils.actor.animShow
import com.relaxing.memorygame.game.utils.actor.disable
import com.relaxing.memorygame.game.utils.actor.enable
import com.relaxing.memorygame.game.utils.actor.setBounds
import com.relaxing.memorygame.game.utils.actor.setOnClickListener
import com.relaxing.memorygame.game.utils.advanced.AdvancedGroup
import com.relaxing.memorygame.game.utils.advanced.AdvancedScreen
import com.relaxing.memorygame.game.utils.font.FontParameter

class AResult(override val screen: AdvancedScreen, font: BitmapFont): AdvancedGroup() {

    private val win  = screen.game.gameAssets.WIN
    private val fail = screen.game.gameAssets.LOSE

    private val img = Image()
    private val coinLbl = Label("0", Label.LabelStyle(font, Color.WHITE))

    override fun addActorsOnGroup() {
        disable()
        animHide()
        addAndFillActor(img)

        addActor(coinLbl)
        coinLbl.setBounds(514f, 856f, 175f, 72f)
        coinLbl.setAlignment(Align.center)


        val menu = Actor()
        val xres = Actor()
        val rest = Actor()
        val next = Actor()
        addActors(menu, xres, rest, next,)
        menu.apply {
            setBounds(141f, 1272f, 119f, 120f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    screen.game.navigationManager.back()
                }
            }
        }
        xres.apply {
            setBounds(820f, 1273f, 118f, 120f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    screen.game.navigationManager.back()
                }
            }
        }
        rest.apply {
            setBounds(256f, 527f, 173f, 174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    screen.game.navigationManager.navigate(TileScreen::class.java.name)
                }
            }
        }
        next.apply {
            setBounds(651f, 529f, 173f, 174f)
            setOnClickListener(screen.game.soundUtil) {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    screen.game.navigationManager.navigate(TileScreen::class.java.name)
                }
            }
        }

    }

    fun animShowWin(v: Int) {
        screen.game.soundUtil.apply { play(URA) }
        coinLbl.setText(v)
        enable()
        img.drawable = TextureRegionDrawable(win)
        animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    fun animShowLose(v: Int) {
        screen.game.soundUtil.apply { play(LOSE) }
        coinLbl.setText(v)
        enable()
        img.drawable = TextureRegionDrawable(fail)
        animShow(TIME_ANIM_SCREEN_ALPHA)
    }

}