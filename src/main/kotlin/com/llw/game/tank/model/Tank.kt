package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.`interface`.ShootAble
import com.llw.game.tank.`interface`.Suffer
import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 坦克
 */
class Tank(viewX: Int, viewY: Int, var isTwoPlay: Boolean = false) : Movable, Blockade, Suffer, ShootAble {

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

    override fun draw() {
        Painter.drawImage(Config.getTankImage(currentDirection, isTwoPlay), x, y)
    }

    /**
     * 通知碰撞
     */
    override fun notifyCollision(badDirection: Direction?) {
        this.badDirection = badDirection
    }
}