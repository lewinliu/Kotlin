package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 坦克
 */
class Tank(override var x: Int, override var y: Int) : Movable {

    override val width: Int = Config.Block

    override val height: Int = Config.Block

    override var speed: Int = 1

    override var currentDirection: Direction = Direction.UP

    override var badDirection: Direction? = null

    override fun draw() {
        Painter.drawImage(Config.getTankImage(currentDirection), x * width, y * height)
    }
}