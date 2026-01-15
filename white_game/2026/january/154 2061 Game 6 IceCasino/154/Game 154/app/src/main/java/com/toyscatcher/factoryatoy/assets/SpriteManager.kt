package com.toyscatcher.factoryatoy.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.toyscatcher.factoryatoy.assets.SpriteManager.Sprite.*
import com.toyscatcher.factoryatoy.assets.util.IAssetManager
import com.toyscatcher.factoryatoy.utils.region

object SpriteManager : IAssetManager() {

    private val startListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..3).onEach { add(AssetDescriptor("sprites/$it.png", Texture::class.java)) }
    }

    private val toyListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..6).onEach { add(AssetDescriptor("sprites/toys/item_$it.png", Texture::class.java)) }
    }

    private val catchToyListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..6).onEach { add(AssetDescriptor("sprites/catch_toys/item_$it.png", Texture::class.java)) }
    }

    lateinit var startList        : MutableList<TextureRegion>
    lateinit var toyList          : MutableList<TextureRegion>
    lateinit var catchToyList     : MutableList<TextureRegion>

    lateinit var box              : TextureRegion
    lateinit var left_default     : TextureRegion
    lateinit var left_pressed     : TextureRegion
    lateinit var mask             : TextureRegion
    lateinit var right_default    : TextureRegion
    lateinit var right_pressed    : TextureRegion
    lateinit var tap_arm          : TextureRegion
    lateinit var tap_grab         : TextureRegion
    lateinit var tap_top          : TextureRegion
    lateinit var toys             : TextureRegion
    lateinit var none             : TextureRegion
    lateinit var blur_background  : TextureRegion
    lateinit var ball             : TextureRegion
    lateinit var glow             : TextureRegion
    lateinit var time             : TextureRegion
    lateinit var money            : TextureRegion
    lateinit var game_over        : TextureRegion
    lateinit var menu_background  : TextureRegion
    lateinit var play_def         : TextureRegion
    lateinit var play_press       : TextureRegion
    lateinit var exit_def         : TextureRegion
    lateinit var exit_press       : TextureRegion

    lateinit var bek       : TextureRegion
    lateinit var pan       : TextureRegion
    lateinit var rules       : TextureRegion



    override fun load(assetManager: AssetManager, assetEnum: IAssetEnum) {
        if (assetEnum !is Sprite) return

        when {
            (assetEnum.descriptor != null)     -> assetManager.load(assetEnum.descriptor)
            (assetEnum.descriptorList != null) -> assetEnum.descriptorList!!.onEach { assetManager.load(it) }
        }
    }

    override fun init(assetManager: AssetManager, assetEnum: IAssetEnum) {
        if (assetEnum !is Sprite) return

        when (assetEnum) {
            START_LIST -> {
                startList = mutableListOf()
                assetEnum.descriptorList!!.onEach { startList.add(assetManager[it].region) }
            }
            TOY_LIST -> {
                toyList = mutableListOf()
                assetEnum.descriptorList!!.onEach { toyList.add(assetManager[it].region) }
            }
            CATCH_TOY_LIST -> {
                catchToyList = mutableListOf()
                assetEnum.descriptorList!!.onEach { catchToyList.add(assetManager[it].region) }
            }

            BOX              -> box              = assetManager[assetEnum.descriptor].region
            LEFT_DEFAULT     -> left_default     = assetManager[assetEnum.descriptor].region
            LEFT_PRESSED     -> left_pressed     = assetManager[assetEnum.descriptor].region
            RIGHT_DEFAULT    -> right_default    = assetManager[assetEnum.descriptor].region
            RIGHT_PRESSED    -> right_pressed    = assetManager[assetEnum.descriptor].region
            TAP_ARM          -> tap_arm          = assetManager[assetEnum.descriptor].region
            TAP_GRAB         -> tap_grab         = assetManager[assetEnum.descriptor].region
            TAP_TOP          -> tap_top          = assetManager[assetEnum.descriptor].region
            TOYS             -> toys             = assetManager[assetEnum.descriptor].region
            NONE             -> none             = assetManager[assetEnum.descriptor].region
            BLUR_BACKGROUND  -> blur_background  = assetManager[assetEnum.descriptor].region
            BALL             -> ball             = assetManager[assetEnum.descriptor].region
            GLOW             -> glow             = assetManager[assetEnum.descriptor].region
            MASK             -> mask             = assetManager[assetEnum.descriptor].region
            TIME             -> time             = assetManager[assetEnum.descriptor].region
            MONEY            -> money            = assetManager[assetEnum.descriptor].region
            GAME_OVER        -> game_over        = assetManager[assetEnum.descriptor].region
            MENU_BACKGROUND  -> menu_background  = assetManager[assetEnum.descriptor].region
            PLAY_DEF         -> play_def        = assetManager[assetEnum.descriptor].region
            PLAY_PRESS       -> play_press        = assetManager[assetEnum.descriptor].region
            EXIT_DEF         -> exit_def        = assetManager[assetEnum.descriptor].region
            EXIT_PRESS       -> exit_press        = assetManager[assetEnum.descriptor].region

            BEK       -> bek        = assetManager[assetEnum.descriptor].region
            PAN       -> pan        = assetManager[assetEnum.descriptor].region
            RULES       -> rules        = assetManager[assetEnum.descriptor].region
        }
    }

    fun loadAll(assetManager: AssetManager){
        super.loadAll(assetManager, entries.toTypedArray())
    }

    fun initAll(assetManager: AssetManager){
        super.initAll(assetManager, entries.toTypedArray())
    }



    enum class Sprite(
        override val descriptor: AssetDescriptor<out Any>? = null,
        override val descriptorList: List<AssetDescriptor<out Any>>? = null,
    ) : IAssetEnum {
        START_LIST(       descriptorList = startListDescriptors),
        TOY_LIST(         descriptorList = toyListDescriptors),
        CATCH_TOY_LIST(   descriptorList = catchToyListDescriptors),

        BOX(              descriptor = AssetDescriptor("sprites/box.png"              , Texture::class.java)),
        LEFT_DEFAULT(     descriptor = AssetDescriptor("sprites/left_default.png"     , Texture::class.java)),
        LEFT_PRESSED(     descriptor = AssetDescriptor("sprites/left_pressed.png"     , Texture::class.java)),
        RIGHT_DEFAULT(    descriptor = AssetDescriptor("sprites/right_default.png"    , Texture::class.java)),
        RIGHT_PRESSED(    descriptor = AssetDescriptor("sprites/right_pressed.png"    , Texture::class.java)),
        TAP_ARM(          descriptor = AssetDescriptor("sprites/tap_arm.png"          , Texture::class.java)),
        TAP_GRAB(         descriptor = AssetDescriptor("sprites/tap_grab.png"         , Texture::class.java)),
        TAP_TOP(          descriptor = AssetDescriptor("sprites/tap_top.png"          , Texture::class.java)),
        TOYS(             descriptor = AssetDescriptor("sprites/toys.png"             , Texture::class.java)),
        NONE(             descriptor = AssetDescriptor("sprites/none.png"             , Texture::class.java)),
        BLUR_BACKGROUND(  descriptor = AssetDescriptor("sprites/blur_background.png"  , Texture::class.java)),
        BALL(             descriptor = AssetDescriptor("sprites/ball.png"             , Texture::class.java)),
        GLOW(             descriptor = AssetDescriptor("sprites/glow.png"             , Texture::class.java)),
        MASK(             descriptor = AssetDescriptor("sprites/mask.png"             , Texture::class.java)),
        TIME(             descriptor = AssetDescriptor("sprites/time.png"             , Texture::class.java)),
        MONEY(            descriptor = AssetDescriptor("sprites/money.png"            , Texture::class.java)),
        GAME_OVER(        descriptor = AssetDescriptor("sprites/game_over.png"        , Texture::class.java)),
        MENU_BACKGROUND(  descriptor = AssetDescriptor("sprites/menu_background.png"  , Texture::class.java)),
        PLAY_DEF(         descriptor = AssetDescriptor("sprites/play_def.png"         , Texture::class.java)),
        PLAY_PRESS(       descriptor = AssetDescriptor("sprites/play_press.png"       , Texture::class.java)),
        EXIT_DEF(         descriptor = AssetDescriptor("sprites/exit_def.png"         , Texture::class.java)),
        EXIT_PRESS(       descriptor = AssetDescriptor("sprites/exit_press.png"       , Texture::class.java)),

        BEK(       descriptor = AssetDescriptor("sprites/bek.png"       , Texture::class.java)),
        PAN(       descriptor = AssetDescriptor("sprites/pan.png"       , Texture::class.java)),
        RULES(       descriptor = AssetDescriptor("sprites/rules.png"       , Texture::class.java)),
    }

}

