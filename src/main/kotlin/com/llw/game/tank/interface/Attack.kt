package com.llw.game.tank.`interface`

interface Attack : Destroyable {

    val attack: Int

    fun attackNotification() {
        this.isDestroy = true
    }
}