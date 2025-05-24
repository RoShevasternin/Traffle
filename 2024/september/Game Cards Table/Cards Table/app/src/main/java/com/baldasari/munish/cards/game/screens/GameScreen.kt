package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.actors.AButton
import com.baldasari.munish.cards.game.actors.ACard
import com.baldasari.munish.cards.game.utils.*
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.actor.setOnClickListener
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.font.FontParameter
import com.baldasari.munish.cards.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font80        = fontGenerator_LuckiestGuy.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(80))
    private val font25        = fontGenerator_LuckiestGuy.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(25))
    private val font17        = fontGenerator_Lustria.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(17))

    private val ls80      = Label.LabelStyle(font80, Color.WHITE)
    private val ls25      = Label.LabelStyle(font25, GColor.xxx)
    private val ls25Black = Label.LabelStyle(font25, Color.BLACK)
    private val ls17      = Label.LabelStyle(font17, Color.BLACK)

    private val selectedPersonages     = PlayerScreen.listPerson
    private val currentPersonFlow      = MutableStateFlow(0)
    private val currentPersonIndex     = selectedPersonages[currentPersonFlow.value]
    private val percentFlow            = MutableStateFlow(0)

    private val btnMenu      = AButton(this, AButton.Static.Type.Back)
    private val imgCubik     = Image(game.all.cList.random())
    private val imgPersonage = Image(game.splash.magList[currentPersonIndex])
    private val imgBackCard  = Image(game.all.backCardList[currentPersonIndex])
    private val imgElement   = Image(game.all.elementList[currentPersonIndex])
    private val lblPercent   = Label("", ls80)

    // Field
    private val playerDataList = List(selectedPersonages.size) { PlayerData(0, getRandom5Card(ACard.Static.Type.entries[selectedPersonages[it]])) }

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgCubik()
                addImgPersonage()
                addImgBackCard()
                addImgElement()
                addLblPercent()
                addCards()

                showCard()
            }
            launch { collectPercentFlow() }
            launch { collectCurrentPlayerFlow() }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(27f,1703f,186f,186f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgCubik() {
        addActor(imgCubik)
        imgCubik.setBounds(49f,1525f,143f,160f)

        imgCubik.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(CubikScreen::class.java.name, GameScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgPersonage() {
        addActor(imgPersonage)
        imgPersonage.setBounds(340f,1278f,400f,400f)
        imgPersonage.apply {
            setOrigin(200f,0f)
            val scale = 0.15f
            addAction(
                Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-scale, -scale, 0.45f, Interpolation.sineIn),
                    Actions.scaleBy(scale, scale, 0.45f, Interpolation.sineOut),
                )
            ))
        }
    }

    private fun AdvancedStage.addImgBackCard() {
        addActor(imgBackCard)
        imgBackCard.setBounds(888f,1561f,153f,248f)
    }

    private fun AdvancedStage.addImgElement() {
        addActor(imgElement)
        imgElement.setBounds(330f,1732f,123f,123f)
    }

    private fun AdvancedStage.addLblPercent() {
        addActor(lblPercent)
        lblPercent.setBounds(522f,1764f,191f,56f)
    }

    private fun AdvancedStage.addCards() {
        playerDataList.onEach { pData ->
            val positionList = getPositionListByCardsSize(pData.cards.size)
            val angleList    = getAngleListByCardsSize(pData.cards.size)

            pData.cards.onEachIndexed { index, aCard ->
                addActor(aCard)
                aCard.setPosition(positionList[index].x, positionList[index].y)
                aCard.rotation = angleList[index]
                aCard.y = -600f

                aCard.setOnClickListener {
                    game.soundUtil.apply { play(card, 0.7f) }


                    pData.cards.onEach { it.disable() }
                    aCard.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.scaleTo(1.46f, 1.46f, 0.8f, Interpolation.swing),
                            Actions.moveTo(394f, 643f, 0.8f, Interpolation.swing),
                            Actions.rotateTo(0f, 0.8f, Interpolation.swing)
                        ),
                        Actions.run {
                            pData.percent    += aCard.valueX
                            percentFlow.value = pData.percent

                            aCard.animHide(TIME_ANIM) {
                                aCard.addAction(Actions.removeActor())
                                pData.cards.remove(aCard)

                                if (playerDataList.all { it.cards.size == 0 }) {
                                    val winner      = playerDataList.maxBy { it.percent }
                                    val winnerIndex = playerDataList.indexOf(winner)
                                    SuperMagScreen.SUPER_MAG_INDEX = selectedPersonages[winnerIndex]

                                    stageUI.root.animHide(TIME_ANIM) {
                                        game.navigationManager.navigate(SuperMagScreen::class.java.name)
                                    }
                                } else {
                                    hideCard()
                                    if (currentPersonFlow.value + 1 <= selectedPersonages.lastIndex) currentPersonFlow.value++ else currentPersonFlow.value = 0
                                }
                            }
                        }
                    ))
                }
            }

        }
    }

    // Logic ------------------------------------------------------------------------

    private fun getAngleListByCardsSize(size: Int): List<Float> = when (size) {
        5 -> listOf(0f,15f,-15f,30f,-30f)
        4 -> listOf(3f,-3f,20f,-20f)
        3 -> listOf(0f,12f,-12f)
        2 -> listOf(15f,-15f)
        1 -> listOf(0f)

        else -> listOf(0f)
    }

    private fun getPositionListByCardsSize(size: Int): List<Vector2> = when (size) {
        5 -> listOf(
            Vector2(394f, -56f),
            Vector2(213f, -87f),
            Vector2(570f, -87f),
            Vector2(51f, -157f),
            Vector2(728f, -157f),
        )

        4 -> listOf(
            Vector2(259f,-87f),
            Vector2(528f, -87f),
            Vector2(74f,-157f),
            Vector2(713f, -157f),
        )

        3 -> listOf(
            Vector2(394f, -87f),
            Vector2(150f, -157f),
            Vector2(638f, -157f),
        )

        2 -> listOf(
            Vector2(280f, -87f),
            Vector2(506f, -87f),
        )

        1 -> listOf(
            Vector2(394f, -37f),
        )

        else -> listOf(Vector2())
    }

    private fun getRandom5Card(type: ACard.Static.Type): MutableList<ACard> = MutableList<ACard>(5) { ACard(this, type, ls25, ls25Black, ls17) }

    private suspend fun collectPercentFlow() {
        percentFlow.collect { percent ->
            runGDX { lblPercent.setText("$percent%") }
        }
    }

    private suspend fun collectCurrentPlayerFlow() {
        currentPersonFlow.collectIndexed { index, currentIndex ->
            var indexElement = selectedPersonages[currentIndex]
            if (index==0) return@collectIndexed
            runGDX {
                imgPersonage.animHide(TIME_ANIM) {
                    imgPersonage.drawable = TextureRegionDrawable(game.splash.magList[indexElement])
                    imgPersonage.animShow(TIME_ANIM)
                }
                imgBackCard.animHide(TIME_ANIM) {
                    imgBackCard.drawable = TextureRegionDrawable(game.all.backCardList[indexElement])
                    imgBackCard.animShow(TIME_ANIM)
                }
                imgElement.animHide(TIME_ANIM) {
                    imgElement.drawable = TextureRegionDrawable(game.all.elementList[indexElement])
                    imgElement.animShow(TIME_ANIM)
                }
                showCard()
            }
        }
    }

    private fun showCard() {
        percentFlow.value = playerDataList[currentPersonFlow.value].percent

        playerDataList[currentPersonFlow.value].cards.also { cards ->
            val positionList = getPositionListByCardsSize(cards.size)
            val angleList    = getAngleListByCardsSize(cards.size)

            cards.onEachIndexed { index, card ->
                card.enable()
                card.rotation = angleList[index]
                card.addAction(Actions.moveTo(positionList[index].x, positionList[index].y, TIME_ANIM, Interpolation.sineOut))
            }
        }
    }

    private fun hideCard() {
        playerDataList[currentPersonFlow.value].cards.onEach { card ->
            card.addAction(Actions.moveBy(0f, -700f, TIME_ANIM, Interpolation.swingIn))
        }
    }


    data class PlayerData(var percent: Int, val cards: MutableList<ACard>)

}