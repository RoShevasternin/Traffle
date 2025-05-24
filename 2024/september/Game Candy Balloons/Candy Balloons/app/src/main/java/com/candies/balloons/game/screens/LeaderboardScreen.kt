package com.candies.balloons.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.utils.GColor
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import com.candies.balloons.game.utils.font.FontParameter
import com.candies.balloons.game.utils.region
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.launch

class LeaderboardScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font45        = fontGenerator_BARLOW_BLACK.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(45))
    private val font81        = fontGenerator_BARLOW_BLACK.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(81))

    private val ls45A = Label.LabelStyle(font45, GColor.A)
    private val ls45B = Label.LabelStyle(font45, GColor.B)
    private val ls45C = Label.LabelStyle(font45, Color.WHITE)
    private val ls81A = Label.LabelStyle(font81, GColor.A)
    private val ls81B = Label.LabelStyle(font81, GColor.B)
    private val ls81C = Label.LabelStyle(font81, Color.WHITE)

    private val namesLS  = listOf(ls45A, ls45B, ls45C, ls45C, ls45C, ls45C)
    private val resultLS = listOf(ls81A, ls81B, ls81C, ls81C, ls81C, ls81C)

    private val names   = listOf(
        "Sugar Swirl",
        "Toffee Trix",
        "Jelly Jinx",
        "YOU",
        "Caramel Crunch",
        "Lolly Popper",
    ).shuffled()
    private val results = List(6) { (10..10_000).random() }.sortedDescending()

    private val lblNameList   = List(6) { Label(names[it], namesLS[it]) }
    private val lblResultList = List(6) { Label(results[it].toString(), resultLS[it]) }

    private val btnMenu        = AButton(this, AButton.Static.Type.Dom)
    private val imgTitle       = Image(game.all.leader)
    private val imgLeaderboard = Image(game.all.leaderboard)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.LIGHT.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgTitle()
                addImgLeaderboard()
                addLbls()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(880f, 1724f, 157f, 154f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addImgTitle() {
        addActor(imgTitle)
        imgTitle.setBounds(69f, 1434f, 941f, 230f)
    }

    private fun AdvancedStage.addImgLeaderboard() {
        addActor(imgLeaderboard)
        imgLeaderboard.setBounds(123f, 73f, 835f, 1234f)
    }

    private fun AdvancedStage.addLbls() {
        var ny = 1192f
        lblNameList.onEach {
            addActor(it)
            it.setBounds(328f, ny, 350f, 55f)
            ny -= 157+55
        }

        var resultNY = 1174f
        lblResultList.onEach {
            addActor(it)
            it.setBounds(686f, resultNY, 189f, 98f)
            resultNY -= 114+98
        }
    }

}