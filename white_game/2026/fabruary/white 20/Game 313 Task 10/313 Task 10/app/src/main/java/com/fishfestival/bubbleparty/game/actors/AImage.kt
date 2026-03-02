package com.fishfestival.bubbleparty.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fishfestival.bubbleparty.game.utils.actor.addAndFillActor
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedGroup
import com.fishfestival.bubbleparty.game.utils.advanced.AdvancedScreen
import com.fishfestival.bubbleparty.game.utils.advanced.PARANAMA
import com.fishfestival.bubbleparty.game.utils.advanced.PIDAR

class AImage constructor(override val screen: PARANAMA): PIDAR() {

    private val image = Image()

    var drawable: Drawable = TextureRegionDrawable()
        get() = image.drawable
        set(value) {
            image.drawable = value
            field = value
        }

    constructor(screen: PARANAMA, region: TextureRegion) : this(screen) {
        image.drawable = TextureRegionDrawable(region)
    }
    constructor(screen: PARANAMA, texture: Texture) : this(screen) {
        image.drawable = TextureRegionDrawable(texture)
    }
    constructor(screen: PARANAMA, drawable: Drawable) : this(screen) {
        image.drawable = drawable
    }
    constructor(screen: PARANAMA, ninePatch: NinePatch) : this(screen) {
        image.drawable = NinePatchDrawable(ninePatch)
    }

    override fun addActorsOnGroup() {
        addAndFillActor(image)
    }

}