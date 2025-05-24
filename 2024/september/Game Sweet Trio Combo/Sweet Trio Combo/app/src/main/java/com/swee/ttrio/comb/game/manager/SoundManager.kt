package com.swee.ttrio.comb.game.manager

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
        card          (SoundData("sound/card.mp3")),
        click_game    (SoundData("sound/click_game.mp3")),
        gray          (SoundData("sound/gray.mp3")),
        select        (SoundData("sound/select.mp3")),
        super_win_game(SoundData("sound/super_win_game.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}