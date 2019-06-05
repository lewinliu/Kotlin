package com.llw.game.tank.`interface`

import com.llw.game.tank.config.Config
import com.llw.game.tank.tools.SoundTool
import org.itheima.kotlin.game.core.Painter

open class TankBorn {

    var isFirstAppear: Boolean = true

    private val appearArray: ArrayList<Int> = arrayListOf()

    init {
        for (i in 1..3) {
            appearArray.add(1)
            appearArray.add(2)
            appearArray.add(3)
            appearArray.add(4)
        }
        println("appearArray size = ${appearArray.size}")
    }

    var imgIndex = 0

    fun drawBorn(view: Movable): Boolean {
        //出现效果
        if (isFirstAppear) {
            Painter.drawImage(Config.View.getBornImage(appearArray[imgIndex]), view.x, view.y)
            //播放出生音效
            if (imgIndex == 0) SoundTool.add()
            //出生效果图索引
            if (imgIndex < appearArray.size - 1) imgIndex++ else isFirstAppear = false
        }
        //坦克
        return isFirstAppear
    }

}