package com.veldan.cosmolot.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music

object MusicManager {

    var loadableMusicList = mutableListOf<IMusic>()

    fun load(assetManager: AssetManager) {
        loadableMusicList.onEach { assetManager.load(it.data.path, Music::class.java) }
    }

    fun init(assetManager: AssetManager) {
        loadableMusicList.onEach { it.data.music = assetManager[it.data.path, Music::class.java] }
    }



    enum class EnumMusic(override val data: MusicData): IMusic {
        GAME_1(MusicData("music/game_1.ogg")),
        GAME_2(MusicData("music/game_2.ogg")),
        GAME_3(MusicData("music/game_3.ogg")),
    }



    interface IMusic {
        val data: MusicData
    }

    data class MusicData(
        val path: String,
    ) {
        lateinit var music: Music
    }

}