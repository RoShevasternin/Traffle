package com.pink.plinuirtaster.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.pink.plinuirtaster.game.LibGDXGame
import com.pink.plinuirtaster.game.actors.AButton
import com.pink.plinuirtaster.game.actors.ANameField
import com.pink.plinuirtaster.game.screens.SettingsScreen.Companion.IS_THEME_LIGHT
import com.pink.plinuirtaster.game.utils.GColor
import com.pink.plinuirtaster.game.utils.TIME_ANIM
import com.pink.plinuirtaster.game.utils.actor.animHideScreen
import com.pink.plinuirtaster.game.utils.actor.animShowScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedScreen
import com.pink.plinuirtaster.game.utils.advanced.AdvancedStage
import com.pink.plinuirtaster.game.utils.font.FontParameter
import com.pink.plinuirtaster.game.utils.region
import com.pink.plinuirtaster.game.utils.runGDX
import kotlinx.coroutines.launch

class NamesScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        val UserData_List = mutableListOf<UserData>()
    }

    data class UserData(val uName: String, var uScore: Int)

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setBorder(3f, GColor.stroke)
    private val font65        = fontGenerator_RubikWetPaint.generateFont(fontParameter.setSize(65))
    private val font74        = fontGenerator_RubikWetPaint.generateFont(fontParameter.setSize(74))

    private val ls65 = Label.LabelStyle(font65, GColor.pink)
    private val ls74 = Label.LabelStyle(font74, GColor.pink)

    private val btnBeck        = AButton(this, AButton.Static.Type.Back)
    private val btnStart       = AButton(this, AButton.Static.Type.Start)
    private val btnPlus        = AButton(this, AButton.Static.Type.Plus)
    private val imgPlayerNames = Image(game.all.PLAYER_NAMES)
    private val nameList       = MutableList(2) { ANameField(this, it.inc(), ls65, ls74) }

    // Field
    private var nameY = 1359f

    override fun show() {
        ScoreScreen.CurrentPlayerIndex = 0
        UserData_List.clear()
        setBackBackground(if (IS_THEME_LIGHT) game.splash.BACKGROUND.region else game.all.Dark.region)

        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addBack()
                addBtnPlus()
                addBtnStart()
                addImgPlayerNames()

                addNameFields()
            }
        }
    }

    private fun AdvancedStage.addBack() {
        addActor(btnBeck)
        btnBeck.apply {
            setBounds(50f,1795f,140f,76f)
            setOnClickListener {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
    }

    private fun AdvancedStage.addBtnStart() {
        addActor(btnStart)
        btnStart.apply {
            setBounds(273f,167f,600f,180f)
            setOnClickListener {
                if (nameList.any { it.nameUser.isEmpty() || it.nameUser.isBlank() }) {
                    game.soundUtil.apply { play(ERROR, 0.5f) }
                    nameList.onEach { nameField ->
                        if (nameField.nameUser.isEmpty() || nameField.nameUser.isBlank()) nameField.showError()
                    }
                } else {
                    nameList.onEach { UserData_List.add(UserData(it.nameUser, 0)) }
                    stageUI.root.animHideScreen(TIME_ANIM) {
                        game.navigationManager.navigate(ScoreScreen::class.java.name, NamesScreen::class.java.name)
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addBtnPlus() {
        addActor(btnPlus)
        btnPlus.apply {
            setBounds(470f,995f,140f,140f)
            setOnClickListener(null) {
                game.soundUtil.apply { play(PLUS, 0.7f) }
                handlePlus()
            }
        }
    }

    private fun AdvancedStage.addImgPlayerNames() {
        addActor(imgPlayerNames)
        imgPlayerNames.setBounds(139f,1549f,809f,152f)
    }

    private fun AdvancedStage.addNameFields() {
        nameList.onEach { name ->
            addActor(name)
            name.setBounds(74f, nameY, 936f, 140f)
            nameY -= 24+140
        }
    }

    // Logic ---------------------------------------------------------------------------------

    private fun handlePlus() {
        if (nameList.size < 6) {
            val nameField = ANameField(this, nameList.size.inc(), ls65, ls74)

            if (nameList.size == 5) btnPlus.also { plus ->
                plus.disable()
                plus.addAction(Actions.fadeOut(0.35f))
            }

            btnPlus.addAction(Actions.sequence(
                Actions.moveBy(0f, -164f, 0.3f, Interpolation.sine),
                Actions.run {
                    nameList.add(nameField)
                    stageUI.addActor(nameField)

                    nameField.setBounds(74f, nameY, 936f, 140f)
                    nameY -= 24+140
                }
            ))
        }
    }

}