package com.llw.game.tank.model

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 敌方坦克
 */
class Enemy(viewX: Int, viewY: Int, private val tankType: Int) : AutoMovable, Blockade, Suffer, ShootAble, TankBorn() {


    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = width * viewX
    override var y: Int = height * viewY

    override var suffer: Int = 10

    override var isDestroy: Boolean = false

    override var speed: Int = Config.Block64 / 32

    override var currentDirection: Direction = Direction.DOWN

    override var badDirection: Direction? = null

    override var lastShotTime: Long = 0

    override fun draw() {
        //刚出现时绘制出生效果，然后才绘制坦克
        if (!drawBorn(this)) Painter.drawImage(Config.View.getEnemyTankImage(tankType, this.currentDirection), x, y)
    }

    /**
     * 自动运动
     */
    override fun autoMove() {
        //当前方向不能移动
        if (this.badDirection == this.currentDirection) {
            println("----------------------------------->   当前方向有障碍：badDirection=${this.badDirection}")
            //随机调整方向
            this.currentDirection = this.randomDirection(this.badDirection!!)
            println("----------------------------------->   调整方向: ${this.currentDirection}")
            return
        }
        move()
    }

    /**
     * 随机方向
     */
    private fun randomDirection(badDirection: Direction): Direction {

        val randomDirection = when ((0..3).shuffled().last()) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> badDirection
        }
        return if (randomDirection != badDirection) randomDirection else randomDirection(badDirection)
    }

}