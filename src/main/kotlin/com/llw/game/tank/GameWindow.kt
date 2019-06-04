package com.llw.game.tank

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.config.Maps
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.*
import com.llw.game.tank.tools.ViewCollection
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class GameWindow : Window(Config.GameName, Config.GameIcon, Config.GameWidth, Config.GameHeight) {

    private val collection = ViewCollection()
    private lateinit var tankP1: Tank
    private lateinit var tankP2: Tank

    //创建地图
    override fun onCreate() = addMap(Maps.Map2)

    //打印地图
    override fun onDisplay() = collection.draw()

    //按键监听
    override fun onKeyPressed(event: KeyEvent) = keyControl(event)

    //耗时操作
    override fun onRefresh() {

        //1.遍历所有运动物
        collection.filter { it is Movable }.forEach { move ->
            move as Movable
            //2.位置在运动物朝向之前的障碍物
            val arr1 = viewFilter(move).filter { (it is Blockade) and (move != it) }
            var badDirection: Direction? = null

            repeat(arr1.size) {
                //3.筛选最靠近运动物的障碍物
                val block = nearestFilter(arr1, move) as Blockade

                //4.判断此障碍物是否发生阻挡
                badDirection = move.willCollision(block)

                //当 攻 遇到 受
                if (null != badDirection && move is Attack && !move.isDestroyable() && block is Suffer) {
                    //子弹接受攻击通知
                    if (move.onAttacking(block)) {
                        //打击效果
                        collection.add(Blast(move))
                        //障碍接受被攻击通知
                        block.notifySuffer(move.attack)
                    } else {
                        badDirection = null
                    }
                }
            }
            //5.将结果通知运动物
            move.notifyCollision(badDirection)

            //敌方坦克自动射击
            if (move is Enemy) {
                //一秒钟自动发射一枚子弹
                if (move.whetherAttack(1000)) collection.add(move.shootBullet())
            }
            //自动运动
            if (move is AutoMovable) {
                move.autoMove()
            }
        }

        //销毁
        collection.remove { view ->
            if (view is Destroyable) {
                return@remove view.isDestroyable()
            }
            return@remove false
        }
    }

    /**
     * 筛选最靠近运动物的View
     */
    private fun nearestFilter(list: List<BaseView>, move: Movable): BaseView? {
        return when (move.currentDirection) {
            Direction.UP -> list.maxBy { it.y }
            Direction.DOWN -> list.minBy { it.y }
            Direction.LEFT -> list.maxBy { it.x }
            Direction.RIGHT -> list.minBy { it.x }
        }
    }

    /**
     * 移动物 移动方向上的 障碍物筛选
     */
    private fun viewFilter(move: Movable): List<BaseView> {
        return collection.filter {
            when (move.currentDirection) {
                //上下筛选
                Direction.UP ->
                    if (move.x >= it.x && move.x + move.width <= it.x + it.width) move.y > it.y
                    else Math.abs(it.x - move.x) < move.width && move.y > it.y
                Direction.DOWN ->
                    if (move.x >= it.x && move.x + move.width <= it.x + it.width) move.y < it.y
                    else Math.abs(it.x - move.x) < move.width && move.y < it.y
                //左右筛选
                Direction.LEFT ->
                    if (move.y >= it.y && move.y + move.height <= it.y + it.height) move.x > it.x
                    else Math.abs(it.y - move.y) < move.height && move.x > it.x
                Direction.RIGHT ->
                    if (move.y >= it.y && move.y + move.height <= it.y + it.height) move.x < it.x
                    else Math.abs(it.y - move.y) < move.height && move.x < it.x
            }
        }
    }

    /**
     * 添加地图
     */
    private fun addMap(mapList: Array<Array<Char>>) {
        collection.clear()
        //添加其他
        for (y in 0 until mapList.size) {
            for (x in 0 until mapList[y].size) {
                when (mapList[y][x]) {
                    '砖' -> collection.add(Wall(x, y))
                    '铁' -> collection.add(Steel(x, y))
                    '草' -> collection.add(Grass(x, y))
                    '水' -> collection.add(Water(x, y))
                    '基' -> collection.add(Camp(x, y))
                    '1' -> collection.add(Enemy(x, y, 1))
                    '2' -> collection.add(Enemy(x, y, 2))
                    '3' -> collection.add(Enemy(x, y, 3))
                    '4' -> collection.add(Enemy(x, y, 4))
                    '5' -> collection.add(Enemy(x, y, 5))
                    '6' -> collection.add(Enemy(x, y, 6))
                }
            }
        }

        //添加坦克
        tankP1 = Tank(2, 12)
        tankP1.speed = 16
        collection.add(tankP1)

        tankP2 = Tank(10, 12, true)
        tankP2.speed = 16
        collection.add(tankP2)

    }

    /**
     * 按键操作
     */
    private fun keyControl(event: KeyEvent) {
        when (event.code) {
            //P1
            KeyCode.W -> {
                tankP1.moveTank(Direction.UP)
            }
            KeyCode.A -> {
                tankP1.moveTank(Direction.LEFT)
            }
            KeyCode.S -> {
                tankP1.moveTank(Direction.DOWN)
            }
            KeyCode.D -> {
                tankP1.moveTank(Direction.RIGHT)
            }
            KeyCode.J -> {
                if (!tankP1.whetherAttack(500)) return
                val bullet = tankP1.shootBullet()
                collection.add(bullet)
            }
            //P2
            KeyCode.UP -> {
                tankP2.moveTank(Direction.UP)
            }
            KeyCode.LEFT -> {
                tankP2.moveTank(Direction.LEFT)
            }
            KeyCode.DOWN -> {
                tankP2.moveTank(Direction.DOWN)
            }
            KeyCode.RIGHT -> {
                tankP2.moveTank(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                if (!tankP2.whetherAttack(500)) return
                val bullet = tankP2.shootBullet()
                collection.add(bullet)
            }
            else -> {
                println("无操作...")
            }
        }
    }

}