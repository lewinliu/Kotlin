package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Attack
import com.llw.game.tank.`interface`.AutoMovable
import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 */
class Bullet(private val move: Movable) : AutoMovable, Attack {

    override var currentDirection: Direction = move.currentDirection

    override val width: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.View.Bullet_16
        Direction.LEFT, Direction.RIGHT -> Config.View.Bullet_32
    }

    override val height: Int = when (currentDirection) {
        Direction.UP, Direction.DOWN -> Config.View.Bullet_32
        Direction.LEFT, Direction.RIGHT -> Config.View.Bullet_16
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
        Painter.drawImage(Config.View.getBulletImage(currentDirection), x, y)
    }

    /**
     * 正在攻击
     */
    override fun onAttacking(block: Blockade): Boolean {
        this.isDestroy = !((move is Tank && block is Tank) || (move is Enemy && block is Enemy))
        return this.isDestroy
    }

    /**
     * 自动前进
     */
    override fun autoMove() {
        if (this.badDirection == this.currentDirection) {
            return
        }
        move()
    }

    /**
     * 障碍的方向，返回null表示无障碍
     */
    override fun willCollision(block: Blockade): Direction? {
        when (this.currentDirection) {
            //UP，无障碍
            Direction.UP -> {
                if (this.y >= block.y + block.height) return null
            }
            //DOWN，无障碍
            Direction.DOWN -> {
                if (this.y + this.height <= block.y) return null
            }
            //LEFT，无障碍
            Direction.LEFT -> {
                if (this.x >= block.x + block.width) return null
            }
            //RIGHT，无障碍
            Direction.RIGHT -> {
                if (this.x + this.width <= block.x) return null
            }
        }
        return this.currentDirection
    }

    /**
     * 移动
     */
    override fun move() {
        val isOverstep = isOverstep()
        //移动位置
        when (this.currentDirection) {
            Direction.UP ->
                if (isOverstep) this.y = - this.height
                else this.y -= this.speed
            Direction.DOWN ->
                if (isOverstep) this.y = Config.GameHeight
                else this.y += this.speed
            Direction.LEFT ->
                if (isOverstep) this.x = - this.width
                else this.x -= this.speed
            Direction.RIGHT ->
                if (isOverstep) this.x = Config.GameWidth
                else this.x += this.speed
        }
    }

    /**
     * 越界判断
     */
    override fun isOverstep(): Boolean {
        return when (this.currentDirection) {
            //UP，越界
            Direction.UP -> this.y <=  - this.height
            //DOWN，越界
            Direction.DOWN -> this.y >= Config.GameHeight
            //LEFT，越界
            Direction.LEFT -> this.x <=  - this.width
            //RIGHT，越界
            Direction.RIGHT -> this.x >= Config.GameWidth
        }
    }

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badDirection: Direction?) {

        super.notifyCollision(badDirection)
        //发生碰撞销毁子弹
        this.isDestroy = null != this.badDirection

    }

}