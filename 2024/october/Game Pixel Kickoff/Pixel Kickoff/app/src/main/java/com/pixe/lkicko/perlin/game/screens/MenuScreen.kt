package com.pixe.lkicko.perlin.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.pixe.lkicko.perlin.game.LibGDXGame
import com.pixe.lkicko.perlin.game.actors.AMenuFull
import com.pixe.lkicko.perlin.game.utils.GColor
import com.pixe.lkicko.perlin.game.utils.TIME_ANIM
import com.pixe.lkicko.perlin.game.utils.actor.animHide
import com.pixe.lkicko.perlin.game.utils.actor.animShow
import com.pixe.lkicko.perlin.game.utils.actor.setOnClickListener
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedScreen
import com.pixe.lkicko.perlin.game.utils.advanced.AdvancedStage
import com.pixe.lkicko.perlin.game.utils.disable
import com.pixe.lkicko.perlin.game.utils.enable
import com.pixe.lkicko.perlin.game.utils.font.FontParameter

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirstShow = true
    }

    private val fontParameter = FontParameter()
    private val font60        = fontGenerator_Jua.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(60))

    private val ls60 = LabelStyle(font60, GColor.black)

    // Actors
    private val lblPX    = Label(game.dataStore.px.toString(), ls60)
    private val imgMenu  = Image(game.all.menu_mini)
    private val menuFull = AMenuFull(this)

    override fun show() {
        if (isFirstShow) {
            isFirstShow = false

            game.musicUtil.also { mu ->
                mu.music = mu.pixbet.apply { isLooping = true }
                mu.volumeLevelFlow.value = 3f
            }
        }

        setUIBackground(game.all.background_1)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLblPX()
        addMenuFull()
        addMenuMini()

        val aPX = Actor()
        addActor(aPX)
        aPX.apply {
            setBounds(678f, 1731f, 402f, 135f)
//            setOnClickListener(game.soundUtil) {
//                root.animHide(TIME_ANIM) {
//                    game.navigationManager.navigate(CoinsScreen::class.java.name, MenuScreen::class.java.name)
//                }
//            }
        }
    }

    private fun AdvancedStage.addLblPX() {
        addActor(lblPX)
        lblPX.setBounds(882f,1763f,152f,69f)
    }

    private fun AdvancedStage.addMenuFull() {
        addActor(menuFull)
        menuFull.setBounds(496f, -1475f, 938f, 1733f)
        menuFull.color.a = 0f
        menuFull.disable()

        menuFull.blockMenu = {
            menuFull.disable()
            menuFull.addAction(Actions.sequence(
                Actions.moveTo(494f, -1475f, 0.35f, Interpolation.pow2),
                Actions.run {
                    menuFull.animHide()
                    imgMenu.animShow()
                    imgMenu.enable()
                }
            ))
        }
    }

    private fun AdvancedStage.addMenuMini() {
        addActor(imgMenu)
        imgMenu.setBounds(496f, -2f, 584f, 259f)

        imgMenu.setOnClickListener(game.soundUtil) {
            imgMenu.disable()
            imgMenu.animHide()
            menuFull.animShow {
                menuFull.enable()
                menuFull.addAction(Actions.moveTo(144f, -2f, 0.35f, Interpolation.pow2))
            }
        }
    }

}