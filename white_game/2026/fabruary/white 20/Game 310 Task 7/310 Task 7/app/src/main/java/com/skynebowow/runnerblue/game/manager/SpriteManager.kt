package com.skynebowow.runnerblue.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        //GAME(AtlasData("atlas/game.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        LOADER(TextureData("textures/loader/loader.png")),

        _1         (TextureData("textures/all/1.png")),
        _2         (TextureData("textures/all/2.png")),
        _3         (TextureData("textures/all/3.png")),
        _4         (TextureData("textures/all/4.png")),

        BEC_DEF    (TextureData("textures/all/bec_def.png")),
        BEC_PRESS  (TextureData("textures/all/bec_press.png")),
        BK_GAME    (TextureData("textures/all/bk_game.png")),
        BK_MENU    (TextureData("textures/all/bk_menu.png")),
        BK_REST    (TextureData("textures/all/bk_rest.png")),
        CHECK      (TextureData("textures/all/check.png")),
        EXIT_DEF   (TextureData("textures/all/exit_def.png")),
        EXIT_PRESS (TextureData("textures/all/exit_press.png")),
        FAIL       (TextureData("textures/all/fail.png")),
        GREET      (TextureData("textures/all/greet.png")),
        PALEN      (TextureData("textures/all/palen.png")),
        PL_DEF     (TextureData("textures/all/pl_def.png")),
        PL_PRESS   (TextureData("textures/all/pl_press.png")),
        RLS        (TextureData("textures/all/rls.png")),
        SHOP       (TextureData("textures/all/shop.png")),
        SHOP_DEF   (TextureData("textures/all/shop_def.png")),
        SHOP_PRESS (TextureData("textures/all/shop_press.png")),
        SOUND_CHECK(TextureData("textures/all/sound_check.png")),
        SOUND_DEF  (TextureData("textures/all/sound_def.png")),
        STAR       (TextureData("textures/all/star.png")),
        TRY        (TextureData("textures/all/try.png")),
        WIN        (TextureData("textures/all/win.png")),
        XMARA      (TextureData("textures/all/xmara.png")),
        RL_DEF     (TextureData("textures/all/rl_def.png")),
        RL_PRESS   (TextureData("textures/all/rl_press.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}