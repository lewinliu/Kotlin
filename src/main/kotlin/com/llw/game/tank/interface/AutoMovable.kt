package com.llw.game.tank.`interface`

import com.llw.game.tank.enum.Direction

interface AutoMovable : Movable {

    fun autoMove() {

        when (currentDirection) {
            Direction.UP -> this.y -= this.speed
            Direction.DOWN -> this.y += this.speed
            Direction.LEFT -> this.x -= this.speed
            Direction.RIGHT -> this.x += this.speed
        }
    }
}