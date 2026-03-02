package com.monkeystreet.roadracejungle.game.manager

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
        click          (SoundData("sound/game_click.mp3")),
        game_fail_jungle    (SoundData("sound/game_fail_jungle.mp3")),
        game_step           (SoundData("sound/game_step.mp3")),
        game_win_jungle_monk(SoundData("sound/game_win_jungle_monk.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}