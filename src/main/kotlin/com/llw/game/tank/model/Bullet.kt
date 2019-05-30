package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Attack
import com.llw.game.tank.`interface`.AutoMovable
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 */
class Bullet(move: Movable) : AutoMovable, Attack {

    override var currentDirection: Direction = move.currentDirection

    override val width: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_16
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_32
    }

    override val height: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.Bullet_32
        Direction.LEFT, Direction.RIGHT -> Config.Bullet_16
    }

    override var x: Int = when (currentDirection){
        Direction.UP, Direction.DOWN ->  move.x + (move.width - this.width) / 2
        Direction.LEFT -> move.x - this.width
        Direction.RIGHT -> move.x + move.width
    }
    override var y: Int = when (currentDirection){
        Direction.UP-> move.y - this.height
        Direction.DOWN -> move.y + move.height
        Direction.LEFT, Direction.RIGHT -> move.y + (move.height - this.height) / 2
    }

    override val attack: Int = 1

    override var isDestroy: Boolean = false


    override val speed: Int = Config.Block64 / 8

    override var badDirection: Direction? = null

    override fun draw() {
        Painter.drawImage(Config.getBulletImage(currentDirection), x, y)
    }

}