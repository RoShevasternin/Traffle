package com.monkeystreet.roadracejungle.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.monkeystreet.roadracejungle.game.utils.actor.addAndFillActor
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedGroup
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class AEggPanelB(override val screen: AdvancedScreen): AdvancedGroup() {

    // Список текстур для цифр від 1 до 6
    private val eggTextures = gdxGame.assetsAll.listSett
    private val listIndex = (0..eggTextures.lastIndex)

    private val imgPanel = Image(gdxGame.assetsAll.CIRCLE_B)
    private val imgEgg = Image(eggTextures.first())

    private var randomIndex = 0

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addImgEgg()
    }

    private fun addImgEgg() {
        addActor(imgEgg)
        imgEgg.setBounds(147f, 146f, 187f, 187f)
        imgEgg.setOrigin(Align.center) // Важливо для обертання або масштабування
    }

    fun shakeEgg(endBlock: (Int) -> Unit) {
        // 1. Очищуємо попередні дії, якщо вони є
        imgEgg.clearActions()

        randomIndex = listIndex.random()

        // Початкова позиція
        val startX = 147f
        val duration = 2f
        val shakeIntensity = 5f // Сила трясіння в пікселях

        // 2. Анімація трясіння (Shake Action)
        val shakeAction = Actions.repeat(
            20, Actions.sequence(
                Actions.moveTo(startX - shakeIntensity, imgEgg.y, 0.05f),
                Actions.moveTo(startX + shakeIntensity, imgEgg.y, 0.05f)
            )
        )

        // 3. Анімація зміни цифр (Runnable Action)
        // Змінюємо текстуру кожні 0.3 секунди протягом 2 секунд
        val changeTextureAction = Actions.repeat(
            6, Actions.sequence(
            Actions.delay(0.3f),
            Actions.run {
                val randomTexture = eggTextures.random()
                imgEgg.drawable = TextureRegionDrawable(randomTexture)
            }
        ))

        // 4. Повернення в центр після завершення
        val resetAction = Actions.moveTo(startX, imgEgg.y, 0.1f)

        // Запускаємо все паралельно
        imgEgg.addAction(
            Actions.sequence(
                Actions.parallel(shakeAction, changeTextureAction),
                resetAction,
                Actions.run {
                    imgEgg.drawable = TextureRegionDrawable(eggTextures[randomIndex])
                    endBlock(randomIndex + 1)
                }
            )
        )
    }


}