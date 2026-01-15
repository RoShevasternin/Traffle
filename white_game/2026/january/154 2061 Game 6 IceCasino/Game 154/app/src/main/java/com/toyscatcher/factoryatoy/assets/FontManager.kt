package com.toyscatcher.factoryatoy.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.toyscatcher.factoryatoy.assets.util.IAssetManager

object FontManager {

    private const val path = "font/font.ttf"

    private val resolverInternal = InternalFileHandleResolver()

    private val descriptor_24 = AssetDescriptor("font.ttf", BitmapFont::class.java)

    lateinit var font: BitmapFont



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



    private fun getParameters() = getLoaderParameter(path) { size = 30 }



    fun load(assetManager: AssetManager, assetEnum: FontEnum) {
        with(assetManager) {
            setLoaderTTF()
            load(assetEnum.descriptor.fileName, BitmapFont::class.java, assetEnum.parameters)
        }
    }

    fun init(assetManager: AssetManager, assetEnum: FontEnum) {
        with(assetManager) {
            when (assetEnum) {
                FontEnum.FONT -> font = get(assetEnum.descriptor)
            }
        }
    }



    enum class FontEnum(
        val descriptor: AssetDescriptor<BitmapFont>,
        val parameters: FreetypeFontLoader.FreeTypeFontLoaderParameter
    ) {
        FONT(descriptor_24, getParameters())
    }
}