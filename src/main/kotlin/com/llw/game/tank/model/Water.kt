package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 水地
 */
class Water(viewX: Int, viewY: Int) : Blockade {

    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override fun draw() {
        Painter.drawImage(Config.View.Water, x, y)
    }
}