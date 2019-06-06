package com.llw.game.tank.model

import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.config.Config
import javafx.scene.paint.Color
import javafx.scene.text.Font
import org.itheima.kotlin.game.core.Painter

/**
 * 选项
 */
class Option(viewX: Int, viewY: Int) : Blockade {

    override val tier: Int = 1

    override val width: Int = 192
    override val height: Int = 192

    override var x: Int = Config.Block64 * viewX
    override var y: Int = Config.Block64 * viewY

    private val selectW: Int = 32
    private val selectH: Int = 32

    private var space: Int = height / 3

    /**
     * 控制选项：0=单人，1=多人，2=地图编辑
     */
    private var index: Int = 0

    private val selectX: Int = x + width + selectW

    private var selectY: Int = y + selectH / 2

    override fun draw() {
        Painter.drawImage(Config.View.Option, x, y)
        Painter.drawImage(Config.View.Select, selectX, selectY + space * index)
        Painter.drawText(
            "U=单人游戏    I=双人游戏    O=地图编辑    P=开始游戏",
            x - Config.Block64,
            Config.GameHeight - Config.Block64,
            Color.YELLOWGREEN,
            Font.font(16.0)
        )
        Painter.drawText(
            "P1: 移动= w a s d,开火=j    P2: 移动= ↑ ← ↓ →,开火=enter",
            x - Config.Block64,
            Config.GameHeight - Config.Block64 / 2,
            Color.YELLOWGREEN,
            Font.font(16.0)
        )
    }


    fun setIndex(value: Int) {
        index += value
        if (index < 0) index = 0
        if (index > 2) index = 2
    }

    fun getIndex(): Int {
        return index
    }
}