package com.candies.balloons.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.LibGDXGame
import com.candies.balloons.game.actors.AButton
import com.candies.balloons.game.actors.AGamePlayer
import com.candies.balloons.game.actors.APlayer
import com.candies.balloons.game.utils.*
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var WIN_INDEX_PLAYER = 0
            private set
        var WIN_INDEX_REG = 0
            private set
        var WIN_BALL_COUNT = 0
            private set
    }

    private val selectedPlayerList = PlayScreen.selectedList

    private val players = listOf(
        game.all._1,
        game.all._2,
        game.all._3,
        game.all._4,
    )
    private val balls   = listOf(
       game.all.b1,
       game.all.b2,
       game.all.b3,
       game.all.b4,
       game.all.b5,
       game.all.b6,
       game.all.b7,
       game.all.b8,
       game.all.b9,
    )

    private val ballPositionsList  = listOf(
        Vector2(361f, 1294f),
        Vector2(286f, 1232f),
        Vector2(204f, 1166f),
        Vector2(177f, 1088f),
        Vector2(259f, 1027f),
        Vector2(388f, 1073f),
        Vector2(491f, 1120f),
        Vector2(608f, 1166f),
        Vector2(708f, 1118f),
        Vector2(775f, 1050f),
        Vector2(759f, 972f),
        Vector2(672f, 906f),
        Vector2(577f, 840f),
        Vector2(491f, 781f),
        Vector2(432f, 719f),
        Vector2(464f, 647f),
        Vector2(551f, 587f),
        Vector2(655f, 647f),
        Vector2(740f, 710f),
        Vector2(845f, 647f),
        Vector2(818f, 569f),
        Vector2(759f, 505f),
        Vector2(691f, 441f),
        Vector2(587f, 382f),
        Vector2(510f, 320f),
    )
    private val randomWinPositions = (0..ballPositionsList.lastIndex).shuffled().take((5..10).random()).sorted().toMutableList()

    private val btnMenu        = AButton(this, AButton.Static.Type.Dom)
    private val btnRestart     = AButton(this, AButton.Static.Type.Restart)
    private val imgStartFinish = Image(game.all.start_finish)
    private val imgCircle      = Image(game.all.circ)
    private val imgCub         = Image(game.all.cub)
    private val playerList     = List(selectedPlayerList.size) { APlayer(this, selectedPlayerList[it].num, selectedPlayerList[it].indexReg) }
    private val playerGameList = List(selectedPlayerList.size) { AGamePlayer(this, players[selectedPlayerList[it].indexReg].region) }
    private val ballsList      = List(randomWinPositions.size) { Image(balls.random()) }
    private val imgCard        = Image(game.all.cart)

    private var currentPlayerIndex = 0

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.GRADIENT.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addRestart()
                addImgStartFinish()
                addImgCircCub()
                addPlayers()

                addBalls()
                addCart()
                addPlayersToGame()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(381f, 1723f, 126f, 124f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
                }
            }
        }
    }

    private fun AdvancedStage.addRestart() {
        addActor(btnRestart)
        btnRestart.apply {
            setBounds(575f, 1726f, 124f, 119f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(GameScreen::class.java.name) }
                }
            }
        }
    }

    private fun AdvancedStage.addImgStartFinish() {
        addActor(imgStartFinish)
        imgStartFinish.setBounds(159f, 21f, 804f, 1665f)
    }

    private fun AdvancedStage.addImgCircCub() {
        addActors(imgCircle, imgCub)
        imgCircle.setBounds(26f, 547f, 281f, 281f)
        imgCub.setBounds(83f, 605f, 173f, 173f)

        imgCub.apply {
            setOrigin(Align.center)
            setOnClickListener {
                game.soundUtil.apply { play(cubik, 0.15f) }

                this.disable()
                addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.rotateBy(-360f*4, 1.2f, Interpolation.swing),
                        Actions.repeat(2, Actions.sequence(
                            Actions.scaleBy(-0.2f, -0.2f, 0.25f),
                            Actions.scaleBy(0.2f, 0.2f, 0.25f),
                        ))
                    ),
                    Actions.run {
                        drawable           = TextureRegionDrawable(game.all.cub_gray)
                        imgCircle.drawable = TextureRegionDrawable(game.all.circ_gray)

                        goPlayer((1..5).random()) {
                            playerList[currentPlayerIndex].uncurrent()
                            if (currentPlayerIndex.inc() == playerList.size) currentPlayerIndex = 0 else currentPlayerIndex++
                            playerList[currentPlayerIndex].current()

                            drawable           = TextureRegionDrawable(game.all.cub)
                            imgCircle.drawable = TextureRegionDrawable(game.all.circ)
                            this.enable()
                        }
                    }
                ))
            }
        }
    }

    private fun AdvancedStage.addPlayers() {
        val posList = listOf(
            Vector2(17f, 1595f),
            Vector2(751f, 1595f),
            Vector2(17f, 40f),
            Vector2(751f, 40f),
        ).take(selectedPlayerList.size)

        playerList.onEachIndexed { index, aPlayer ->
            if (index == 0) aPlayer.current()
            addActor(aPlayer)
            aPlayer.setBounds(posList[index].x, posList[index].y, 304f, 280f)
        }
    }

    private fun AdvancedStage.addCart() {
        addActor(imgCard)
        imgCard.setBounds(437f, 250f, 109f, 141f)
        imgCard.setOrigin(58f, 1f)
        imgCard.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-0.2f, -0.2f, 0.4f, Interpolation.sineIn),
                    Actions.scaleBy(0.2f, 0.2f, 0.4f, Interpolation.sineOut),
                )
            ))
    }

    private fun AdvancedStage.addPlayersToGame() {
        addActors(playerGameList)
        when(selectedPlayerList.size) {
            1 -> {
                playerGameList[0].setBounds(486f, 1362f, 111f, 123f)
            }
            2 -> {
                playerGameList[0].setBounds(427f, 1362f, 111f, 123f)
                playerGameList[1].setBounds(547f, 1362f, 111f, 123f)

            }
            3 -> {
                playerGameList[0].setBounds(393f, 1362f, 111f, 123f)
                playerGameList[1].setBounds(486f, 1362f, 111f, 123f)
                playerGameList[2].setBounds(579f, 1362f, 111f, 123f)
            }
            4 -> {
                playerGameList[0].setBounds(371f, 1362f, 111f, 123f)
                playerGameList[1].setBounds(450f, 1362f, 111f, 123f)
                playerGameList[2].setBounds(529f, 1362f, 111f, 123f)
                playerGameList[3].setBounds(608f, 1362f, 111f, 123f)
            }
        }
    }

    private fun AdvancedStage.addBalls() {
        ballsList.onEachIndexed { index, ball ->
            addActor(ball)
            val pos = ballPositionsList[randomWinPositions[index]]
            ball.setBounds(pos.x, pos.y, 100f, 100f)
            ball.addAction(Actions.sequence(
                Actions.delay((10..100).random() / 100f),
                Actions.forever(Actions.sequence(
                    Actions.moveBy(0f, 20f, 0.3f, Interpolation.fastSlow),
                    Actions.delay(0.1f),
                    Actions.moveBy(0f, -20f, 0.15f, Interpolation.slowFast),
                    Actions.delay(0.3f),
                ))
            ))
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun goPlayer(step: Int, endBlock: () -> Unit) {
        playerGameList[currentPlayerIndex].also { player ->
            coroutine?.launch {
                repeat(step) {
                    if (isActive.not()) return@launch
                    runGDX {
                        val pos = if(player.steps == 25) Vector2(440f, 257f) else ballPositionsList[player.steps]
                        player.clearActions()
                        player.addAction(Actions.sequence(
                            Actions.moveTo(pos.x, pos.y, 0.4f, Interpolation.sine),
                            Actions.run {
                                when {
                                    player.steps == 25 -> {
                                        // WIN
                                        WIN_INDEX_PLAYER = currentPlayerIndex
                                        WIN_INDEX_REG    = playerList[currentPlayerIndex].indexReg
                                        WIN_BALL_COUNT   = playerList[currentPlayerIndex].counterFlow.value

                                        stageUI.root.animHide(TIME_ANIM) {
                                            game.navigationManager.navigate(WonScreen::class.java.name)
                                        }
                                        this.cancel()
                                    }
                                    randomWinPositions.contains(player.steps) -> {
                                        ballsList[randomWinPositions.indexOf(player.steps)].animHide(0.25f)
                                        randomWinPositions[randomWinPositions.indexOf(player.steps)] = 1000
                                        playerList[currentPlayerIndex].counterFlow.value += 1
                                        player.steps += 1
                                        game.soundUtil.apply { play(ball, 1f) }
                                    }
                                    else -> {
                                        player.steps += 1
                                        game.soundUtil.apply { play(point, 0.2f) }
                                    }
                                }
                            }
                        ))
                    }
                    delay(500)
                }
                runGDX { endBlock() }
            }
        }
    }

}