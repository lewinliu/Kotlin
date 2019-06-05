package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.model.Blast
import org.itheima.kotlin.game.core.Composer

interface Suffer : Destroyable {

    var suffer: Int

    fun notifySuffer(attack: Int): Blast? {

        //被打击音效
        try {
            Composer.play(Config.Sound.Sound_hit)
        } catch (e: Exception) {
            println("Suffer.kt error=$e")
        }

        //生命值不大于0时，死亡
        if (suffer <= 0) {
            this.isDestroy = true
            return Blast(this)
        }

        this.suffer -= attack

        return null
    }
}