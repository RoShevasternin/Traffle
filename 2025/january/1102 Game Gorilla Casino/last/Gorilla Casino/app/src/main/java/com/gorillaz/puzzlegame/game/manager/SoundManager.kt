package com.gorillaz.puzzlegame.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        bet           (SoundData("sound/bet.mp3")),
        click         (SoundData("sound/click.mp3")),
        coins         (SoundData("sound/coins.mp3")),
        fail          (SoundData("sound/fail.mp3")),
        spin          (SoundData("sound/spin.mp3")),
        upgrade       (SoundData("sound/upgrade.mp3")),
        win_roulette  (SoundData("sound/win_roulette.mp3")),
        win_slot      (SoundData("sound/win_slot.mp3")),
        boom          (SoundData("sound/boom.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}