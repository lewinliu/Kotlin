package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Suffer
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 铁墙
 */
class Steel(viewX: Int, viewY: Int) : Blockade, Suffer {

    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    override var suffer: Int = 10

    override var isDestroy: Boolean = false

    override fun draw() {
        Painter.drawImage(Config.View.Steel, x, y)
    }
}