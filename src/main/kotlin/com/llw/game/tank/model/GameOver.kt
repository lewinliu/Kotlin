package com.llw.game.tank.model

import com.llw.game.tank.`interface`.BaseView
import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Suffer
import com.llw.game.tank.config.Config
import javafx.scene.paint.Color
import javafx.scene.text.Font
import org.itheima.kotlin.game.core.Painter

/**
 * 游戏结束
 */
class GameOver : BaseView {

    override val tier: Int = 4

    override val width: Int = 96
    override val height: Int = 96

    override var x: Int = Config.GameWidth / 2 - width / 2
    override var y: Int = Config.GameHeight / 2 - height / 2

    override fun draw() {
        Painter.drawColor(Color.WHITE, x - width / 2, y - height / 2, width * 2, height * 2 + 32)
        Painter.drawImage(Config.View.GameOver, x, y)
        Painter.drawText(
            "基地爆炸，游戏结束！",
            x-32,
            y - height / 2 +height * 2-16,
            Color.RED,
            Font.font(18.0)
        )
        Painter.drawText(
            "请按Esc键退出！",
            x-16,
            y - height / 2 +height * 2 + 16,
            Color.RED,
            Font.font(18.0)
        )
    }
}