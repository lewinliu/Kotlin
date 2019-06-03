package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.Enemy
import com.llw.game.tank.model.Tank

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
    fun move(direction: Direction) {

        if (this.currentDirection != direction) {
            //和当前方向不一致时，只调整方向
            this.currentDirection = direction
            println("----------------------------------->   调整方向: ${this.currentDirection}")
            return
        }

        if (this.badDirection == this.currentDirection) {
            println("----------------------------------->   当前方向有障碍：badDirection=${this.badDirection}")
            return
        }

        //移动位置
        when (this.currentDirection) {
            //UP，未越界
            Direction.UP ->
                if (this.y - this.speed >= 0) this.y -= this.speed
                else this.y = 0
            //DOWN，未越界
            Direction.DOWN ->
                if (this.y + this.speed <= Config.GameHeight - this.height) this.y += this.speed
                else this.y = Config.GameHeight - this.height
            //LEFT，未越界
            Direction.LEFT ->
                if (this.x - this.speed >= 0) this.x -= this.speed
                else this.x = 0
            //RIGHT，未越界
            Direction.RIGHT ->
                if (this.x + this.speed <= Config.GameWidth - this.width) this.x += this.speed
                else this.y = Config.GameWidth - this.width
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
     * 通知碰撞
     */
    fun notifyCollision(badDirection: Direction?) {
        this.badDirection = badDirection
    }

}