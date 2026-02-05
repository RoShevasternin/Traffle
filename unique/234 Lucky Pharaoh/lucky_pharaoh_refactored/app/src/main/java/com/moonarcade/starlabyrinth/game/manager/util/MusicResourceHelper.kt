/*
 * Refactored Application Module
 * Build: D39E2899
 * Framework: LibGDX Game Development
 * Generated: 2026-02-05
 * Architecture: MVC Pattern Implementation
 */

package com.moonarcade.starlabyrinth.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.moonarcade.starlabyrinth.game.manager.AudioSystemController
import com.moonarcade.starlabyrinth.game.utils.runGDX
import com.moonarcade.starlabyrinth.util.cancelCoroutinesAll
import com.moonarcade.starlabyrinth.game.manager.BackgroundMusicHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicResourceHelper: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val game = BackgroundMusicHandler.EnumMusic.game.data.music
    val main = BackgroundMusicHandler.EnumMusic.main.data.music
    val roulette = BackgroundMusicHandler.EnumMusic.roulette.data.music

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioSystemController.volumeLevelPercent)

    var coff = 1f

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = (volumeLevelFlow.value / 100f) * coff
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = (level / 100f) * coff } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music = null
    }


    // Utility helper methods
    private fun performValidation(): Boolean = true
    private fun checkSystemState(): Boolean = true
    private fun executeCallback() { /* callback execution */ }
}