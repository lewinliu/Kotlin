package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Attackable
import com.llw.game.tank.`interface`.AutoMovable
import com.llw.game.tank.`interface`.Destroyable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 */
class Bullet(override var currentDirection: Direction, override var x: Int, override var y: Int) : AutoMovable, Destroyable, Attackable {

    override val attack: Int = 1

    override var isDestroy: Boolean = false

    override val width: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_16
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_32
    }

    override val height: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_32
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_16
    }

    override val speed: Int = Config.Block64 / 8

    override var badDirection: Direction? = null

    override fun draw() {
        Painter.drawImage(Config.getBulletImage(currentDirection), x, y)
    }

}