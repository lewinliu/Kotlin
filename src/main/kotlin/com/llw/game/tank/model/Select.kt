package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 选择
 */
class Select(viewX: Int, viewY: Int) : Blockade {

    override val tier: Int = 1

    override val width: Int = 32
    override val height: Int = 32

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override fun draw() {
        Painter.drawImage(Config.View.Select, x, y)
    }
}