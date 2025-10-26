package com.castlesmash.catapultclash.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.castlesmash.catapultclash.game.LibGDXGame
import com.castlesmash.catapultclash.game.actors.checkbox.ACheckBox
import com.castlesmash.catapultclash.game.utils.TIME_ANIM
import com.castlesmash.catapultclash.game.utils.actor.animHide
import com.castlesmash.catapultclash.game.utils.actor.animShow
import com.castlesmash.catapultclash.game.utils.actor.setOnClickListener
import com.castlesmash.catapultclash.game.utils.advanced.AdvancedScreen
import com.castlesmash.catapultclash.game.utils.advanced.AdvancedStage
import com.castlesmash.catapultclash.game.utils.region

class FirstScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val casImg  = Image(game.allAssets.menu)

    override fun show() {
        stageUI.root.animHide()
        setBackgrounds(game.startAssets.CBACA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(casImg)
        casImg.setBounds(681f, 407f, 557f, 365f)


        var ny = 609f

        val scrName = listOf(
            CatapultaMenuScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        val actors = listOf(Actor(), Actor())
        actors.forEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(681f, ny, 557f, 163f)
            ny -= 39 + 163

            actor.setOnClickListener(game.soundUtil) {
                if (index == 2) game.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(scrName[index], FirstScreen::class.java.name)
                }
            }
        }


        val boxMus = ACheckBox(this@FirstScreen, ACheckBox.Static.Type.MUSIC)
        val boxSou = ACheckBox(this@FirstScreen, ACheckBox.Static.Type.SOUND)

        addActors(boxMus, boxSou)
        boxMus.setBounds(450f, 93f, 481f, 131f)
        boxSou.setBounds(989f, 93f, 481f, 131f)

        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying.not()) boxMus.check(false)

            boxMus.setOnCheckListener { if (it) {
                mmm.pause()
            } else {
                mmm.play()
            } }
        }

        if (game.soundUtil.isPause) boxSou.check(false)
        boxSou.setOnCheckListener { game.soundUtil.isPause = it }

    }

}