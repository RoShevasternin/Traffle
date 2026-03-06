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

class _4_Screen(_game: LibGDXGame): Ilevel(_game) {

    private val bRectList = List(4) { BRect(this, game.assetsAll.listP[3]) }
    private val bCoinList = List(2) { BCoin(this) }
    private val bTriiList = List(3) { BTri(this, game.assetsAll.listT[3]) }
    override val bBall    = BBall(this)

    override fun show() {
        stageUI.root.animHide(TIME_ANIM)
        setBackBackground(game.assetsAll.B4.region)
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
            Vector2(288f, 1432f),
            Vector2(-84f, 943f),
            Vector2(653f, 545f),
            Vector2(-46f, 453f),
        ).onEachIndexed { index, pos ->
            bRectList[index].create(pos, Vector2(503f, 81f))
        }
    }

    private fun createB_Coin() {
        listOf(
            Vector2(67f, 1042f),
            Vector2(281f, 545f),
        ).onEachIndexed { index, pos ->
            bCoinList[index].create(pos, Vector2(127f, 127f))
        }
    }

    private fun createB_Tri() {
        listOf(
            Vector2(226f, 1014f),
            Vector2(884f, 626f),
            Vector2(-14f, 533f),
        ).onEachIndexed { index, pos ->
            bTriiList[index].create(pos, Vector2(197f, 185f))
        }
    }

    private fun createB_Ball() {
        bBall.create(433f, 1523f, 214f, 214f)
    }

}