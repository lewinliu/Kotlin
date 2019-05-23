package com.llw.game.tank

import com.llw.game.tank.`interface`.BaseView
import com.llw.game.tank.`interface`.Blockade
import com.llw.game.tank.`interface`.Movable
import com.llw.game.tank.config.Config
import com.llw.game.tank.config.Maps
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class GameWindow : Window(Config.GameName, Config.GameIcon, Config.GameWidth, Config.GameHeight) {

    var views = ArrayList<BaseView>()

    private lateinit var tankP1: Tank

    override fun onCreate() {
        initMap(Maps.Map1)
    }


    override fun onDisplay() {
        //打印视图
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            //P1
            KeyCode.W -> {
                tankP1.move(Direction.UP)
            }
            KeyCode.A -> {
                tankP1.move(Direction.LEFT)
            }
            KeyCode.S -> {
                tankP1.move(Direction.DOWN)
            }
            KeyCode.D -> {
                tankP1.move(Direction.RIGHT)
            }
            KeyCode.J -> {
            }
            //P2
            KeyCode.UP -> {
            }
            KeyCode.LEFT -> {
            }
            KeyCode.DOWN -> {
            }
            KeyCode.RIGHT -> {
            }
            KeyCode.ENTER -> {
            }
            else -> {
                println("无操作...")
            }
        }
    }

    override fun onRefresh() {
        //1.遍历所有移动物
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            //2.是障碍物，并且不是自身
            val arr = views.filter { (it is Blockade) and (move != it) }
            //3.按移动物的朝向筛选，并且位置在移动物朝向之前的
            val arr1 = arr.filter {
                when (move.currentDirection) {
                    Direction.UP -> move.x == it.x && move.y > it.y
                    Direction.DOWN -> move.x == it.x && move.y < it.y
                    Direction.LEFT -> move.y == it.y && move.x > it.x
                    Direction.RIGHT -> move.y == it.y && move.x < it.x
                }
            }
            var badDirection: Direction? = null
            repeat(arr1.size) {
                //4.筛选最靠近移动物的障碍物
                val block: Blockade = when (move.currentDirection) {
                    Direction.UP -> arr1.maxBy { it.y } as Blockade
                    Direction.DOWN -> arr1.minBy { it.y } as Blockade
                    Direction.LEFT -> arr1.maxBy { it.x } as Blockade
                    Direction.RIGHT -> arr1.minBy { it.x } as Blockade
                }
                //5.判断此障碍物是否发生阻挡
                badDirection = move.willCollision(block)
            }
            //6.将结果通知移动物
            move.notifyCollision(badDirection)
        }
    }

    /**
     * 添加地图
     */
    private fun initMap(mapList: Array<Array<Char>>) {
        views.clear()

        //添加坦克
        tankP1 = Tank(10, 12)
        views.add(tankP1)

        //添加其他
        for (y in 0 until mapList.size) {
            for (x in 0 until mapList[y].size) {
                when (mapList[y][x]) {
                    '砖' -> views.add(Wall(x, y))
                    '铁' -> views.add(Steel(x, y))
                    '草' -> views.add(Grass(x, y))
                    '水' -> views.add(Water(x, y))
                    '基' -> views.add(Camp(x, y))
                }
            }
        }
    }

}