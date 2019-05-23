package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 坦克
 */
class Tank(override var x: Int, override var y: Int, var isTwoPlay: Boolean = false) : Movable, Blockade {

    override val width: Int = Config.Block64

    override val height: Int = Config.Block64

    override var speed: Int = Config.Block64

    override var currentDirection: Direction = Direction.UP

    override var badDirection: Direction? = null

    override fun draw() {
        println("isTwoPlay = $isTwoPlay,   this.x = ${this.x.toFloat() / this.width.toFloat()}     this.y = ${this.y.toFloat() / this.height.toFloat()}")
        Painter.drawImage(Config.getTankImage(currentDirection, isTwoPlay), x, y)
    }

    fun shootBullet(): Bullet {
        return when (currentDirection) {
            Direction.UP -> {
                //子弹从坦克中间出现，中间值 = 坦克的x坐标 + （坦克宽度 - 子弹宽度）/2
                Bullet(currentDirection, this.x + (this.width - Config.Bullet_16) / 2, this.y)
            }
            Direction.DOWN -> {
                Bullet(currentDirection, this.x + (this.width - Config.Bullet_16) / 2, this.y + this.height)
            }
            Direction.LEFT -> {
                Bullet(currentDirection, this.x, this.y + (this.height - Config.Bullet_16) / 2)
            }
            Direction.RIGHT -> {
                Bullet(currentDirection, this.x + this.width, this.y + (this.height - Config.Bullet_16) / 2)
            }
        }
    }
}