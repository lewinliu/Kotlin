package com.llw.game.tank.model

import com.llw.game.tank.`interface`.BaseView
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 草地
 */
class Grass(override var x: Int, override var y: Int) : BaseView {
    override val width: Int
        get() = Config.Block
    override val height: Int
        get() = Config.Block

    override fun draw() {
        Painter.drawImage(Config.Grass, x * width, y * height)
    }
}