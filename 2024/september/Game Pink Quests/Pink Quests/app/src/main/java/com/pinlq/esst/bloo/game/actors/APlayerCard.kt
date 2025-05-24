package com.pinlq.esst.bloo.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.pinlq.esst.bloo.game.utils.TIME_ANIM
import com.pinlq.esst.bloo.game.utils.actor.animHide
import com.pinlq.esst.bloo.game.utils.actor.setOnClickListener
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedGroup
import com.pinlq.esst.bloo.game.utils.advanced.AdvancedScreen
import com.pinlq.esst.bloo.game.utils.disable
import com.pinlq.esst.bloo.game.utils.font.FontParameter

class APlayerCard(
    override val screen: AdvancedScreen,
    val userNum: Int,
): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font270       = screen.fontGenerator_IrishGrover_Regular.generateFont(fontParameter.setSize(270))

    private val ls270 = Label.LabelStyle(font270, Color.WHITE)

    private val image     = Image(screen.game.all.select_panel)
    private val lblNum    = Label(userNum.toString(), ls270)
    private val imageIcon = Image()

    var isHasIcon = false
        private set

    override fun addActorsOnGroup() {
        addAndFillActor(image)
        addActors(lblNum, imageIcon)
        children.onEach { it.disable() }

        lblNum.setBounds(210f,209f,88f,282f)
        imageIcon.setBounds(55f,125f,400f,450f)
    }

    fun setIcon(texture: Texture) {
        lblNum.color.a     = 0f
        imageIcon.drawable = TextureRegionDrawable(texture)
        isHasIcon = true
    }

}