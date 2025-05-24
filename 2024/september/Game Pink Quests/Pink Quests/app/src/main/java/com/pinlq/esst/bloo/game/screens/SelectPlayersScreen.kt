package com.pinlq.esst.bloo.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.pinlq.esst.bloo.game.LibGDXGame
import com.pinlq.esst.bloo.game.actors.APlayerCard
import com.pinlq.esst.bloo.game.actors.TmpGroup
import com.pinlq.esst.bloo.game.utils.*
import com.pinlq.esst.bloo.game.utils.actor.*
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedGroup
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedStage

class SelectPlayersScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        val listPlayerRegionIndex = mutableListOf<Int>()
    }

    private val countUser = NumOfPlayersScreen.COUNT_USER

    private val imgTitle       = Image(game.all.select_of_players)

    private val tmpGroup1      = TmpGroup(this)
    private val tmpGroup2      = TmpGroup(this)
    private val listPlayerCard = List(countUser) { APlayerCard(this, it.inc()) }
    private val imgPlayers     = Image(game.all.chars)

    private var currentPlayerCard = listPlayerCard.first()

    override fun show() {
        listPlayerRegionIndex.clear()
        repeat(countUser) { listPlayerRegionIndex.add(0) }

          setBackBackground(game.splash.listBackground[MenuScreen.BACKGROUND_INDEX].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgTitle()
        addAndFillActors(tmpGroup1, tmpGroup2)
        tmpGroup1.also {
            it.addListPlayerCard()
        }
        tmpGroup2.also {
            it.color.a = 0f
            it.disable()
            it.addImgPlayers()
        }

    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(115f,1582f,850f,188f)
    }

    private fun AdvancedGroup.addListPlayerCard() {
        val listPos = listOf(
            listOf(
                Vector2(285f,610f)
            ),
            listOf(
                Vector2(15f,610f),
                Vector2(555f,610f),
            ),
            listOf(
                Vector2(15f,801f),
                Vector2(555f,801f),
                Vector2(285f,61f),
            ),
            listOf(
                Vector2(15f,801f),
                Vector2(555f,801f),
                Vector2(15f,61f),
                Vector2(555f,61f),
            ),
        )[countUser-1]

        listPlayerCard.onEachIndexed { index, aPlayerCard ->
            addActor(aPlayerCard)
            val pos = listPos[index]
            aPlayerCard.setBounds(pos.x,pos.y,510f,700f)
            aPlayerCard.setOnClickListener(game.soundUtil) {
                currentPlayerCard = aPlayerCard
                showGroup2()
            }
        }
    }

    private fun AdvancedGroup.addImgPlayers() {
        addActor(imgPlayers)
        imgPlayers.setBounds(109f,229f,868f,1257f)

        var nx = 115f
        var ny = 1109f

        val listActor = List(9) { Actor() }
        listActor.onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx, ny,265f,298f)
            nx += 30 + 265
            if (index.inc() % 3 == 0) {
                nx = 115f
                ny -= 103 + 298
            }

            actor.setOnClickListener(game.soundUtil) {
                val personageRegion = game.splash.listPersonage[index]
                listPlayerRegionIndex[currentPlayerCard.userNum-1] = index
                currentPlayerCard.setIcon(personageRegion)
                showGroup1()
            }
        }
    }

    // Logic ---------------------------------------------------------------------------

    private fun showGroup2() {
        tmpGroup1.apply {
            disable()
            animHide(TIME_ANIM)
        }
        tmpGroup2.apply {
            enable()
            animShow(TIME_ANIM)
        }
    }

    private fun showGroup1() {
        tmpGroup2.apply {
            disable()
            animHide(TIME_ANIM)
        }
        tmpGroup1.apply {
            enable()
            animShow(TIME_ANIM) {
                if (listPlayerCard.all { it.isHasIcon }) {
                    disable()
                    addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run {
                            stageUI.root.animHideScreen(TIME_ANIM) {
                                game.navigationManager.navigate(ShakeScreen::class.java.name, SelectPlayersScreen::class.java.name)
                            }
                        }
                    ))
                }
            }
        }
    }


}