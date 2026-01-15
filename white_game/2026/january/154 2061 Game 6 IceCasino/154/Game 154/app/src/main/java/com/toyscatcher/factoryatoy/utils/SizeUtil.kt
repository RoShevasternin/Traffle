package com.toyscatcher.factoryatoy.utils

import com.badlogic.gdx.scenes.scene2d.Actor
import com.toyscatcher.factoryatoy.HEIGHT

//val Float.onePercent: Float get() = this / 100
//
//fun getSizeFromPercent(percent: Float, size: Float): Float {
//    return size.onePercent * percent
//}
//
//fun getPercentFromFigma(size: Float, fromSize: Float): Float {
//    return size / fromSize.onePercent
//}
//
//fun Actor.setUpFromFigma(
//    appParentWidth: Float = WIDTH,
//    appParentHeight: Float = HEIGHT,
//    figmaParentWidth: Float = FIGMA_WIDTH,
//    figmaParentHeight: Float = FIGMA_HEIGHT,
//    figmaX: Float,
//    figmaY: Float,
//    figmaWidth: Float,
//    figmaHeight: Float,
//) {
//    val percentX = getPercentFromFigma(figmaX, figmaParentWidth)
//    val x = getSizeFromPercent(percentX, appParentWidth)
//
//    val percentY = 100 - getPercentFromFigma((figmaY + figmaHeight), figmaParentHeight)
//    val y = getSizeFromPercent(percentY, appParentHeight)
//
//    val percentWidth = getPercentFromFigma(figmaWidth, figmaParentWidth)
//    val width = getSizeFromPercent(percentWidth, appParentWidth)
//
//    val percentHeight = getPercentFromFigma(figmaHeight, figmaParentHeight)
//    val height = getSizeFromPercent(percentHeight, appParentHeight)
//
//    setBounds(x, y, width, height)
//
//}

fun getFigmaY(y: Float, height: Float, parentH: Float = HEIGHT) = parentH - (y + height)

fun Actor.setBoundsFigmaY(x: Float, y: Float, width: Float, height: Float, parentH: Float = HEIGHT){
    val reverseY = getFigmaY(y, height, parentH)
    setBounds(x, reverseY, width, height)
}