package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction

/**
 * 可移动的
 */
interface Movable : BaseView {

    /**
     * 当前的方向
     */
    var currentDirection: Direction

    /**
     * 移动速度
     */
    val speed: Int

    /**
     * 不能走的方向
     */
    var badDirection: Direction?

    /**
     * 移动
     */
    fun move() {
        val isOverstep = isOverstep()
        //移动位置
        when (this.currentDirection) {
            Direction.UP ->
                if (isOverstep) this.y = 0
                else this.y -= this.speed
            Direction.DOWN ->
                if (isOverstep) this.y = Config.GameHeight - this.height
                else this.y += this.speed
            Direction.LEFT ->
                if (isOverstep) this.x = 0
                else this.x -= this.speed
            Direction.RIGHT ->
                if (isOverstep) this.x = Config.GameWidth - this.width
                else this.x += this.speed
        }
    }

    /**
     * 障碍的方向，返回null表示无障碍
     */
    fun willCollision(block: Blockade): Direction? {
        when (this.currentDirection) {
            //UP，无障碍
            Direction.UP -> {
                if (this.y - this.speed >= block.y + block.height) return null
            }
            //DOWN，无障碍
            Direction.DOWN -> {
                if (this.y + this.height + this.speed <= block.y) return null
            }
            //LEFT，无障碍
            Direction.LEFT -> {
                if (this.x - this.speed >= block.x + block.width) return null
            }
            //RIGHT，无障碍
            Direction.RIGHT -> {
                if (this.x + this.width + this.speed <= block.x) return null
            }
        }
        return this.currentDirection
    }

    /**
     * 越界判断
     */
    fun isOverstep(): Boolean {
        return when (this.currentDirection) {
            //UP，越界
            Direction.UP -> this.y - this.speed < 0
            //DOWN，越界
            Direction.DOWN -> this.y + this.speed > Config.GameHeight - this.height
            //LEFT，越界
            Direction.LEFT -> this.x - this.speed < 0
            //RIGHT，越界
            Direction.RIGHT -> this.x + this.speed > Config.GameWidth - this.width
        }
    }

    /**
     * 通知碰撞
     */
    fun notifyCollision(badDirection: Direction?) {
        this.badDirection = if (isOverstep()) this.currentDirection else badDirection
    }

}