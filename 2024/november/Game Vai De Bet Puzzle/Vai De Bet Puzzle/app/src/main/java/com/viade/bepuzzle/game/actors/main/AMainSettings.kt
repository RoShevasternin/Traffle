package com.viade.bepuzzle.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.viade.bepuzzle.game.actors.ACircles
import com.viade.bepuzzle.game.actors.button.AButton
import com.viade.bepuzzle.game.actors.checkbox.ACheckBox
import com.viade.bepuzzle.game.actors.checkbox.ACheckBoxGroup
import com.viade.bepuzzle.game.utils.Block
import com.viade.bepuzzle.game.utils.TIME_ANIM_SCREEN
import com.viade.bepuzzle.game.utils.actor.animHideSuspend
import com.viade.bepuzzle.game.utils.actor.animShowSuspend
import com.viade.bepuzzle.game.utils.advanced.AdvancedGroup
import com.viade.bepuzzle.game.utils.advanced.AdvancedScreen
import com.viade.bepuzzle.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var IS_VIBRO = true
            private set
    }

    private val aCircles = ACircles(screen)

    private val imgSettings = Image(screen.game.assetsAll.settings)
    private val listBox     = List(3) { ACheckBox(screen, ACheckBox.Type.CHECK) }
    //private val btnPrivacy  = AButton(screen, AButton.Type.Privacy)
    //private val btnWeb      = AButton(screen, AButton.Type.Web)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(aCircles)
                addImgPanelSettings()
                addBoxs()
                //addBtnPrivacy()
                //addBtnWeb()

                children.onEach { it.color.a = 0f }
            }

            delay(100)
            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgPanelSettings() {
        addActor(imgSettings)
        imgSettings.setBounds(0f, 471f, 1080f, 840f)
    }

    private fun addBoxs() {
        var ny = 1127f
        listBox.onEach { aCheckBox ->
            addActor(aCheckBox)

            aCheckBox.setBounds(650f, ny, 110f, 110f)
            ny -= (128 + 110)
        }

        // Music
        listBox[0].apply {
            if (screen.game.musicUtil.music?.isPlaying == true) check(false)
            setOnCheckListener { isCheck ->
                if (isCheck) {
                    screen.game.musicUtil.music?.play()
                } else {
                    screen.game.musicUtil.music?.pause()
                }
            }
        }
        // Sound
        listBox[1].apply {
            if (screen.game.soundUtil.isPause.not()) check(false)
            setOnCheckListener { isCheck ->
                screen.game.soundUtil.isPause = isCheck.not()
            }
        }
        // Vibro
        listBox[2].apply {
            if (IS_VIBRO) check(false)
            setOnCheckListener { isCheck ->
                IS_VIBRO = isCheck
            }
        }
    }

//    private fun addBtnPrivacy() {
//        addActor(btnPrivacy)
//        btnPrivacy.setBounds(93f, 23f, 378f, 140f)
//        btnPrivacy.setOnClickListener {
//            // todo: open Privacy
//        }
//    }
//
//    private fun addBtnWeb() {
//        addActor(btnWeb)
//        btnWeb.setBounds(632f, 23f, 378f, 140f)
//        btnWeb.setOnClickListener {
//            // todo: open Web
//        }
//    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            children.onEach {
                it.animShowSuspend(0.4f)
            }
        }
    }

    // Anim ------------------------------------------------

    suspend fun animHideMain(block: Block = Block {  }) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}