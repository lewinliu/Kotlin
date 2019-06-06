package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Destroyable
import com.llw.game.tank.`interface`.Suffer
import com.llw.game.tank.config.Config
import com.llw.game.tank.tools.SoundTool
import org.itheima.kotlin.game.core.Painter

/**
 * 爆炸效果
 */
class Blast(suffer: Suffer) : Destroyable {

    override val tier: Int = 2

    override val width: Int = Config.Block64
    override val height: Int = Config.Block64

    override var x: Int = suffer.x
    /*when (move.currentDirection) {
        Direction.UP, Direction.DOWN -> move.x + (move.width - this.width) / 2
        Direction.LEFT -> move.x - this.width / 2
        Direction.RIGHT -> move.x + move.width - this.width / 2
    }*/
    override var y: Int = suffer.y
    /*when (move.currentDirection) {
        Direction.UP -> move.y - this.height / 2
        Direction.DOWN -> move.y + move.height - this.height / 2
        Direction.LEFT, Direction.RIGHT -> move.y + (move.height - this.height) / 2
    }*/

    override var isDestroy: Boolean = false

    private val imgArray = 1..31
    private var imgIndex = 1

    override fun draw() {
        Painter.drawImage(Config.View.getBlastImage(imgIndex), x, y)
        //爆炸音效
        if (imgIndex ==1) SoundTool.blast()
        if (imgIndex in imgArray) imgIndex++ else this.isDestroy = true
    }
}