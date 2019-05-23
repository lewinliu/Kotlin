package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 */
class Bullet(override var currentDirection: Direction, override var x: Int, override var y: Int) : Movable {

    override val width: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_16
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_32
    }

    override val height: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_32
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_16
    }

    override val speed: Int = Config.Block64 / 2

    override var badDirection: Direction? = null

    override fun draw() {
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        Painter.drawImage(Config.getBulletImage(currentDirection), x, y)
    }
}