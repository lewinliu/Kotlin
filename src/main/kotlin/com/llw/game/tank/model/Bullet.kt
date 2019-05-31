package com.llw.game.tank.model

import com.llw.game.tank.`interface`.*
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

    override var x: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> move.x + (move.width - this.width) / 2
        Direction.LEFT -> move.x - this.width
        Direction.RIGHT -> move.x + move.width
    }
    override var y: Int = when (currentDirection) {
        Direction.UP -> move.y - this.height
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

    /**
     * 重写移动方法，修改移动范围
     */
    override fun move(direction: Direction) {
        //移动位置
        when (this.currentDirection) {
            //UP，未越界
            Direction.UP -> if (this.y - this.speed >= -this.height) this.y -= this.speed
            //DOWN，未越界
            Direction.DOWN -> if (this.y + this.speed <= Config.GameHeight) this.y += this.speed
            //LEFT，未越界
            Direction.LEFT -> if (this.x - this.speed >= -this.width) this.x -= this.speed
            //RIGHT，未越界
            Direction.RIGHT -> if (this.x + this.speed <= Config.GameWidth) this.x += this.speed
        }
    }

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badDirection: Direction?) {
        if (null != badDirection) {
            this.isDestroy = true
        }
        super.notifyCollision(badDirection)
    }

    /**
     * 自动前进
     */
    override fun autoMove() {
        if (this.badDirection == this.currentDirection) {
            return
        }
        move(this.currentDirection)
    }

}