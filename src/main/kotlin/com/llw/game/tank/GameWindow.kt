package com.llw.game.tank

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.config.Maps
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.*
import com.llw.game.tank.tools.TimeTool
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.lang.Math
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(Config.GameName, Config.GameIcon, Config.GameWidth, Config.GameHeight) {

    private var views = CopyOnWriteArrayList<BaseView>()

    private lateinit var tankP1: Tank
    private lateinit var tankP2: Tank

    private var shotP1: Long = 0
    private var shotP2: Long = 0

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
        keyControl(event)
    }

    override fun onRefresh() {

        //1.遍历所有运动物
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            //2.是障碍物，并且不是自身
            val arr = views.filter { (it is Blockade) and (move != it) }
            //3.按运动物的朝向筛选，并且位置在运动物朝向之前的
            val arr1 = screen(arr, move)
            var badDirection: Direction? = null
            repeat(arr1.size) {
                //4.筛选最靠近运动物的障碍物
                val block: Blockade = when (move.currentDirection) {
                    Direction.UP -> arr1.maxBy { it.y } as Blockade
                    Direction.DOWN -> arr1.minBy { it.y } as Blockade
                    Direction.LEFT -> arr1.maxBy { it.x } as Blockade
                    Direction.RIGHT -> arr1.minBy { it.x } as Blockade
                }
                //5.判断此障碍物是否发生阻挡
                badDirection = move.willCollision(block)
            }
            //6.将结果通知运动物
            move.notifyCollision(badDirection)

            //自动运动
            if (move is AutoMovable) {
                move.notifyCollision(badDirection)
                move.autoMove()
            }
        }

        //销毁
        views.filter { it is Destroyable }.forEach {
            it as Destroyable
            if (it.isDestroyable()) {
                views.remove(it)
            }
        }

        //攻击
        views.filter { it is Attack && it is AutoMovable }.forEach { attack ->
            attack as AutoMovable
            val arr = views.filter { it is Suffer }
            val arr1 = screen(arr,attack)
            //被攻击
            repeat(arr1.size) {

            }
        }

    }

    /**
     * 移动物 移动方向上的 障碍物筛选
     */
    private fun screen(arr: List<BaseView>, move: Movable): List<BaseView> {
        return arr.filter {
            when (move.currentDirection) {
                //上下筛选 Math.abs(it.x - move.x)>0 && Math.abs(it.x - move.x)<move.width
                Direction.UP -> Math.abs(it.x - move.x) >= 0 && Math.abs(it.x - move.x) < move.width && move.y > it.y
                Direction.DOWN -> Math.abs(it.x - move.x) >= 0 && Math.abs(it.x - move.x) < move.width && move.y < it.y
                //左右筛选 Math.abs(it.y - move.y)>0 && Math.abs(it.y - move.y)<move.height
                Direction.LEFT -> Math.abs(it.y - move.y) >= 0 && Math.abs(it.y - move.y) < move.height && move.x > it.x
                Direction.RIGHT -> Math.abs(it.y - move.y) >= 0 && Math.abs(it.y - move.y) < move.height && move.x < it.x
            }
        }
    }

    /**
     * 添加地图
     */
    private fun initMap(mapList: Array<Array<Char>>) {
        views.clear()

        //添加坦克
        tankP1 = Tank(2 * Config.Block64, 12 * Config.Block64)
        tankP1.speed = 8
        views.add(tankP1)

        tankP2 = Tank(10 * Config.Block64, 12 * Config.Block64, true)
        tankP2.speed = 16
        views.add(tankP2)

        //添加其他
        for (y in 0 until mapList.size) {
            for (x in 0 until mapList[y].size) {
                when (mapList[y][x]) {
                    '砖' -> views.add(Wall(x * Config.Block64, y * Config.Block64))
                    '铁' -> views.add(Steel(x * Config.Block64, y * Config.Block64))
                    '草' -> views.add(Grass(x * Config.Block64, y * Config.Block64))
                    '水' -> views.add(Water(x * Config.Block64, y * Config.Block64))
                    '基' -> views.add(Camp(x * Config.Block64, y * Config.Block64))
                }
            }
        }
    }

    /**
     * 按键操作
     */
    private fun keyControl(event: KeyEvent) {
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
                if (!TimeTool.whetherAttack(shotP1, 500)) {
                    return
                }
                shotP1 = System.currentTimeMillis()
                val bullet = tankP1.shootBullet()
                views.add(bullet)
            }
            //P2
            KeyCode.UP -> {
                tankP2.move(Direction.UP)
            }
            KeyCode.LEFT -> {
                tankP2.move(Direction.LEFT)
            }
            KeyCode.DOWN -> {
                tankP2.move(Direction.DOWN)
            }
            KeyCode.RIGHT -> {
                tankP2.move(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                if (!TimeTool.whetherAttack(shotP2, 500)) {
                    return
                }
                shotP2 = System.currentTimeMillis()
                val bullet = tankP2.shootBullet()
                views.add(bullet)
            }
            else -> {
                println("无操作...")
            }

        }
    }

}