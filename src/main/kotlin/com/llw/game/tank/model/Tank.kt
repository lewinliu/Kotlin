package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 坦克
 */
class Tank(override var x: Int, override var y: Int, var isTwoPlay: Boolean = false) : Movable {

    override val width: Int = Config.Block64

    override val height: Int = Config.Block64

    override var speed: Int = Config.Block64

    override var currentDirection: Direction = Direction.UP

    override var badDirection: Direction? = null

    override fun draw() {
        println("isTwoPlay = $isTwoPlay,   this.x = ${this.x.toFloat() / this.width.toFloat()}     this.y = ${this.y.toFloat() / this.height.toFloat()}")
        Painter.drawImage(Config.getTankImage(currentDirection, isTwoPlay), x, y)
    }
}