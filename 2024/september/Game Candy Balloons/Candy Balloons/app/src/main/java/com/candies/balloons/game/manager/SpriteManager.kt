package com.candies.balloons.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTexturesList   = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTeture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }


    enum class EnumAtlas(val data: AtlasData) {
        //ALL(AtlasData("assets/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        DARK(TextureData("textures/splash/dark.png")),
        candy(TextureData("textures/splash/candy.png")),
        title(TextureData("textures/splash/title.png")),

        GRADIENT(TextureData("textures/backgrounds/gradient.png")),
        LIGHT   (TextureData("textures/backgrounds/light.png")),

        _1            (TextureData("textures/1.png")),
        _2            (TextureData("textures/2.png")),
        _3            (TextureData("textures/3.png")),
        _4            (TextureData("textures/4.png")),
        achi          (TextureData("textures/achi.png")),
        achi_def      (TextureData("textures/achi_def.png")),
        achi_press    (TextureData("textures/achi_press.png")),
        arrow         (TextureData("textures/arrow.png")),
        b1            (TextureData("textures/b1.png")),
        b2            (TextureData("textures/b2.png")),
        b3            (TextureData("textures/b3.png")),
        b4            (TextureData("textures/b4.png")),
        b5            (TextureData("textures/b5.png")),
        b6            (TextureData("textures/b6.png")),
        b7            (TextureData("textures/b7.png")),
        b8            (TextureData("textures/b8.png")),
        b9            (TextureData("textures/b9.png")),
        cart          (TextureData("textures/cart.png")),
        dom_def       (TextureData("textures/dom_def.png")),
        dom_press     (TextureData("textures/dom_press.png")),
        english       (TextureData("textures/english.png")),
        ext_def       (TextureData("textures/ext_def.png")),
        ext_press     (TextureData("textures/ext_press.png")),
        howtoplay     (TextureData("textures/howtoplay.png")),
        htp           (TextureData("textures/htp.png")),
        htpdef        (TextureData("textures/htpdef.png")),
        htpprs        (TextureData("textures/htpprs.png")),
        language      (TextureData("textures/language.png")),
        lead_def      (TextureData("textures/lead_def.png")),
        lead_press    (TextureData("textures/lead_press.png")),
        leader        (TextureData("textures/leader.png")),
        leaderboard   (TextureData("textures/leaderboard.png")),
        num_of_player (TextureData("textures/num_of_player.png")),
        off           (TextureData("textures/off.png")),
        on            (TextureData("textures/on.png")),
        perfect       (TextureData("textures/perfect.png")),
        perfect_block (TextureData("textures/perfect_block.png")),
        pers_counter  (TextureData("textures/pers_counter.png")),
        play_def      (TextureData("textures/play_def.png")),
        play_press    (TextureData("textures/play_press.png")),
        pp_def        (TextureData("textures/pp_def.png")),
        pp_press      (TextureData("textures/pp_press.png")),
        restart_def   (TextureData("textures/restart_def.png")),
        restart_press (TextureData("textures/restart_press.png")),
        selectel      (TextureData("textures/selectel.png")),
        sett_def      (TextureData("textures/sett_def.png")),
        sett_press    (TextureData("textures/sett_press.png")),
        settings      (TextureData("textures/settings.png")),
        start_finish  (TextureData("textures/start_finish.png")),
        sw_coll       (TextureData("textures/sw_coll.png")),
        sw_coll_block (TextureData("textures/sw_coll_block.png")),
        won           (TextureData("textures/won.png")),
        cub           (TextureData("textures/cub.png")),
        cub_gray           (TextureData("textures/cub_gray.png")),
        circ          (TextureData("textures/circ.png")),
        circ_gray          (TextureData("textures/circ_gray.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}