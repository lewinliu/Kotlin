package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.model.Appear
import org.itheima.kotlin.game.core.Composer

interface Born : Destroyable {

    var isFirstAppear: Boolean

    fun firstAppear(move: Movable) {
        if (isFirstAppear) {
            try {
                Composer.play(Config.Sound.Sound_start)
            } catch (e: Exception) {
                println("Born.kt error=$e")
            }
        } else {
            isDestroy = true
        }
    }

}