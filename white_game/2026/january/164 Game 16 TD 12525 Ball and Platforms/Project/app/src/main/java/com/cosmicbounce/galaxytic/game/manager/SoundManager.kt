package com.cosmicbounce.galaxytic.game.manager

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
        click_mouse (SoundData("sound/click_mouse.mp3")),
        hit_a_tree_1(SoundData("sound/hit_a_tree_1.mp3")),
        hit_a_tree_2(SoundData("sound/hit_a_tree_2.mp3")),
        hit_a_tree_3(SoundData("sound/hit_a_tree_3.mp3")),
        jump        (SoundData("sound/jump.mp3")),
        win_in_game (SoundData("sound/win_in_game.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}