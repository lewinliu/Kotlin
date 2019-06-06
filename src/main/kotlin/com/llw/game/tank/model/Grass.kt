package com.llw.game.tank.model

import com.llw.game.tank.`interface`.BaseView
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 草地
 */
class Grass(viewX: Int, viewY: Int) : BaseView {

    override val tier: Int = 3

    override val width: Int  = Config.Block64
    override val height: Int  = Config.Block64

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override fun draw() {
        Painter.drawImage(Config.View.Grass, x, y)
    }
}