package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Born
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 出生效果
 */
class Appear(move: Movable) : Born {

    override val width: Int = move.width
    override val height: Int = move.height

    override var x: Int = move.x
    override var y: Int = move.y

    override var isDestroy: Boolean = false

    override var isFirstAppear: Boolean = true

    private val imgArray =
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4)
    private var imgIndex = 0

    override fun draw() {
        Painter.drawImage(Config.View.getBornImage(imgArray[imgIndex]), x, y)
        if (imgIndex < imgArray.size - 1) imgIndex++ else {
            this.isDestroy = true
            this.isFirstAppear = false
        }
    }
}