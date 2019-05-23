package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 砖墙
 */
class Wall(override var x: Int, override var y: Int) : Blockade {
    override val width: Int
        get() = Config.Block64
    override val height: Int
        get() = Config.Block64

    override fun draw() {
        Painter.drawImage(Config.Wall, x, y)
    }
}