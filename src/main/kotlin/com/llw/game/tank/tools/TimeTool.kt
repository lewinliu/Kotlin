package com.llw.game.tank.tools

object TimeTool {

    fun timeInterval(lastTime: Long, delayed: Long): Boolean {
        return lastTime + delayed <= System.currentTimeMillis()
    }
}