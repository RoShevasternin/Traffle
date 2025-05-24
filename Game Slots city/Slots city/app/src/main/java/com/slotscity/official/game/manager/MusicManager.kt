package com.slotscity.official.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music

class MusicManager(var assetManager: AssetManager) {

    var loadableMusicList = mutableListOf<MusicData>()

    fun load() {
        loadableMusicList.onEach { assetManager.load(it.path, Music::class.java) }
    }

    fun init() {
        loadableMusicList.onEach { it.music = assetManager[it.path, Music::class.java] }
        loadableMusicList.clear()
    }

    enum class EnumMusic(val data: MusicData) {
        MUSIC(MusicData("music/cinematic-adventure-alive.ogg")),
        SUSPECT(MusicData("music/suspect.ogg")),
    }

    enum class CarnavalCatMusic(val data: MusicData) {
        MUSIC(MusicData("music/Carnaval Cat/sakura-meditate-beat.ogg")),
    }

    enum class TreasureSnipesMusic(val data: MusicData) {
        MUSIC(MusicData("music/Treasure Snipes/orchestral-epic-adventure.ogg")),
    }

    enum class SweetBonanzaMusic(val data: MusicData) {
        MUSIC(MusicData("music/Sweet Bonanza/fun-kids-playful-comic-carefree-game-happy.ogg")),
    }

    data class MusicData(
        val path: String,
    ) {
        lateinit var music: Music
    }

}