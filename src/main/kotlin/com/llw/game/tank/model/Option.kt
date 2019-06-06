package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 选项
 */
class Option(viewX: Int, viewY: Int) : Blockade {

    override val tier: Int = 1

    override val width: Int = 192
    override val height: Int = 192

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override fun draw() {
        Painter.drawImage(Config.View.Option, x, y)
    }
}