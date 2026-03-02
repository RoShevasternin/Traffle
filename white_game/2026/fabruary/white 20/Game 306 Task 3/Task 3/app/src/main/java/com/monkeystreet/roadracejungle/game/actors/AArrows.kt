package com.monkeystreet.roadracejungle.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedGroup
import com.monkeystreet.roadracejungle.game.utils.advanced.AdvancedScreen
import com.monkeystreet.roadracejungle.game.utils.gdxGame

class AArrows(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listImgArrow = List(3) { Image(gdxGame.assetsAll.ARROW) }

    override fun addActorsOnGroup() {
        addImgArrow()

        startAnimation()
    }

    private fun addImgArrow() {
        var ny = 0f
        listImgArrow.forEach {
            addActor(it)
            it.setBounds(0f, ny, 36f, 76f)
            ny += 20 + 76
        }
    }

    private fun startAnimation() {
        listImgArrow.forEachIndexed { index, img ->
            // Створюємо послідовність дій
            val sequence = Actions.sequence(
                // 1. Затримка, щоб стрілки рухалися одна за одною
                Actions.delay(index * 0.2f),
                // 2. Постійне повторення
                Actions.forever(
                    Actions.sequence(
                        // Збільшення з одночасним висвітленням
                        Actions.parallel(
                            Actions.scaleTo(1.2f, 1.2f, 0.3f),
                            Actions.alpha(1f, 0.3f)
                        ),
                        // Зменшення до норми
                        Actions.parallel(
                            Actions.scaleTo(1.0f, 1.0f, 0.3f),
                            Actions.alpha(0.5f, 0.3f)
                        ),
                        // Пауза перед наступним циклом хвилі
                        Actions.delay(0.4f)
                    )
                )
            )
            img.addAction(sequence)
        }
    }

}