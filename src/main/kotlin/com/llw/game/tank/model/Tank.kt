package com.llw.game.tank.model

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 坦克
 */
class Tank(viewX: Int, viewY: Int, var isTwoPlay: Boolean = false) : Movable, Blockade, Suffer, ShootAble {//, Born

    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = width * viewX
    override var y: Int = height * viewY

    override var suffer: Int = 10

    override var isDestroy: Boolean = false

    override var speed: Int = Config.Block64 / 4

    override var currentDirection: Direction = Direction.UP

    override var badDirection: Direction? = null

    override var lastShotTime: Long = 0

    //override var isFirstAppear: Boolean = true

    override fun draw() {
        /*if (!isFirstAppear)*/ Painter.drawImage(Config.View.getTankImage(currentDirection, isTwoPlay), x, y)
    }

    fun moveTank(direction: Direction) {
        if (this.currentDirection != direction) {
            //和当前方向不一致时，只调整方向
            this.currentDirection = direction
            println("-----------------------------------> Tank   调整方向: ${this.currentDirection}")
            return
        }

        if (this.badDirection == this.currentDirection) {
            println("-----------------------------------> Tank   当前方向有障碍：badDirection=${this.badDirection}")
            return
        }
        move()
    }
}