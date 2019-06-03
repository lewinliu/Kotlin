package com.llw.game.tank.model

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

/**
 * 敌方坦克
 */
class Enemy(viewX: Int, viewY: Int, private val tankType: Int) : AutoMovable, Blockade, Suffer, ShootAble {


    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = width * viewX
    override var y: Int = height * viewY

    override var suffer: Int = 10

    override var isDestroy: Boolean = false

    override var speed: Int = Config.Block64 / 8

    override var currentDirection: Direction = Direction.DOWN

    override var badDirection: Direction? = null

    override var lastShotTime: Long = 0

    override fun draw() {
        Painter.drawImage(Config.getEnemyTankImage(tankType, this.currentDirection), x, y)
    }

    /**
     * 自动运动
     */
    override fun autoMove() {
        //当前方向不能移动则转向
        if (this.badDirection == this.currentDirection) {
            this.currentDirection = this.randomDirection(Direction.UP)
            return
        }
        move(this.currentDirection)
    }

    /**
     * 随机方向
     */
    private fun randomDirection(currentDirection: Direction): Direction {

        val random = (0..3).shuffled().last()

        val randomDirection = when (random) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> currentDirection
        }

//        val randomDirection = when (Random(3).nextInt()) {
//            0 -> Direction.UP
//            1 -> Direction.DOWN
//            2 -> Direction.LEFT
//            3 -> Direction.RIGHT
//            else -> currentDirection
//        }
        println("random:$random,   random:$randomDirection,   currentDirection:$currentDirection")
        return if (randomDirection != currentDirection) randomDirection else randomDirection(randomDirection)
    }

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badDirection: Direction?) {
        this.badDirection = badDirection
    }


}