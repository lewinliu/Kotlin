package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 基地
 */
class Camp(viewX: Int, viewY: Int) : Blockade {

    override val width: Int  = Config.Block64
    override val height: Int  = Config.Block64

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override fun draw() {
        Painter.drawImage(Config.Camp, x, y)
    }
}