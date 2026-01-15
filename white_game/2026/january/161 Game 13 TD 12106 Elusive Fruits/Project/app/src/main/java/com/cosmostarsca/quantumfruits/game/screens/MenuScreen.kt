package com.cosmostarsca.quantumfruits.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.cosmostarsca.quantumfruits.game.LibGDXGame
import com.cosmostarsca.quantumfruits.game.screens.DishchScreen.Companion.RECORD
import com.cosmostarsca.quantumfruits.game.utils.TIME_ANIM
import com.cosmostarsca.quantumfruits.game.utils.actor.animHide
import com.cosmostarsca.quantumfruits.game.utils.actor.animShow
import com.cosmostarsca.quantumfruits.game.utils.actor.setOnClickListener
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedScreen
import com.cosmostarsca.quantumfruits.game.utils.advanced.AdvancedStage
import com.cosmostarsca.quantumfruits.game.utils.font.FontParameter
import com.cosmostarsca.quantumfruits.game.utils.region

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val recordText = "Record\n" + "--------------------\n"

    private val parmezan = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + recordText).setSize(54)
    private val font     = fontGenerator_Bold.generateFont(parmezan)

    private val aRecordLbl = Label(recordText + RECORD, Label.LabelStyle(font, Color.WHITE))

    private val menu = Image(game.assets.menu)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.load.Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(menu.apply { setBounds(529f, 114f, 469f, 672f) })
        val play     = Actor()
        val settings = Actor()
        val rules    = Actor()
        val exit     = Actor()

        addActors(play, settings, rules, exit)

        play.apply {
            setBounds(529f, 639f, 469f, 147f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(DishchScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        settings.apply {
            setBounds(529f, 466f, 469f, 147f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        rules.apply {
            setBounds(529f, 293f, 469f, 147f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        exit.apply {
            setBounds(529f, 114f, 469f, 147f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) { game.navigationManager.exit() }
            }
        }

        addActor(aRecordLbl)
        aRecordLbl.apply {
            setBounds(61f, 69f, 387f,270f)
            setAlignment(Align.center)
        }
    }

}