package com.llw.game.tank.tools

object TimeTool {

    fun whetherAttack(lastTime: Long, delayed: Long): Boolean {
        return lastTime + delayed <= System.currentTimeMillis()
    }
}