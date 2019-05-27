package com.llw.game.tank.`interface`

interface Suffer : Destroyable {

    var suffer: Int

    fun onSuffer(assailant: Attack) {
        this.suffer -= assailant.attack
        if (this.suffer < 0) this.isDestroy = true
    }
}