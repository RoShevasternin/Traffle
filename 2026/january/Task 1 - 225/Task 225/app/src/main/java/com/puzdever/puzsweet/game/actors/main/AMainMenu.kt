package com.puzdever.puzsweet.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzdever.puzsweet.game.actors.button.AButton
import com.puzdever.puzsweet.game.actors.checkbox.ACheckBox
import com.puzdever.puzsweet.game.screens.PuzzleScreen
import com.puzdever.puzsweet.game.screens.MenuScreen
import com.puzdever.puzsweet.game.screens.RulesScreen
import com.puzdever.puzsweet.game.utils.Acts
import com.puzdever.puzsweet.game.utils.Block
import com.puzdever.puzsweet.game.utils.TIME_ANIM_SCREEN
import com.puzdever.puzsweet.game.utils.actor.animDelay
import com.puzdever.puzsweet.game.utils.actor.animHide
import com.puzdever.puzsweet.game.utils.actor.animMoveTo
import com.puzdever.puzsweet.game.utils.actor.animShow
import com.puzdever.puzsweet.game.utils.actor.setOnClickListener
import com.puzdever.puzsweet.game.utils.advanced.AdvancedMainGroup
import com.puzdever.puzsweet.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val listBtnType = listOf(AButton.Type.Play, AButton.Type.Rules)

    private val imgMenu    = Image(gdxGame.assetsAll.MENU_PAN)
    private val listBtn    = List(2) { AButton(screen, listBtnType[it]) }
    private val music      = ACheckBox(screen, ACheckBox.Type.Music)
    private val sound      = ACheckBox(screen, ACheckBox.Type.Sound)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        addMusic()
        addSound()

        animShowMain {
            music.animMoveTo(music.x, 1728f, 0.5f)
            sound.animMoveTo(sound.x, 1728f, 0.5f)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(67f, 376f, 947f, 1169f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(220f, 1066f, 640f, 189f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(PuzzleScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(220f, 819f, 640f, 189f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(220f, 572f, 640f, 189f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        //gdxGame.activity.webViewHelper.loadUrl("file:///android_asset/privacy_policy.html", false)
                    }
                }
            }
        }
    }


    private fun addMusic() {
        addActor(music)
        music.setBounds(886f, 2000f, 133f, 133f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) music.check()
        music.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addSound() {
        addActor(sound)
        sound.setBounds(71f, 2000f, 133f, 133f)
        if (gdxGame.soundUtil.isPause) sound.check()
        sound.setOnCheckListener {
            gdxGame.soundUtil.isPause = it
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}