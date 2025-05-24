package com.swee.ttrio.comb.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.swee.ttrio.comb.game.LibGDXGame
import com.swee.ttrio.comb.game.actors.*
import com.swee.ttrio.comb.game.actors.checkbox.ACheckBox
import com.swee.ttrio.comb.game.utils.*
import com.swee.ttrio.comb.game.utils.actor.animHide
import com.swee.ttrio.comb.game.utils.actor.animShow
import com.swee.ttrio.comb.game.utils.actor.setOnClickListener
import com.swee.ttrio.comb.game.utils.advanced.AdvancedScreen
import com.swee.ttrio.comb.game.utils.advanced.AdvancedStage
import com.swee.ttrio.comb.game.utils.font.FontParameter
import com.swee.ttrio.comb.util.OneTime
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var PlayerDataList: List<PlayerData>? = null
    }

    private val selectedPlayerDataList = StartScreen.PlayerDataList

    private val fontParameter = FontParameter()
    private val font36        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(36))
    private val font21        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(21))
    private val font23        = fontGenerator_NerkoOne.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(23))

    private val ls36 = Label.LabelStyle(font36, GColor.number)
    private val ls21 = Label.LabelStyle(font21, Color.WHITE)
    private val ls23 = Label.LabelStyle(font23, GColor.purple)

    private val currentPlayerIndexFlow = MutableSharedFlow<Int>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private var currentPlayerIndex     = 0

    private val btnMenu  = AButton(this, AButton.Static.Type.Menu)
    private val boxPause = ACheckBox(this, ACheckBox.Static.Type.PAUSE_PLAY)
    private val imgStol  = Image(game.all.STOL)

    private val imgGrayCardList  = MutableList(25) { Image(game.all.GRAY_CARD) }
    private val lblCurrentPlayer = ALabel(this,"Player 1's turn", ls23)

    private val pausePanel = APausePanel(this).apply { color.a = 0f }

    // Field
    private val playerDataList = List(selectedPlayerDataList.size) { playerIndex -> PlayerData(
        AGamePlayer(this, selectedPlayerDataList[playerIndex], ls36, ls21),
        MutableList(5) { cardIndex -> ACard(this, ACard.Static.Type.entries[cardIndex]) }
    ) }
    private val gamePlayerList = ArrayDeque<AGamePlayer>(playerDataList.map { it.gamePlayer })

    data class PlayerData(val gamePlayer: AGamePlayer, val cards: MutableList<ACard>)

    private val cardAngleList = listOf(-12f, -6f, 0f, 2f, 12f)
    private val cardPosList   = listOf(
        Vector2(259f, -41f),
        Vector2(196f, -23f),
        Vector2(135f, -15f),
        Vector2(57f, -22f),
        Vector2(12f, -40f),
    )

    private val gamePlayerPosList = listOf(
        Vector2(109f,168f),
        Vector2(275f,722f),
        Vector2(14f,722f),
    )
    private val gamePlayerMinScale = 0.5380f

    private var currentClickedCard: ACard? = null
    private var currentClickedCardIndex    = 0
    private var currentClickedCardIndexZ   = 0

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.all.BACKGROUND_3.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            showCard(0)

            coroutine?.launch {
                stageUI.addImgGrayCardList()
            }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgStol()
                addMenu()
                addPause()

                addLblCurrentPlayer()

                addPlayers()
                addCards()

                addPausePanel()
            }
            launch { collectCurrentPlayerFlow() }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(140f,783f,51f,51f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(boxPause)
        boxPause.apply {
            setBounds(200f,783f,51f,51f)
            setOnCheckListener { if (it) animShowPausePanel() }
        }
    }

    private fun AdvancedStage.addImgStol() {
        addAndFillActor(imgStol)
    }

    private suspend fun AdvancedStage.addImgGrayCardList() {
        var angle = 0f
        imgGrayCardList.onEach { img ->
            runGDX {
                img.disable()
                addActor(img)
                img.setBounds(305f, 484f, 101f, 142f)
                img.rotation = angle
                angle += 0.5f

                img.setOnClickListener {
                    game.soundUtil.apply { play(gray, 0.2f) }

                    lblCurrentPlayer.label.setText("")
                    imgGrayCardList.onEach { it.disable() }
                    img.animTurn {
                        currentClickedCard?.let { card ->
                            card.type = ACard.Static.Type.entries.random()
                            card.randomIndex  = (0..9).random()
                            card.region       = game.all.listCards[card.type.ordinal][card.randomIndex]
                            card.img.drawable = TextureRegionDrawable(card.region)
                            card.addAction(Actions.moveTo(cardPosList[currentClickedCardIndex].x, cardPosList[currentClickedCardIndex].y, 0.5f, Interpolation.swingOut))
                        }
                        val onceWin = OneTime()
                        var isWin   = false

                        playerDataList[currentPlayerIndex].gamePlayer.score += (10..20).random()

                        ACard.Static.Type.entries.onEach { type ->
                            val countType = playerDataList[currentPlayerIndex].cards.count { it.type == type }
                            if (countType >= 3 || playerDataList[currentPlayerIndex].gamePlayer.score >= 100) {
                                onceWin.use {
                                    isWin = true
                                    playerDataList[currentPlayerIndex].gamePlayer.score += 100
                                    PlayerDataList = playerDataList
                                    stageUI.root.animHide(TIME_ANIM) {
                                        game.navigationManager.navigate(WinsScreen::class.java.name)
                                    }
                                }
                            }
                        }

                        if (isWin.not()) {
                            addAction(Actions.sequence(
                                Actions.delay(1f),
                                Actions.run { nextPlayer() }
                            ))
                        }
                    }
                }
            }
            delay(100)
        }
    }

    private fun AdvancedStage.addLblCurrentPlayer() {
        addActor(lblCurrentPlayer)
        lblCurrentPlayer.label.setAlignment(Align.center)

        lblCurrentPlayer.setBounds(133f,366f,123f,21f)
        lblCurrentPlayer.setOrigin(Align.center)

        lblCurrentPlayer.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-0.2f, -0.2f, 0.25f),
            Actions.scaleBy(0.2f, 0.2f, 0.25f),
        )))
    }

    private fun AdvancedStage.addPlayers() {
        playerDataList.onEachIndexed { index, playerData ->
            playerData.gamePlayer.also { player ->
                addActor(player)
                player.score = (20..30).random()
                player.setBounds(gamePlayerPosList[index].x, gamePlayerPosList[index].y, 171f, 189f)
                if (index != 0) player.setScale(gamePlayerMinScale)
            }
        }
    }

    private fun AdvancedStage.addCards() {
        playerDataList.onEach { pData -> pData.cards.onEachIndexed { index, card ->
            addActor(card)
            card.rotation = cardAngleList[index]
            card.setBounds(cardPosList[index].x,-200f,116f,163f)

            card.setOnClickListener {
                game.soundUtil.apply { play(this.card, 0.5f) }

                currentClickedCard       = card
                currentClickedCardIndex  = index
                currentClickedCardIndexZ = card.zIndex

                pData.cards.onEach { it.disable() }

                card.toFront()
                card.animTurn(index) {
                    lblCurrentPlayer.label.setText("Take card from deck")
                    card.zIndex = currentClickedCardIndexZ
                    imgGrayCardList.onEach { it.enable() }
                }
            }
        } }
    }

    private fun AdvancedStage.addPausePanel() {
        addAndFillActor(pausePanel)
        pausePanel.disable()
        pausePanel.playBlock = { boxPause.uncheck(false) }
    }

    // Logic ------------------------------------------------------------------------

    private suspend fun collectCurrentPlayerFlow() {
        currentPlayerIndexFlow.collect {
            currentPlayerIndex = it
            runGDX { lblCurrentPlayer.label.setText("Player ${it.inc()}'s turn") }
        }
    }

    private fun showCard(playerIndex: Int, endBlock: () -> Unit = {}) {
        playerDataList[playerIndex].cards.onEachIndexed { index, card ->
            card.addAction(Actions.sequence(
                Actions.moveTo(cardPosList[index].x, cardPosList[index].y, 0.4f, Interpolation.swingOut),
                Actions.run { if (index == playerDataList[playerIndex].cards.lastIndex) endBlock() }
            ))
        }
    }

    private fun hideCard(playerIndex: Int, endBlock: () -> Unit = {}) {
        playerDataList[playerIndex].cards.onEachIndexed { index, card ->
            card.addAction(Actions.sequence(
                Actions.moveTo(cardPosList[index].x, -200f, 0.3f, Interpolation.swingIn),
                Actions.run { if (index == playerDataList[playerIndex].cards.lastIndex) endBlock() }
            ))
        }
    }

    private fun ACard.animTurn(index: Int, endBlock: () -> Unit) {
        addAction(Actions.sequence(
            Actions.parallel(
                Actions.rotateTo(0f, 0.5f),
                Actions.moveTo(80f, 448f, 0.5f),
                Actions.scaleTo(1.6034f, 1.6034f, 0.5f)
            ),
            Actions.delay(0.7f),
            Actions.parallel(
                Actions.rotateTo((-5..5).random().toFloat(), 0.5f),
                Actions.moveTo(304f, (305..310).random().toFloat(), 0.5f),
                Actions.scaleTo(1f, 1f, 0.5f)
            ),
            Actions.run {
                endBlock()

                val img = Image(region)
                stageUI.addActor(img)
                img.rotation = rotation
                img.setBounds(x, y, width, height)
            },
            Actions.parallel(
                Actions.rotateTo(cardAngleList[index]),
                Actions.moveTo(cardPosList[index].x, -200f)
            )
        ))
    }

    private fun Image.animTurn(endBlock: () -> Unit) {
        setOrigin(Align.center)
        addAction(Actions.sequence(
            Actions.parallel(
                Actions.rotateTo(360f, 0.7f),
                Actions.moveTo(0f, -200f, 0.7f, Interpolation.swingIn),
            ),
            Actions.run { endBlock() }
        ))
    }

    private fun nextPlayer() {
        if (playerDataList.size > 1) {
            animUpdateGamePlayers {
                val removedGamePlayer = gamePlayerList.removeFirst()
                gamePlayerList.addLast(removedGamePlayer)

                hideCard(currentPlayerIndex) {
                    if (currentPlayerIndex + 1 > playerDataList.lastIndex) {
                        currentPlayerIndex = 0
                        currentPlayerIndexFlow.tryEmit(0)
                    } else {
                        currentPlayerIndex += 1
                        currentPlayerIndexFlow.tryEmit(currentPlayerIndex)
                    }

                    showCard(currentPlayerIndex)
                    playerDataList[currentPlayerIndex].cards.onEach { it.enable() }
                }
            }
        } else {
            currentPlayerIndexFlow.tryEmit(0)
            playerDataList[currentPlayerIndex].cards.onEach { it.enable() }
        }
    }

    private fun animUpdateGamePlayers(endBlock: () -> Unit) {
        val time          = 1f
        val interpolation = Interpolation.sine

        when(gamePlayerList.size) {
            2 -> {
                gamePlayerList[0].addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(gamePlayerMinScale, gamePlayerMinScale, time, interpolation),
                        Actions.moveTo(gamePlayerPosList[1].x, gamePlayerPosList[1].y, time, interpolation)
                    )
                ))
                gamePlayerList[1].addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(1f, 1f, time, interpolation),
                        Actions.moveTo(gamePlayerPosList[0].x, gamePlayerPosList[0].y, time, interpolation)
                    ),
                    Actions.run(endBlock)
                ))
            }
            3 -> {
                gamePlayerList[0].addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(gamePlayerMinScale, gamePlayerMinScale, time, interpolation),
                        Actions.moveTo(gamePlayerPosList[2].x, gamePlayerPosList[2].y, time, interpolation)
                    )
                ))
                gamePlayerList[1].addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(1f, 1f, time, interpolation),
                        Actions.moveTo(gamePlayerPosList[0].x, gamePlayerPosList[0].y, time, interpolation)
                    )
                ))
                gamePlayerList[2].addAction(Actions.sequence(
                    Actions.parallel(
                        Actions.scaleTo(gamePlayerMinScale, gamePlayerMinScale, time, interpolation),
                        Actions.moveTo(gamePlayerPosList[1].x, gamePlayerPosList[1].y, time, interpolation)
                    ),
                    Actions.run(endBlock)
                ))
            }
        }
    }

    private fun animShowPausePanel() {
        pausePanel.enable()
        pausePanel.toFront()
        pausePanel.animShow(TIME_ANIM) { pausePanel.toFront() }
    }

}