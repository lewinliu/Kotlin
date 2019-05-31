package com.llw.game.tank.config

import com.llw.game.tank.enum.Direction

object Config {

    const val GameName = "坦克大战"

    const val GameIcon = "img/icon.jpg"

    const val Block64 = 64

    const val GameWidth = Block64 * 13

    const val GameHeight = Block64 * 13

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


    //子弹大小
    const val Bullet_16 = 16
    const val Bullet_32 = 32

    //子弹
    private const val Bullet_U = "/img/shot_top.gif"

    private const val Bullet_D = "/img/shot_bottom.gif"

    private const val Bullet_L = "/img/shot_left.gif"

    private const val Bullet_R = "/img/shot_right.gif"

    /**
     * 根据方向获取子弹图片
     */
    fun getBulletImage(direction: Direction): String {
        return when (direction) {
            Direction.UP ->  Bullet_U
            Direction.DOWN -> Bullet_D
            Direction.LEFT -> Bullet_L
            Direction.RIGHT -> Bullet_R
        }
    }

    /**
     * 根据索引获取爆炸图片
     */
    fun getBlastImage(imgIndex:Int): String {
        return "/img/blast_$imgIndex.png"
    }

}