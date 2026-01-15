package com.catchroyal.oceancatcher.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.catchroyal.oceancatcher.assets.SpriteManager.Sprite.*
import com.catchroyal.oceancatcher.assets.util.IAssetManager
import com.catchroyal.oceancatcher.utils.region

object SpriteManager : IAssetManager() {

    private val backgroundListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..2).onEach { add(AssetDescriptor("sprites/backgrounds/background_$it.png", Texture::class.java)) }
    }

    private val countdownListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..3).onEach { add(AssetDescriptor("sprites/countdown/$it.png", Texture::class.java)) }
    }

    private val birdListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..3).onEach { add(AssetDescriptor("sprites/bird_anim/bird_$it.png", Texture::class.java)) }
    }

    private val itemListDescriptors = mutableListOf<AssetDescriptor<Texture>>().apply {
        (1..8).onEach { add(AssetDescriptor("sprites/items/item_$it.png", Texture::class.java)) }
    }

    lateinit var backgroundList : MutableList<TextureRegion>
    lateinit var countdownList  : MutableList<TextureRegion>
    lateinit var birdList       : MutableList<TextureRegion>
    lateinit var itemList       : MutableList<TextureRegion>

    lateinit var hp             : TextureRegion
    lateinit var jewelry        : TextureRegion
    lateinit var hero           : TextureRegion
    lateinit var none           : TextureRegion
    lateinit var play_def       : TextureRegion
    lateinit var play_press     : TextureRegion
    lateinit var exit_def       : TextureRegion
    lateinit var exit_press     : TextureRegion
    lateinit var banana         : TextureRegion
    lateinit var monkey         : TextureRegion
    lateinit var game_over      : TextureRegion
    lateinit var heart          : TextureRegion

    lateinit var rls          : TextureRegion
    lateinit var rlsd          : TextureRegion
    lateinit var rules          : TextureRegion



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
            BACKGROUNDS -> {
                backgroundList = mutableListOf()
                assetEnum.descriptorList!!.onEach { backgroundList.add(assetManager[it].region) }
            }
            COUNTDOWN   -> {
                countdownList = mutableListOf()
                assetEnum.descriptorList!!.onEach { countdownList.add(assetManager[it].region) }
            }
            BIRDS       -> {
                birdList = mutableListOf()
                assetEnum.descriptorList!!.onEach { birdList.add(assetManager[it].region) }
            }
            ITEMS       -> {
                itemList = mutableListOf()
                assetEnum.descriptorList!!.onEach { itemList.add(assetManager[it].region) }
            }

            HP          -> hp         = assetManager[assetEnum.descriptor].region
            JEWELRY     -> jewelry    = assetManager[assetEnum.descriptor].region
            HERO        -> hero       = assetManager[assetEnum.descriptor].region
            NONE        -> none       = assetManager[assetEnum.descriptor].region
            PLAY_DEF    -> play_def   = assetManager[assetEnum.descriptor].region
            PLAY_PRESS  -> play_press = assetManager[assetEnum.descriptor].region
            EXIT_DEF    -> exit_def   = assetManager[assetEnum.descriptor].region
            EXIT_PRESS  -> exit_press = assetManager[assetEnum.descriptor].region
            MONKEY      -> monkey     = assetManager[assetEnum.descriptor].region
            BANANA      -> banana     = assetManager[assetEnum.descriptor].region
            GAME_OVER   -> game_over  = assetManager[assetEnum.descriptor].region
            HEART       -> heart      = assetManager[assetEnum.descriptor].region

            RLS       -> rls      = assetManager[assetEnum.descriptor].region
            RLSD       -> rlsd      = assetManager[assetEnum.descriptor].region
            RULES       -> rules      = assetManager[assetEnum.descriptor].region
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
        BACKGROUNDS(descriptorList = backgroundListDescriptors),
        COUNTDOWN(  descriptorList = countdownListDescriptors ),
        BIRDS(      descriptorList = birdListDescriptors      ),
        ITEMS(      descriptorList = itemListDescriptors      ),

        HP(        descriptor = AssetDescriptor("sprites/hp.png"        , Texture::class.java)),
        JEWELRY(   descriptor = AssetDescriptor("sprites/jewelry.png"   , Texture::class.java)),
        HERO(      descriptor = AssetDescriptor("sprites/hero.png"      , Texture::class.java)),
        NONE(      descriptor = AssetDescriptor("sprites/none.png"      , Texture::class.java)),
        PLAY_DEF(  descriptor = AssetDescriptor("sprites/play_def.png"  , Texture::class.java)),
        PLAY_PRESS(descriptor = AssetDescriptor("sprites/play_press.png", Texture::class.java)),
        EXIT_DEF(  descriptor = AssetDescriptor("sprites/exit_def.png"  , Texture::class.java)),
        EXIT_PRESS(descriptor = AssetDescriptor("sprites/exit_press.png", Texture::class.java)),
        MONKEY(    descriptor = AssetDescriptor("sprites/monkey.png"    , Texture::class.java)),
        BANANA(    descriptor = AssetDescriptor("sprites/banana.png"    , Texture::class.java)),
        GAME_OVER( descriptor = AssetDescriptor("sprites/game_over.png" , Texture::class.java)),
        HEART(     descriptor = AssetDescriptor("sprites/heart.png"     , Texture::class.java)),

        RLS(     descriptor = AssetDescriptor("sprites/rls.png"     , Texture::class.java)),
        RLSD(     descriptor = AssetDescriptor("sprites/rlsd.png"     , Texture::class.java)),
        RULES(     descriptor = AssetDescriptor("sprites/rules.png"     , Texture::class.java)),
    }

}

