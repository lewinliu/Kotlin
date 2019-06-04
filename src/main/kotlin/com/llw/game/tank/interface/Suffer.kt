package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Composer

interface Suffer : Destroyable {

    var suffer: Int

    fun notifySuffer(attack: Int) {
        if (suffer <= 0) {
            this.isDestroy = true
            return
        }
        this.suffer -= attack

        try {
            Composer.play(Config.Sound.Sound_hit)
        } catch (e: Exception) {
            println("Suffer.kt error=$e")
        }


    }
}