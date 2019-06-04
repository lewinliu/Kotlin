package com.llw.game.tank.model

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 */
class Bullet(private val move: Movable) : AutoMovable, Attack {

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
    override fun move() {
        //移动位置
        when (this.currentDirection) {
            //UP，未越界
            Direction.UP -> this.y -= this.speed
            //DOWN，未越界
            Direction.DOWN -> this.y += this.speed
            //LEFT，未越界
            Direction.LEFT -> this.x -= this.speed
            //RIGHT，未越界
            Direction.RIGHT -> this.x += this.speed
        }
    }

    /**
     * 正在攻击
     */
    override fun onAttacking(block: Blockade): Boolean {
        this.isDestroy = !((move is Tank && block is Tank) || (move is Enemy && block is Enemy))
        println("条件1  =  ${move is Tank && block is Tank}")
        println("条件2  =  ${move is Enemy && block is Enemy}")
        return this.isDestroy
    }

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badDirection: Direction?) {
        //badDirection不为空则发生碰撞，子弹销毁
        this.isDestroy = null != badDirection
        super.notifyCollision(badDirection)
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

}