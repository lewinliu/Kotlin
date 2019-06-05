package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.Bullet
import com.llw.game.tank.tools.SoundTool
import com.llw.game.tank.tools.TimeTool

/**
 * 射击功能
 */
interface ShootAble : Movable {

    var lastShotTime: Long

    fun whetherAttack(spaceTime: Long): Boolean {
        if (!TimeTool.timeInterval(lastShotTime, spaceTime)) {
            return false
        }
        lastShotTime = System.currentTimeMillis()
        return true
    }


    fun shootBullet(speed: Int = Config.Block64 / 8): Bullet {
        //发射子弹的声音
        SoundTool.fire()
        return when (this.currentDirection) {
            Direction.UP -> {
                //子弹从坦克中间出现，中间值 = 坦克的x坐标 + （坦克宽度 - 子弹宽度）/2
                Bullet(this, speed)
            }
            Direction.DOWN -> {
                Bullet(this, speed)
            }
            Direction.LEFT -> {
                Bullet(this, speed)
            }
            Direction.RIGHT -> {
                Bullet(this, speed)
            }
        }
    }
}