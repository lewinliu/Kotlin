package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.enum.Direction

interface AutoMovable : Movable {

    fun autoMove() {
        if (this.badDirection == this.currentDirection) {
            return
        }
        move(this.currentDirection)
    }

    /**
     * 移动
     */
    override fun move(direction: Direction) {
        //移动位置
        when (this.currentDirection) {
            //UP，未越界
            Direction.UP -> if (this.y >= -this.speed) this.y -= this.speed
            else this.y = -this.speed
            //DOWN，未越界
            Direction.DOWN -> if (this.y + this.speed <= Config.GameHeight) this.y += this.speed
            else this.y = Config.GameHeight - this.height
            //LEFT，未越界
            Direction.LEFT -> if (this.x >= -this.speed) this.x -= this.speed
            else this.x = -this.speed
            //RIGHT，未越界
            Direction.RIGHT -> if (this.x + this.speed <= Config.GameWidth) this.x += this.speed
            else this.x = Config.GameWidth
        }
    }
}