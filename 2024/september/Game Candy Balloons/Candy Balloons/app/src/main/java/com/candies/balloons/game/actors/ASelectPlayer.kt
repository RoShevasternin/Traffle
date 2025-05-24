package com.candies.balloons.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.candies.balloons.game.utils.GColor
import com.candies.balloons.game.utils.actor.setOnClickListener
import com.candies.balloons.game.utils.advanced.AdvancedGroup
import com.candies.balloons.game.utils.advanced.AdvancedScreen
import com.candies.balloons.game.utils.font.FontParameter

class ASelectPlayer(
    override val screen: AdvancedScreen,
    var count: Int
) : AdvancedGroup() {

    private val fontParameter = FontParameter()
    private val font41        = screen.fontGenerator_BALOO_CYRILLIC.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"player").setSize(41))

    private val ls41 = Label.LabelStyle(font41, GColor.gray)

    private val players = listOf(
        screen.game.all._1,
        screen.game.all._2,
        screen.game.all._3,
        screen.game.all._4,
    )

    private val lblName   = Label("player $count", ls41)
    private val imgPlayer = Image(players.first())

    var isSelect = false
    var indexReg = 0
        private set

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.all.selectel))
        addActors(lblName, imgPlayer)
        lblName.apply {
            setAlignment(Align.center)
            setBounds(99f, 260f, 151f, 76f)
        }
        imgPlayer.setBounds(82f, 42f, 183f, 203f)

        val left  = Actor()
        val right = Actor()
        addActors(left, right)

        left.apply {
            setBounds(5f, 17f, 78f, 217f)
            setOnClickListener(screen.game.soundUtil) {
                if (indexReg > 0) {
                    indexReg--
                    imgPlayer.drawable = TextureRegionDrawable(players[indexReg])
                }
            }
        }
        right.apply {
            setBounds(258f, 17f, 78f, 217f)
            setOnClickListener(screen.game.soundUtil) {
                if (indexReg < 3) {
                    indexReg++
                    imgPlayer.drawable = TextureRegionDrawable(players[indexReg])
                }
            }
        }
    }

}