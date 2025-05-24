package com.baldasari.munish.cards.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.baldasari.munish.cards.game.LibGDXGame
import com.baldasari.munish.cards.game.actors.AButton
import com.baldasari.munish.cards.game.actors.checkbox.ACheckBox
import com.baldasari.munish.cards.game.utils.TIME_ANIM
import com.baldasari.munish.cards.game.utils.actor.animHide
import com.baldasari.munish.cards.game.utils.actor.animShow
import com.baldasari.munish.cards.game.utils.actor.setOnClickListener
import com.baldasari.munish.cards.game.utils.advanced.AdvancedScreen
import com.baldasari.munish.cards.game.utils.advanced.AdvancedStage
import com.baldasari.munish.cards.game.utils.disable
import com.baldasari.munish.cards.game.utils.region
import com.baldasari.munish.cards.game.utils.runGDX
import com.baldasari.munish.cards.util.log
import kotlinx.coroutines.launch

class PlayerScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        val listPerson = mutableListOf<Int>()
    }

    private val btnMenu     = AButton(this, AButton.Static.Type.Back)
    private val imgText     = Image(game.all.pList.first())
    private val boxList     = List(4) { ACheckBox(this, ACheckBox.Static.Type.PLAYER) }
    private val magList     = List(4) { Image(game.splash.magList[it]) }

    private var checkCounter = 0

    override fun show() {
        listPerson.clear()
        stageUI.root.animHide()
        setBackBackground(game.splash.background.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addMenu()
                addImgText()
                addBoxes()
                addImgMagList()
            }
        }
    }

    private fun AdvancedStage.addMenu() {
        addActor(btnMenu)
        btnMenu.apply {
            setBounds(447f,-5f,186f,186f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addImgText() {
        addActor(imgText)
        imgText.setBounds(60f,1581f,961f,227f)
    }

    private fun AdvancedStage.addImgMagList() {
        val posList = listOf(
            Vector2(75f,958f),
            Vector2(605f,958f),
            Vector2(75f,290f),
            Vector2(605f,290f),
        )
        magList.onEachIndexed { index, image ->
            image.disable()
            addActor(image)
            image.setBounds(posList[index].x, posList[index].y, 400f, 400f)
        }
    }

    private fun AdvancedStage.addBoxes() {
        val posList = listOf(
            Vector2(17f,821f),
            Vector2(547f,821f),
            Vector2(17f,153f),
            Vector2(547f,153f),
        )
        var clickCount = 0

        boxList.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(posList[index].x, posList[index].y, 516f, 666f)
            box.isSound = false
            box.setOnCheckListener {
                if(it) {
                    game.soundUtil.apply { play(select, 0.7f) }

                    clickCount++
                    if (clickCount == SelectScreen.SELECT_COUNT) {
                        stageUI.root.animHide(TIME_ANIM) {
                            game.navigationManager.navigate(GameScreen::class.java.name, PlayerScreen::class.java.name)
                        }
                    }
                    listPerson.add(index)
                    box.disable()
                    box.animHide(1f)
                    magList[index].animHide(0.8f)

                    if (checkCounter < 3) checkCounter++
                    imgText.drawable = TextureRegionDrawable(game.all.pList[checkCounter])
                }
            }
        }
    }

}