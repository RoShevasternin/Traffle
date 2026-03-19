package com.vortemika208.w1n.game.actors.roulette

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.vortemika208.w1n.game.utils.AlignH
import com.vortemika208.w1n.game.utils.AlignV
import com.vortemika208.w1n.game.utils.GameColor
import com.vortemika208.w1n.game.utils.actor.addActorAligned
import com.vortemika208.w1n.game.utils.actor.addAndFillActor
import com.vortemika208.w1n.game.utils.actor.animHide
import com.vortemika208.w1n.game.utils.actor.animShow
import com.vortemika208.w1n.game.utils.actor.disable
import com.vortemika208.w1n.game.utils.actor.enable
import com.vortemika208.w1n.game.utils.actor.setOnClickListener
import com.vortemika208.w1n.game.utils.advanced.AdvancedGroup
import com.vortemika208.w1n.game.utils.advanced.AdvancedScreen
import com.vortemika208.w1n.game.utils.gdxGame

class APanelRoulette(override val screen: AdvancedScreen): AdvancedGroup() {

    enum class State {
        SHOW, HIDE,
        GAME_ROULETTE,
        RESULT_TRY_AGAIN, RESULT_WIN,
    }

    private val aDimImg         = Image(screen.drawerUtil.getTexture(GameColor.black_77))
    private val aTryRoulette    = ATryRoulette(screen)
    private val aGameRoulette   = AGameRoulette(screen)
    private val aResultTryAgain = AResultTryAgain(screen)
    private val aResultWin      = AResultWin(screen)

    // ------------------------------------------------------------------------
    // State
    // ------------------------------------------------------------------------
    private var state = State.HIDE

    override fun addActorsOnGroup() {
        addAndFillActor(aDimImg)
        addTryRoulette()
        addGameRoulette()

        addResultTryAgain()
        addResultWin()

        aDimImg.setOnClickListener { setState(State.HIDE) }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addTryRoulette() {
        aTryRoulette.apply {
            color.a = 0f
            disable()
        }
        aTryRoulette.setSize(1265f, 796f)
        addActorAligned(aTryRoulette, AlignH.CENTER, AlignV.CENTER)

        aTryRoulette.blockClose = { setState(State.HIDE) }
        aTryRoulette.blockStart = { setState(State.GAME_ROULETTE) }
    }

    private fun addGameRoulette() {
        aGameRoulette.apply {
            color.a = 0f
            disable()
        }
        aGameRoulette.setSize(1372f, 836f)
        addActorAligned(aGameRoulette, AlignH.CENTER, AlignV.CENTER)

        aGameRoulette.blockResult = { result ->
            aGameRoulette.animHideGameRoulette()
            when(result) {
                ARoulette.Result.TRY_AGAIN -> {
                    setState(State.RESULT_TRY_AGAIN)
                }
                else -> {
                    AResultWin.WIN_COIN_FLOW.value = result.sum
                    gdxGame.modelPlayer.addCoin(result.sum)
                    setState(State.RESULT_WIN)
                }
            }
        }
        aGameRoulette.blockClose = {
            setState(State.SHOW)
        }
    }

    private fun addResultTryAgain() {
        aResultTryAgain.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aResultTryAgain)

        aResultTryAgain.blockTryAgain = {
            setState(State.GAME_ROULETTE)
        }
        aResultTryAgain.blockClose = {
            setState(State.SHOW)
        }
    }

    private fun addResultWin() {
        aResultWin.apply {
            color.a = 0f
            disable()
        }
        addAndFillActor(aResultWin)

        aResultWin.blockClose = {
            setState(State.SHOW)
        }
    }

    // ------------------------------------------------------------------------
    // setState
    // ------------------------------------------------------------------------
    fun setState(newState: State) {
        state = newState
        val animDur = 0.25f

        // Спочатку ховаємо все
        aTryRoulette.animHide(animDur);    aTryRoulette.disable()
        aGameRoulette.animHide(animDur);   aGameRoulette.disable()
        aResultTryAgain.animHide(animDur); aResultTryAgain.disable()
        aResultWin.animHide(animDur);      aResultWin.disable()

        when (newState) {
            State.SHOW -> {
                animShowPanelRoulette()
                aTryRoulette.animShowTryRoulette()
            }
            State.HIDE -> {
                animHidePanelRoulette()
            }
            State.GAME_ROULETTE -> {
                aGameRoulette.animShowGameRoulette()
            }
            State.RESULT_TRY_AGAIN -> {
                gdxGame.soundUtil.apply { play(roulette_fail) }
                aResultTryAgain.animShowResultTryAgain()
            }
            State.RESULT_WIN -> {
                gdxGame.soundUtil.apply { play(roulette_win) }
                aResultWin.animShowResultWin()
            }
        }
    }

    // ------------------------------------------------------------------------
    // Animations
    // ------------------------------------------------------------------------

    private fun animShowPanelRoulette() {
        clearActions()
        enable()
        animShow(0.25f)
    }

    private fun animHidePanelRoulette() {
        clearActions()
        disable()
        animHide(0.25f)
    }

}