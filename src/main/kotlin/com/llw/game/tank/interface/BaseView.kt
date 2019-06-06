package com.llw.game.tank.`interface`

/**
 * 基础
 */
interface BaseView {

    /**
     * 横坐标
     */
    var x: Int
    /**
     * 纵坐标
     */
    var y: Int
    /**
     * 宽度
     */
    val width: Int
    /**
     * 高度
     */
    val height: Int

    /**
     * 层级，决定添加到地图中的顺序
     */
    val tier: Int

    /**
     * 绘制
     */
    fun draw()

}