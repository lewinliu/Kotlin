package com.llw.game.tank.config

import com.llw.game.tank.enum.Direction

object Config {

    const val GameName = "坦克大战"

    const val GameIcon = "img/icon.jpg"

    const val Block = 64

    const val Horizontal = 13

    const val Vertical = 13

    const val GameWidth = Block * Horizontal

    const val GameHeight = Block * Vertical

    // 建筑物
    const val Grass = "/img/grass.gif"

    const val Steel = "/img/steel.gif"

    const val Wall = "/img/wall.gif"

    const val Water = "/img/water.gif"

    const val Camp = "/img/camp.gif"

    // P1 坦克，四个方向
    private const val Tank_P1_U = "/img/tank_u.gif"

    private const val Tank_P1_D = "/img/tank_d.gif"

    private const val Tank_P1_L = "/img/tank_l.gif"

    private const val Tank_P1_R = "/img/tank_r.gif"

    // P2 坦克
    private const val Tank_P2_U = "/img/tank_2_u.gif"

    private const val Tank_P2_D = "/img/tank_2_d.gif"

    private const val Tank_P2_L = "/img/tank_2_l.gif"

    private const val Tank_P2_R = "/img/tank_2_r.gif"

    /**
     * 根据方向获取坦克图片
     */
    fun getTankImage(direction: Direction, isTwoPlay: Boolean = false): String {

        return if (!isTwoPlay) when (direction) {
            Direction.UP -> Tank_P1_U
            Direction.DOWN -> Tank_P1_D
            Direction.LEFT -> Tank_P1_L
            Direction.RIGHT -> Tank_P1_R
        } else when (direction) {
            Direction.UP -> Tank_P2_U
            Direction.DOWN -> Tank_P2_D
            Direction.LEFT -> Tank_P2_L
            Direction.RIGHT -> Tank_P2_R
        }
    }

}