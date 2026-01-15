package com.princesssand.soundsprinss.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.princesssand.soundsprinss.R
import com.princesssand.soundsprinss.game.actors.mask.InvertedMask
import com.princesssand.soundsprinss.game.utils.GameColor
import com.princesssand.soundsprinss.game.utils.Layout.Tutorial.Game
import com.princesssand.soundsprinss.game.utils.Layout.Tutorial.Levels
import com.princesssand.soundsprinss.game.utils.Layout.Tutorial.Menu
import com.princesssand.soundsprinss.game.utils.Layout.Tutorial.Rules
import com.princesssand.soundsprinss.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.princesssand.soundsprinss.game.utils.actor.animHide
import com.princesssand.soundsprinss.game.utils.actor.animShow
import com.princesssand.soundsprinss.game.utils.actor.disable
import com.princesssand.soundsprinss.game.utils.actor.enable
import com.princesssand.soundsprinss.game.utils.actor.setBounds
import com.princesssand.soundsprinss.game.utils.actor.setOnClickListener
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedGroup
import com.princesssand.soundsprinss.game.utils.advanced.AdvancedScreen
import com.princesssand.soundsprinss.game.utils.font.FontParameter

class ATutorials(override val screen: AdvancedScreen) : AdvancedGroup() {

    companion object {
        private var stepIndex = 0

        val STEP get() = Static.Step.entries[stepIndex]

        fun nextStep() {
            if (stepIndex < Static.Step.entries.lastIndex) stepIndex++
        }
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(30)
    private val font          = screen.fontGenerator_Merienda.generateFont(fontParameter)

    private val maskRegion     = screen.drawerUtil.getRegion(GameColor.tutorialBlack.cpy().apply { a = 0.75f })
    private val mask           = InvertedMask(screen, maskRegion)
    private val visibleAreaImg = Image(screen.game.assetsAll.tutorial)
    private val handImg        = Image(screen.game.assetsAll.hand)
    private val textLbl        = Label("", Label.LabelStyle(font, Color.WHITE))
    private val nextLbl        = Label("NEXT", Label.LabelStyle(font, Color.WHITE))
    private val backImg        = Image(screen.game.assetsAll.b_music)

    override fun addActorsOnGroup() {
        disable()

        when(STEP) {
            Static.Step.MenuRules     -> addMenuRules()
            Static.Step.RulesBack     -> addRulesBack()
            Static.Step.MenuSettings  -> addMenuSettings()
            Static.Step.SettingsMusic -> addSettingsMusic()
            Static.Step.MenuLevels    -> addMenuLevels()
            Static.Step.LevelsLocked  -> addLevelsLocked()
            Static.Step.Game          -> addGame()
        }

    }

    fun addMenuRules() {
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Menu.rules)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(715f, 383f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_menu_rules))
            setBounds(499f, 245f, 529f, 86f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    fun addRulesBack() {
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Rules.back)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(889f, 335f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_rules_back))
            setBounds(664f, 217f, 529f, 88f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    fun addMenuSettings() {
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Menu.settings)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(715f, 491f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_menu_settings))
            setBounds(499f, 362f, 529f, 88f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    fun addSettingsMusic() {
        addAndFillActor(backImg)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(677f, 606f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_settings_music))
            setBounds(944f, 601f, 554f, 132f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    fun addMenuLevels() {
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Menu.levels)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(715f, 600f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_menu_levels))
            setBounds(499f, 461f, 529f, 88f)
            setAlignment(Align.center)
            wrap = true
        }
    }

    fun addLevelsLocked() {
        enable()
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Levels.locked)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(946f, 368f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_levels_locked))
            setBounds(631f, 535f, 529f, 132f)
            setAlignment(Align.center)
            wrap = true
        }

        addActor(nextLbl)
        nextLbl.apply {
            setBounds(855f, 406f, 82f, 44f)
            setOnClickListener(screen.game.soundUtil) {
                this@ATutorials.disable()
                toLevelsUnlocked()
            }
        }
    }

    fun addGame() {
        addAndFillActor(mask)

        mask.addActor(visibleAreaImg)
        visibleAreaImg.setBounds(Game.ball)

        addActor(handImg)
        handImg.apply {
            setOrigin(Align.center)
            setBounds(777f, 364f, 66f, 77f)
            animHand()
        }

        addActor(textLbl)
        textLbl.apply {
            setText(screen.game.activity.getString(R.string.tutorial_game))
            setBounds(301f, 577f, 924f, 132f)
            setAlignment(Align.center)
            wrap = true
        }
    }




    fun toSettingsSound() {
        addAction(Actions.sequence(
            Actions.delay(1f),
            Actions.run {
                backImg.drawable = TextureRegionDrawable(screen.game.assetsAll.b_sound)

                handImg.addAction(Actions.moveTo(677f, 455f, TIME_ANIM_SCREEN_ALPHA))

                textLbl.apply {
                    animHide(TIME_ANIM_SCREEN_ALPHA) {
                        setText(screen.game.activity.getString(R.string.tutorial_settings_sound))
                        setBounds(944f, 450f, 554f, 132f)
                        setAlignment(Align.center)
                        wrap = true
                        animShow(TIME_ANIM_SCREEN_ALPHA)
                    }
                }
            }
        ))
    }

    fun toSettingsBack() {
        addAction(Actions.sequence(
            Actions.delay(1f),
            Actions.run {
                backImg.drawable = TextureRegionDrawable(screen.game.assetsAll.b_back)

                handImg.addAction(Actions.moveTo(911f, 335f, TIME_ANIM_SCREEN_ALPHA))

                textLbl.apply {
                    animHide(TIME_ANIM_SCREEN_ALPHA) {
                        setText(screen.game.activity.getString(R.string.tutorial_settings_back))
                        setBounds(687f, 218f, 529f, 88f)
                        setAlignment(Align.center)
                        wrap = true
                        animShow(TIME_ANIM_SCREEN_ALPHA)
                    }
                }
            }
        ))
    }

    fun toLevelsUnlocked() {
        nextLbl.animHide(TIME_ANIM_SCREEN_ALPHA)
        visibleAreaImg.addAction(Actions.moveTo(126f, 499f, TIME_ANIM_SCREEN_ALPHA))
        handImg.addAction(Actions.moveTo(284f, 468f, TIME_ANIM_SCREEN_ALPHA))

        textLbl.apply {
            animHide(TIME_ANIM_SCREEN_ALPHA) {
                setText(screen.game.activity.getString(R.string.tutorial_levels_unlocked))
                setBounds(350f, 491f, 529f, 220f)
                setAlignment(Align.center)
                wrap = true
                animShow(TIME_ANIM_SCREEN_ALPHA)
            }
        }
    }


    // Anim
    private fun animHand() {
        val scale = 0.5f
        val time  = 0.4f
        handImg.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-scale, -scale, time, Interpolation.sineIn),
            Actions.scaleBy(scale, scale, time, Interpolation.sineOut),
        )))
    }

    object Static {
        enum class Step {
            MenuRules,
            RulesBack,
            MenuSettings,
            SettingsMusic,
            MenuLevels,
            LevelsLocked,
            Game,
        }
    }

}