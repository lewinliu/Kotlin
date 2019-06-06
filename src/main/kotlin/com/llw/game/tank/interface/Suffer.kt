package com.llw.game.tank.`interface`

import com.llw.game.tank.model.Blast
import com.llw.game.tank.tools.SoundTool

interface Suffer : Destroyable {

    var suffer: Int

    fun notifySuffer(attack: Int): Blast? {

        //被打击音效
        SoundTool.hit()

        this.suffer -= attack

        //生命值不大于0时，死亡
        if (suffer <= 0) {
            this.isDestroy = true
            return Blast(this)
        }

        return null
    }
}