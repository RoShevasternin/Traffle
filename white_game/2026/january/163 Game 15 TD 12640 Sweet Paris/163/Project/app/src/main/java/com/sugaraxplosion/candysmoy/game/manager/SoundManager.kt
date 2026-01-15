package com.sugaraxplosion.candysmoy.game.manager

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
        childs_joy         (SoundData("sound/childs_joy.mp3")),
        click              (SoundData("sound/click.mp3")),
        item               (SoundData("sound/item.mp3")),
        plus               (SoundData("sound/plus.mp3")),
        soft_explosion_bomb(SoundData("sound/soft_explosion_bomb.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}