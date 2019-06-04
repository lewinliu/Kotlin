package com.llw.game.tank.config

import com.llw.game.tank.enum.Direction

object Config {

    const val GameName = "坦克大战"

    const val GameIcon = "img/icon.jpg"

    const val Block64 = 64

    const val GameWidth = Block64 * 13

    const val GameHeight = Block64 * 13

    object View{
        
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
                Direction.UP -> Bullet_U
                Direction.DOWN -> Bullet_D
                Direction.LEFT -> Bullet_L
                Direction.RIGHT -> Bullet_R
            }
        }

        /**
         * 根据索引获取爆炸图片
         */
        fun getBlastImage(imgIndex: Int): String {
            return "/img/blast_$imgIndex.png"
        }

        // 敌方坦克 type = 1
        private const val Enemy_1_U = "/img/enemy_1_u.gif"

        private const val Enemy_1_D = "/img/enemy_1_d.gif"

        private const val Enemy_1_L = "/img/enemy_1_l.gif"

        private const val Enemy_1_R = "/img/enemy_1_r.gif"

        // 敌方坦克 type = 2
        private const val Enemy_2_U = "/img/enemy_2_u.gif"

        private const val Enemy_2_D = "/img/enemy_2_d.gif"

        private const val Enemy_2_L = "/img/enemy_2_l.gif"

        private const val Enemy_2_R = "/img/enemy_2_r.gif"

        // 敌方坦克 type = 3
        private const val Enemy_3_U = "/img/enemy_3_u.gif"

        private const val Enemy_3_D = "/img/enemy_3_d.gif"

        private const val Enemy_3_L = "/img/enemy_3_l.gif"

        private const val Enemy_3_R = "/img/enemy_3_r.gif"

        // 敌方坦克 type = 4
        private const val Enemy_4_U = "/img/enemy_4_u.gif"

        private const val Enemy_4_D = "/img/enemy_4_d.gif"

        private const val Enemy_4_L = "/img/enemy_4_l.gif"

        private const val Enemy_4_R = "/img/enemy_4_r.gif"

        // 敌方坦克 type = 5
        private const val Enemy_5_U = "/img/enemy_5_u.gif"

        private const val Enemy_5_D = "/img/enemy_5_d.gif"

        private const val Enemy_5_L = "/img/enemy_5_l.gif"

        private const val Enemy_5_R = "/img/enemy_5_r.gif"

        // 敌方坦克 type = 6
        private const val Enemy_6_U = "/img/enemy_6_u.gif"

        private const val Enemy_6_D = "/img/enemy_6_d.gif"

        private const val Enemy_6_L = "/img/enemy_6_l.gif"

        private const val Enemy_6_R = "/img/enemy_6_r.gif"

        /**
         * 获取敌方坦克
         */
        fun getEnemyTankImage(type: Int = 1, direction: Direction): String {

            return when (type) {
                1 -> when (direction) {
                    Direction.UP -> Enemy_1_U
                    Direction.DOWN -> Enemy_1_D
                    Direction.LEFT -> Enemy_1_L
                    Direction.RIGHT -> Enemy_1_R
                }
                2 -> when (direction) {
                    Direction.UP -> Enemy_2_U
                    Direction.DOWN -> Enemy_2_D
                    Direction.LEFT -> Enemy_2_L
                    Direction.RIGHT -> Enemy_2_R
                }
                3 -> when (direction) {
                    Direction.UP -> Enemy_3_U
                    Direction.DOWN -> Enemy_3_D
                    Direction.LEFT -> Enemy_3_L
                    Direction.RIGHT -> Enemy_3_R
                }
                4 -> when (direction) {
                    Direction.UP -> Enemy_4_U
                    Direction.DOWN -> Enemy_4_D
                    Direction.LEFT -> Enemy_4_L
                    Direction.RIGHT -> Enemy_4_R
                }
                5 -> when (direction) {
                    Direction.UP -> Enemy_5_U
                    Direction.DOWN -> Enemy_5_D
                    Direction.LEFT -> Enemy_5_L
                    Direction.RIGHT -> Enemy_5_R
                }
                6 -> when (direction) {
                    Direction.UP -> Enemy_6_U
                    Direction.DOWN -> Enemy_6_D
                    Direction.LEFT -> Enemy_6_L
                    Direction.RIGHT -> Enemy_6_R
                }
                else -> ""
            }
        }
    }




    /**
     * 声音
     */
    object Sound {
        const val Sound_add = "/sound/add.wav"
        const val Sound_blast = "/sound/blast.wav"
        const val Sound_fire = "/sound/fire.wav"
        const val Sound_hit = "/sound/hit.wav"
        const val Sound_start = "/sound/start.wav"
    }


}