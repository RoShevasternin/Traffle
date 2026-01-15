package com.samartachokitse.endelgase.game.actors

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedGroup
import com.samartachokitse.endelgase.game.utils.advanced.AdvancedScreen

class AImageAns(override val screen: AdvancedScreen): AdvancedGroup() {

    private val image = Image()

    var drawable: Drawable = TextureRegionDrawable()
        get() = image.drawable
        set(value) {
            image.drawable = value
            field = value
        }

    override fun addActorsOnGroup() {
        addActor(image)
        image.setBounds(86f, 16f, 50f, 50f)
        image.touchable = Touchable.disabled
    }

}