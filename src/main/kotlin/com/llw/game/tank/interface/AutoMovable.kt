package com.llw.game.tank.`interface`

import com.llw.game.tank.enum.Direction

interface AutoMovable : Movable {

    fun autoMove() {
        if (currentDirection == badDirection){
            println("autoMove() ---> badDirection $badDirection")
            return
        }
        println("Bullet：x=$x，y=$y")
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }
}