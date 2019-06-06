package com.llw.game.tank

import com.llw.game.tank.`interface`.*
import com.llw.game.tank.config.Config
import com.llw.game.tank.config.Maps
import com.llw.game.tank.enum.Direction
import com.llw.game.tank.model.*
import com.llw.game.tank.tools.SoundTool
import com.llw.game.tank.tools.ViewCollection
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class GameWindow : Window(Config.GameName, Config.GameIcon, Config.GameWidth, Config.GameHeight) {

    private val collection = ViewCollection()
    private lateinit var option: Option
    private lateinit var tankP1: Tank
    private lateinit var tankP2: Tank
    private lateinit var camp: Camp

    private var isGameOver: Boolean = false
    private var mapIndex: Int = -1

    //层级1：水地
    private val tier1 = ArrayList<BaseView>()
    //层级2：子弹，坦克等
    private val tier2 = ArrayList<BaseView>()
    //层级3：草地，砖墙等
    private val tier3 = ArrayList<BaseView>()
    //层级4：game over
    private val tier4 = ArrayList<BaseView>()

    //创建地图
    override fun onCreate() {
        if (mapIndex < Maps.mapList.size - 1) mapIndex++ else mapIndex = 0

        initMap(Maps.mapList[mapIndex])
        addMap()//添加地图
        //游戏开始音效
        SoundTool.start()
    }

    //打印地图
    override fun onDisplay() {
        collection.draw()
    }

    //按键监听
    override fun onKeyPressed(event: KeyEvent) = keyControl(event)

    //耗时操作
    override fun onRefresh() {
        //游戏结束
        if (isGameOver()) return

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
                        //障碍接受被攻击通知
                        val blast = block.notifySuffer(move.attack)
                        //打击效果
                        if (blast != null) collection.add(blast)
                    } else {
                        badDirection = null
                    }
                }
            }
            //5.将结果通知运动物
            move.notifyCollision(badDirection)

            //敌方坦克自动射击
            if (move is Enemy) {
                //5秒钟自动发射一枚子弹
                if (move.whetherAttack(5000)) {
                    addView(move.shootBullet())
                    addMap()//重新添加地图
                }
            }
            //自动运动
            if (move is AutoMovable) {
                move.autoMove()
            }
        }

        //销毁
        collection.remove { view ->
            if (view is Destroyable) {
                if (view.isDestroyable()) {
                    removeView(view)
                    return@remove view.isDestroyable()
                }
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
                    if (move.x >= it.x && move.x + move.width <= it.x + it.width) move.y <= it.y
                    else Math.abs(it.x - move.x) < move.width && move.y < it.y
                //左右筛选
                Direction.LEFT ->
                    if (move.y >= it.y && move.y + move.height <= it.y + it.height) move.x > it.x
                    else Math.abs(it.y - move.y) < move.height && move.x > it.x
                Direction.RIGHT ->
                    if (move.y >= it.y && move.y + move.height <= it.y + it.height) move.x <= it.x
                    else Math.abs(it.y - move.y) < move.height && move.x < it.x
            }
        }
    }

    /**
     * 初始化
     */
    private fun initMap(map: Array<Array<String>>) {
        tier1.clear()
        tier2.clear()
        tier3.clear()
        tier4.clear()
        //添加其他
        for (y in 0 until map.size) {
            for (x in 0 until map[y].size) {
                when (map[y][x]) {
                    "|" -> {
                        option = Option(x, y)
                        addView(option)
                    }
                    "砖" -> addView(Wall(x, y))
                    "铁" -> addView(Steel(x, y))
                    "草" -> addView(Grass(x, y))
                    "水" -> addView(Water(x, y))
                    "基" -> {
                        camp = Camp(x, y)
                        addView(camp)
                    }
                    "p1" -> {
                        tankP1 = Tank(x, y)
                        addView(tankP1)
                    }
                    "p2" -> {
                        if (option.getIndex() == 1) {
                            tankP2 = Tank(x, y, true)
                            addView(tankP2)
                        }
                    }
                    "1", "2", "3", "4", "5", "6" -> {
                        val type = map[y][x].toInt()
                        addView(Enemy(x, y, type))
                    }
                }
            }
        }
    }

    /**
     * 添加地图
     */
    private fun addMap() {
        collection.clear()
        collection.addAll(tier1)
        collection.addAll(tier2)
        collection.addAll(tier3)
        collection.addAll(tier4)
    }


    /**
     * 在地图中添加view
     */
    private fun addView(view: BaseView) {
        when (view.tier) {
            1 -> tier1.add(view)
            2 -> tier2.add(view)
            3 -> tier3.add(view)
            4 -> tier4.add(view)
        }
    }

    /**
     * 在地图中移除view
     */
    private fun removeView(view: BaseView) {
        when (view.tier) {
            1 -> tier1.remove(view)
            2 -> {
                tier2.remove(view)
                if (tier2.none { it is Tank }) {
                    //我方坦克死完了，游戏结束
                    isGameOver = true
                }
                if (tier2.none { it is Enemy }) {
                    //敌方坦克死完了，游戏胜利
                    onCreate()
                }
            }
            3 -> tier3.remove(view)
            4 -> tier4.remove(view)
        }
    }

    /**
     * 判断游戏是否结束
     */
    private fun isGameOver(): Boolean {
        //1.游戏结束，不再添加视图
        if (isGameOver) {
            return true
        }
        //2.基地被销毁，游戏结束
        if (mapIndex > 0 && camp.isDestroyable()) {
            isGameOver = true
            addView(GameOver())
            addMap()//重新添加地图
            return true
        }
        return false
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
                addView(tankP1.shootBullet())
                addMap()//重新添加地图
            }
            //P2
            KeyCode.LEFT -> {
                tankP2.moveTank(Direction.LEFT)
            }
            KeyCode.RIGHT -> {
                tankP2.moveTank(Direction.RIGHT)
            }
            //回到选择模式
            KeyCode.ESCAPE -> {
                println("退出...")
                mapIndex = -1
                isGameOver = true
                onCreate()
            }
            else -> {
                if (mapIndex == 0) {//选择模式
                    when (event.code) {
                        KeyCode.UP -> {
                            option.setIndex(-1)
                        }
                        KeyCode.DOWN -> {
                            option.setIndex(1)
                        }
                        KeyCode.ENTER -> {
                            isGameOver = false
                            onCreate()
                        }
                        else -> println("其他按键...")
                    }
                } else {
                    when (event.code) {//移动与射击模式
                        KeyCode.UP -> {
                            tankP2.moveTank(Direction.UP)
                        }
                        KeyCode.DOWN -> {
                            tankP2.moveTank(Direction.DOWN)
                        }
                        KeyCode.ENTER -> {
                            if (isGameOver) {
                                println("游戏结束，重新开始...")
                                isGameOver = false
                                mapIndex = -1
                                onCreate()
                            } else {
                                if (tankP2.isDestroyable()) return
                                if (!tankP2.whetherAttack(500)) return
                                addView(tankP2.shootBullet())
                                addMap()//重新添加地图
                            }
                        }
                        else -> println("其他按键...")
                    }
                }
            }
        }
    }

}