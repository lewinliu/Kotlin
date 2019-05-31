package com.llw.game.tank.`interface`

interface Suffer : Destroyable {

    var suffer: Int

    fun notifySuffer(attack: Int) {
        if (suffer <= 0) {
            this.isDestroy = true
            return
        }
        this.suffer -= attack
    }
}