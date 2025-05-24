package com.candies.balloons.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.utils.GColor
import com.candies.balloons.game.utils.TIME_ANIM
import com.candies.balloons.game.utils.actor.animHide
import com.candies.balloons.game.utils.actor.animShow
import com.candies.balloons.game.utils.advanced.AdvancedGroup
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.font.FontParameter
import com.candies.balloons.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class APlayer(
    override val screen: AdvancedScreen,
    val count: Int,
    val indexReg: Int,
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val font31        = screen.fontGenerator_BALOO_CYRILLIC.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"player").setSize(31))
    private val font46        = screen.fontGenerator_BALOO_CYRILLIC.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(46))

    private val ls31 = Label.LabelStyle(font31, GColor.gray)
    private val ls46 = Label.LabelStyle(font46, GColor.blue)

    private val players = listOf(
        screen.game.all._1,
        screen.game.all._2,
        screen.game.all._3,
        screen.game.all._4,
    )

    private val lblName   = Label("player $count", ls31)
    private val lblCount  = Label("0", ls46)
    private val imgPlayer = Image(players[indexReg])
    private val imgCart   = Image(screen.game.all.cart)

    val counterFlow = MutableStateFlow(0)

    override fun addActorsOnGroup() {
        addActor(Image(screen.game.all.pers_counter).apply { setBounds(39f, 6f, 265f, 274f) })
        addActors(lblName, imgPlayer, lblCount, imgCart)
        lblName.apply {
            setAlignment(Align.center)
            setBounds(116f, 217f, 111f, 58f)
        }
        lblCount.apply {
            setAlignment(Align.center)
            setBounds(179f, 0f, 53f, 86f)

            coroutine?.launch {
                counterFlow.collect {
                    runGDX { setText(it) }
                }
            }
        }
        imgPlayer.setBounds(105f, 77f, 133f, 148f)
        imgPlayer.setOrigin(Align.center)
        imgCart.setBounds(0f, 63f, 110f, 141f)
        imgCart.color.a = 0f
    }

    fun current() {
        imgPlayer.addAction(
            Actions.forever(
            Actions.sequence(
                Actions.scaleBy(-0.2f, -0.2f, 0.3f, Interpolation.sineIn),
                Actions.scaleBy(0.2f, 0.2f, 0.3f, Interpolation.sineOut),
            )
        ))
        imgCart.animShow(TIME_ANIM)
    }

    fun uncurrent() {
        imgPlayer.clearActions()
        imgPlayer.addAction(Actions.scaleTo(1f, 1f, 0.25f))
        imgCart.animHide(TIME_ANIM)
    }

}