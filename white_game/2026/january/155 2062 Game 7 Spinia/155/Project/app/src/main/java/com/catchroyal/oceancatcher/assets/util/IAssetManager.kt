package com.catchroyal.oceancatcher.assets.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager

abstract class IAssetManager {

    abstract fun load(assetManager: AssetManager, assetEnum: IAssetEnum)
    abstract fun init(assetManager: AssetManager, assetEnum: IAssetEnum)

    protected fun loadAll(assetManager: AssetManager, enums: Array<out IAssetEnum>) {
        enums.onEach { load(assetManager, it) }
    }

    protected fun initAll(assetManager: AssetManager, enums: Array<out IAssetEnum>) {
        enums.onEach { init(assetManager, it) }
    }

    interface IAssetEnum {
        val descriptor: AssetDescriptor<out Any>?
        val descriptorList: List<AssetDescriptor<out Any>>?
    }

}