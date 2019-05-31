package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config

interface Destroyable : BaseView {

    var isDestroy: Boolean

    fun isDestroyable(): Boolean {
        if (this.x <= -this.width || this.y <= -this.height || this.x >= Config.GameWidth || this.y >= Config.GameHeight) this.isDestroy = true
        return this.isDestroy
    }
}