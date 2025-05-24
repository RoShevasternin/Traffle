package com.viade.bepuzzle.game.manager

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
        click          (SoundData("sound/click.mp3")),
        buy            (SoundData("sound/buy.mp3")),
        fail           (SoundData("sound/fail.mp3")),
        fail_buy       (SoundData("sound/fail_buy.mp3")),
        fail_use_bonus (SoundData("sound/fail_use_bonus.mp3")),
        puzzle_click   (SoundData("sound/puzzle_click.mp3")),
        use_bonus      (SoundData("sound/use_bonus.mp3")),
        win            (SoundData("sound/win.mp3")),

    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}