package com.catchroyal.oceancatcher.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.catchroyal.oceancatcher.assets.FontManager.FontEnum.*

object FontManager {

    private const val path = "font/RobotoMono-Medium.ttf"

    private val resolverInternal = InternalFileHandleResolver()

    private val descriptor_50 = AssetDescriptor("font_50.ttf", BitmapFont::class.java)
    private val descriptor_26 = AssetDescriptor("font_26.ttf", BitmapFont::class.java)
    private val descriptor_17 = AssetDescriptor("font_17.ttf", BitmapFont::class.java)



    lateinit var font_50: BitmapFont
    lateinit var font_26: BitmapFont
    lateinit var font_17: BitmapFont



    private fun AssetManager.setLoaderTTF() {
        setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolverInternal))
        setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolverInternal))
    }



    private fun getLoaderParameter(
        fileName: String,
        parameters: FreeTypeFontGenerator.FreeTypeFontParameter.() -> Unit = { }
    ) = FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = fileName
        parameters(fontParameters)
    }



    private fun getParameter50() = getLoaderParameter(path) { size = 50 }
    private fun getParameter26() = getLoaderParameter(path) { size = 26 }
    private fun getParameter17() = getLoaderParameter(path) { size = 17 }



    fun load(assetManager: AssetManager, assetEnum: FontEnum) {
        with(assetManager) {
            setLoaderTTF()
            load(assetEnum.descriptor.fileName, BitmapFont::class.java, assetEnum.parameters)
        }
    }

    fun init(assetManager: AssetManager, assetEnum: FontEnum) {
        with(assetManager) {
            when (assetEnum) {
                FONT_50 -> font_50 = get(assetEnum.descriptor)
                FONT_26 -> font_26 = get(assetEnum.descriptor)
                FONT_17 -> font_17 = get(assetEnum.descriptor)
            }
        }
    }

    fun loadAll(assetManager: AssetManager){
        values().onEach { load(assetManager, it) }
    }

    fun initAll(assetManager: AssetManager){
        values().onEach { init(assetManager, it) }
    }



    enum class FontEnum(
        val descriptor: AssetDescriptor<BitmapFont>,
        val parameters: FreetypeFontLoader.FreeTypeFontLoaderParameter
    ) {
        FONT_50(descriptor_50, getParameter50()),
        FONT_26(descriptor_26, getParameter26()),
        FONT_17(descriptor_17, getParameter17()),
    }
}