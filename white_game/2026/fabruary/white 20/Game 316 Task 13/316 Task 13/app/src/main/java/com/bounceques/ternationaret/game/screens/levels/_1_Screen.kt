package com.bounceques.ternationaret.game.screens.levels

import com.badlogic.gdx.math.Vector2
import com.bounceques.ternationaret.game.LibGDXGame
import com.bounceques.ternationaret.game.box2d.bodies.BBall
import com.bounceques.ternationaret.game.box2d.bodies.BCoin
import com.bounceques.ternationaret.game.box2d.bodies.BRect
import com.bounceques.ternationaret.game.box2d.bodies.BTri
import com.bounceques.ternationaret.game.utils.TIME_ANIM
import com.bounceques.ternationaret.game.utils.actor.animHide
import com.bounceques.ternationaret.game.utils.actor.animShow
import com.bounceques.ternationaret.game.utils.advanced.AdvancedStage
import com.bounceques.ternationaret.game.utils.region

class _1_Screen(_game: LibGDXGame): Ilevel(_game) {

    private val bRectList = List(5) { BRect(this, game.assetsAll.listP[0]) }
    private val bCoinList = List(2) { BCoin(this) }
    private val bTriiList = List(2) { BTri(this, game.assetsAll.listT[0]) }
    override val bBall    = BBall(this)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B1.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStage() {
        createB_Rect()
        createB_Coin()
        createB_Tri()
        createB_Ball()
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Rect() {
        listOf(
            Vector2(0f, 1432f),
            Vector2(751f, 1175f),
            Vector2(-78f, 945f),
            Vector2(686f, 484f),
            Vector2(-34f, 324f),
        ).onEachIndexed { index, pos ->
            bRectList[index].create(pos, Vector2(503f, 81f))
        }
    }

    private fun createB_Coin() {
        listOf(
            Vector2(871f, 1272f),
            Vector2(364f, 420f),
        ).onEachIndexed { index, pos ->
            bCoinList[index].create(pos, Vector2(127f, 127f))
        }
    }

    private fun createB_Tri() {
        listOf(
            Vector2(-14f, 1026f),
            Vector2(821f, 555f),
        ).onEachIndexed { index, pos ->
            bTriiList[index].create(pos, Vector2(197f, 185f))
        }
    }

    private fun createB_Ball() {
        bBall.create(64f, 1549f, 214f, 214f)
    }

}