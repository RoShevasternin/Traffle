package com.toyscatcher.factoryatoy.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.toyscatcher.factoryatoy.HEIGHT
import com.toyscatcher.factoryatoy.VOLUME_TAP_MOVE
import com.toyscatcher.factoryatoy.WIDTH
import com.toyscatcher.factoryatoy.actors.Ball
import com.toyscatcher.factoryatoy.actors.ButtonClickable
import com.toyscatcher.factoryatoy.actors.Start
import com.toyscatcher.factoryatoy.actors.Tap
import com.toyscatcher.factoryatoy.advanced.AdvancedScreen
import com.toyscatcher.factoryatoy.assets.FontManager
import com.toyscatcher.factoryatoy.assets.SpriteManager
import com.toyscatcher.factoryatoy.listeners.toClickable
import com.toyscatcher.factoryatoy.listeners.toHoldeble
import com.toyscatcher.factoryatoy.utils.*
import kotlinx.coroutines.*
import kotlin.random.Random

class GameScreen : AdvancedScreen() {
    override val viewport = FitViewport(WIDTH, HEIGHT)

    private val toys = mutableListOf(
        Toy(0, SpriteManager.toyList[0], 100, 5),
        Toy(1, SpriteManager.toyList[1], 200, 7),
        Toy(2, SpriteManager.toyList[2], 300, 10),
        Toy(3, SpriteManager.toyList[3], 400, 12),
        Toy(4, SpriteManager.toyList[4], 500, 15),
        Toy(5, SpriteManager.toyList[5], 1000, 20),
    )

    private val coroutineDotToyX = CoroutineScope(Dispatchers.Main)
    private val coroutineStartGame = CoroutineScope(Dispatchers.Main)
    private val coroutineTapUp = CoroutineScope(Dispatchers.Main)
    private val coroutineSearchToy = CoroutineScope(Dispatchers.Main)

    private val tap = Tap()
    private val start = Start()
    private val left = ButtonClickable()
    private val right = ButtonClickable()
    private val ball = Ball()
    private val gameover = Image(SpriteManager.game_over)
    private val time = Label("60", Label.LabelStyle(FontManager.font, null))
    private val money = Label("0", Label.LabelStyle(FontManager.font, null))
    private val timeShowText= Label("", Label.LabelStyle(FontManager.font, null))
    private val moneyShowText = Label("", Label.LabelStyle(FontManager.font, null))
    private val moneyGameOver = Label("", Label.LabelStyle(FontManager.font, null))
    private val timeGameOver = Label("", Label.LabelStyle(FontManager.font, null))

    private var currentDotToyX = 0f
    private var currentToy = generateToy()
    private var isToyCatch = false
    private var timer = Timer().apply { doAfterZero = { doAfterTimeZero() } }
    private var isGameOver = false



    override fun show() {
        super.show()
        stage.addActors(getActors())
        startGame()
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK) NavigationUtil.back()
        return false
    }

    override fun dispose() {
        super.dispose()
        disposeAll(timer)
        cancelCoroutinesAll(coroutineDotToyX, coroutineStartGame, coroutineTapUp, coroutineSearchToy)
    }



    override fun render(delta: Float) {
        super.render(delta)
        if (Gdx.input.justTouched()) restart()
    }



    private fun getActors() = listOf<Actor>(
        setUpTap(),
        setUpBox(),
        setUpToys(),
        setUpDotToy(),
        setUpMask(),
        setUpBall(),
        setUpLeft(),
        setUpRight(),
        setUpMoney(),
        setUpMoneyText(),
        setUpTime(),
        setUpTimeText(),
        setUpShowTimeText(),
        setUpShowMoneyText(),
        setUpStart(),
        setUpGameOver(),
        setUpTimeGameOver(),
        setUpMoneyGameOver(),
    )



    private fun setUpMask() = Image(SpriteManager.mask).apply {
        setBoundsFigmaY(MASK_TOP_X, MASK_TOP_Y, MASK_TOP_W, MASK_TOP_H)
    }

    private fun setUpTimeGameOver() = timeGameOver.apply {
        isVisible = false
        setAlignment(Align.center)
        setBoundsFigmaY(TIME_GAME_OVER_X, TIME_GAME_OVER_Y, TIME_GAME_OVER_W, TIME_GAME_OVER_H)
    }

    private fun setUpMoneyGameOver() = moneyGameOver.apply {
        isVisible = false
        setAlignment(Align.center)
        setBoundsFigmaY(MONEY_GAME_OVER_X, MONEY_GAME_OVER_Y, MONEY_GAME_OVER_W, MONEY_GAME_OVER_H)
    }

    private fun setUpGameOver() = gameover.apply {
        isVisible = false
        setBoundsFigmaY(GAME_OVER_X, GAME_OVER_Y, GAME_OVER_W, GAME_OVER_H)
    }

    private fun setUpBox() = Image(SpriteManager.box).apply {
        setBoundsFigmaY(BOX_X, BOX_Y, BOX_W, BOX_H)
    }

    private fun setUpShowTimeText() = timeShowText.apply {
        setAlignment(Align.center)
        setBoundsFigmaY(TIME_SHOW_TEXT_X, TIME_SHOW_TEXT_Y, TIME_SHOW_TEXT_W, TIME_SHOW_TEXT_H)
    }

    private fun setUpShowMoneyText() = moneyShowText.apply {
        setAlignment(Align.center)
        setBoundsFigmaY(MONEY_SHOW_TEXT_X, MONEY_SHOW_TEXT_Y, MONEY_SHOW_TEXT_W, MONEY_SHOW_TEXT_H)
    }

    private fun setUpMoney() = Image(SpriteManager.money).apply {
        setBoundsFigmaY(MONEY_X, MONEY_Y, MONEY_W, MONEY_H)
    }

    private fun setUpMoneyText() = money.apply {
        setAlignment(Align.center)
        setBoundsFigmaY(MONEY_TEXT_X, MONEY_TEXT_Y, MONEY_TEXT_W, MONEY_TEXT_H)
    }

    private fun setUpTime() = Image(SpriteManager.time).apply {
        setBoundsFigmaY(TIME_X, TIME_Y, TIME_W, TIME_H)
    }

    private fun setUpTimeText() = time.apply {
        timer.listener = { setText(it) }
        setAlignment(Align.center)
        setBoundsFigmaY(TIME_TEXT_X, TIME_TEXT_Y, TIME_TEXT_W, TIME_TEXT_H)
    }

    private fun setUpToys() = Image(SpriteManager.toys).apply {
        setBoundsFigmaY(TOYS_X, TOYS_Y, TOYS_W, TOYS_H)
    }

    private fun setUpBall() = ball.apply {
        setBoundsFigmaY(BALL_X, BALL_Y, BALL_W, BALL_H)
        toClickable().setOnClickListener { ballHandler() }
    }

    private fun setUpLeft() = left.apply {
        setStyle(ButtonClickable.Style(
             default = SpriteManager.left_default,
             pressed = SpriteManager.left_pressed,
        ))
        setBoundsFigmaY(LEFT_X, LEFT_Y, LEFT_W, LEFT_H)
        toHoldeble().setOnHoldListener(
            blockInside = { leftHandler(true) },
            blockOutside = { leftHandler(false) },
        )
    }

    private fun setUpRight() = right.apply {
        setStyle(ButtonClickable.Style(
            default = SpriteManager.right_default,
            pressed = SpriteManager.right_pressed,
        ))
        setBoundsFigmaY(RIGHT_X, RIGHT_Y, RIGHT_W, RIGHT_H)
        toHoldeble().setOnHoldListener(
            blockInside = { rightHandler(true) },
            blockOutside = { rightHandler(false) },
        )
    }

    private fun setUpDotToy() = Image(SpriteManager.none).apply {
        fun getRandomDotToyX() = Random.nextInt(DOT_TOY_MAX_LEFT, DOT_TOY_MAX_RIGHT).toFloat()

        setBoundsFigmaY(getRandomDotToyX(), DOT_TOY_Y, DOT_TOY_W, DOT_TOY_H)
        coroutineDotToyX.launch {
            while (true) {
                delay(500)
                x = getRandomDotToyX()
                currentDotToyX = x
                delay(1000)
                currentToy = generateToy()
            }
        }
    }

    private fun setUpTap() = tap.apply {
        setBoundsFigmaY(TAP_X, TAP_Y, TAP_W, TAP_H)
    }

    private fun setUpStart() = start.apply {
        setBoundsFigmaY(START_X, START_Y, START_W, START_H)
    }



    private fun leftHandler(isInside: Boolean) {
        right.disable()
        if (isInside) with(tap) { x -= if (x > TAP_MIN_X) 1 else 0 }
        else right.enable()
    }

    private fun rightHandler(isInside: Boolean) {
        left.disable()
        if (isInside) with(tap) { x += if (x < TAP_MAX_X) 1 else 0 }
        else left.enable()
    }

    private fun ballHandler() {
        if (ball.isEnable) {
            ball.disable()

            if (ball.isWin) tap.setToyById(currentToy.id)

            coroutineTapUp.launch {
                tap.up(doOnEnd = {
                    if(ball.isWin) reward()
                    tap.down()
                    delay(2500)
                    ball.enable()
                })
            }
        }
    }



    private fun startGame() {
        coroutineStartGame.launch {
            start.start()
            timer.go()
            tap.down()
            delay(2500)
            ball.enable()
        }
        searchToy()
    }



    private fun searchToy() {
        coroutineSearchToy.launch {
            while (true) {
                delay(50)
                if (tap.isDown) {
                    isToyCatch = (currentDotToyX in tap.x..(tap.x + tap.width))
                    if (isToyCatch) ball.setToyRegion(currentToy.region)
                    else ball.setToyRegion(null)
                }else ball.setToyRegion(null)
            }
        }
    }



    private fun generateToy() = toys.random()



    private fun doAfterTimeZero() {
        isGameOver = true
        gameover.isVisible = true
        timeGameOver.apply {
            setText(gameTime)
            isVisible = true
        }
        moneyGameOver.apply {
            setText(money.text)
            isVisible = true
        }
    }



    private suspend fun reward(){
        timeShowText.setText("+${currentToy.time}")
        moneyShowText.setText("+${currentToy.money}")
        timer.time += currentToy.time
        money.apply { setText(text.toString().toInt()+currentToy.money) }
        delay(1000)
        timeShowText.setText("")
        moneyShowText.setText("")
    }



    private fun restart(){
        if (isGameOver) {
            gameTime = 0
            NavigationUtil.navigate(GameScreen())
        }
    }

}