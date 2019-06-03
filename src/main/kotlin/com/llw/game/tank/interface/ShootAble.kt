package com.llw.game.tank.`interface`

import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.Bullet
import com.llw.game.tank.tools.TimeTool

/**
 * 射击功能
 */
interface ShootAble:Movable {

    var lastShotTime: Long

    fun whetherAttack(time: Long): Boolean{
        if (!TimeTool.timeInterval(lastShotTime, time)) {
            return false
        }
        lastShotTime = System.currentTimeMillis()
        return true
    }

    fun shootBullet(): Bullet {
        return when (this.currentDirection) {
            Direction.UP -> {
                //子弹从坦克中间出现，中间值 = 坦克的x坐标 + （坦克宽度 - 子弹宽度）/2
                Bullet(this)
            }
            Direction.DOWN -> {
                Bullet(this)
            }
            Direction.LEFT -> {
                Bullet(this)
            }
            Direction.RIGHT -> {
                Bullet(this)
            }
        }
    }
}