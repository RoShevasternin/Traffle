package com.veldan.cosmolot.game.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.veldan.cosmolot.game.manager.AudioManager
import com.veldan.cosmolot.game.manager.MusicManager
import com.veldan.cosmolot.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val GAME_1 get() = MusicManager.EnumMusic.GAME_1.data.music
    val GAME_2 get() = MusicManager.EnumMusic.GAME_2.data.music
    val GAME_3 get() = MusicManager.EnumMusic.GAME_3.data.music

    val musicList get() = listOf(GAME_1, GAME_2, GAME_3)

    val volumeLevel = MutableStateFlow(AudioManager.volumeLevel_100)

    var isPause = false

    private var previousMusic: Music? = null
    var currentMusic: Music?
        get() = previousMusic
        set(value) {
            runGDX {
                if (previousMusic != value) {
                    previousMusic?.stop()
                    previousMusic = value
                    previousMusic?.play()
                    previousMusic?.isLooping = true
                }
            }
        }



    init {
        collectVolumeLevel()
    }



    override fun dispose() {
        previousMusic = null
        cancelCoroutinesAll(coroutine)
    }

    @JvmName("setPause1")
    fun setPause(value: Boolean) {
        isPause = value
        if (value) currentMusic?.pause() else currentMusic?.play()
    }

    private fun collectVolumeLevel() {
        var volume: Float

        coroutine.launch { volumeLevel.collect { level ->
            volume = (level / 100f)
            setPause(volume < 0f)
            musicList.onEach { music -> music.volume = volume }
        } }
    }

}