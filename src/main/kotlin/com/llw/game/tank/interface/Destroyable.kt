package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config

interface Destroyable : BaseView {

    var isDestroy: Boolean

    fun isDestroyable(): Boolean {
        if (this.x < 0 || this.y < 0 || this.x > Config.GameWidth || this.y > Config.GameHeight) return false
        return isDestroy
    }
}