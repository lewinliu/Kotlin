package com.llw.game.tank.model

import com.llw.game.tank.`interface`.BaseView
import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Suffer
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 游戏结束
 */
class GameOver : BaseView {

    override val tier: Int = 4

    override val width: Int = Config.GameWidth
    override val height: Int = Config.GameHeight

    override var x: Int = Config.GameWidth
    override var y: Int = Config.GameHeight

    override fun draw() {
        Painter.drawImage(Config.View.GameOver, x, y)
    }
}