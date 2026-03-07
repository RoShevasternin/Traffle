package com.fushflyacensee.adventcoral.game.actors

import com.fushflyacensee.adventcoral.game.utils.actor.addAndFillActor
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedGroup
import com.fushflyacensee.adventcoral.game.utils.advanced.AdvancedScreen
import com.fushflyacensee.adventcoral.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class AVolume(override val screen: AdvancedScreen): AdvancedGroup() {

    private val listVolumeImg = gdxGame.assetsAll.listSett
    private val max = listVolumeImg.lastIndex

    private val aVolumeImg = Image()

    private var currentLevel = 0

    override fun addActorsOnGroup() {
        addAndFillActor(aVolumeImg)
        setLevel(0) // стартове значення
    }

    // 🔥 Основний метод
    fun setLevel(level: Int) {
        currentLevel = level.coerceIn(0, max)
        aVolumeImg.drawable = TextureRegionDrawable(listVolumeImg[currentLevel])
    }

    fun increase() {
        setLevel(currentLevel + 1)
    }

    fun decrease() {
        setLevel(currentLevel - 1)
    }

    fun getLevel(): Int = currentLevel

    // 🔥 ОТ ТУТ ГОЛОВНЕ
    fun getPercent(): Int {
        if (max == 0) return 0
        return ((currentLevel.toFloat() / max.toFloat()) * 100f).toInt()
    }

    fun setPercent(percent: Int) {
        val clamped = percent.coerceIn(0, 100)
        val level = ((clamped / 100f) * max).toInt()
        setLevel(level)
    }

}