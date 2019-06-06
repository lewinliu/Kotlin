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
    private lateinit var tankP1: Tank
    private lateinit var tankP2: Tank
    private lateinit var camp: Camp

    //层级1：底层，砖墙，坦克
    private val tier1 = ArrayList<BaseView>()
    //层级2：水地
    private val tier2 = ArrayList<BaseView>()
    //层级3：子弹
    private val tier3 = ArrayList<BaseView>()
    //层级4：草地
    private val tier4 = ArrayList<BaseView>()

    //创建地图
    override fun onCreate() {
        initMap(Maps.Map2)
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
     * 初始化
     */
    private fun initMap(mapList: Array<Array<Char>>) {
        //添加其他
        for (y in 0 until mapList.size) {
            for (x in 0 until mapList[y].size) {
                when (mapList[y][x]) {
                    '砖' -> addView(Wall(x, y))
                    '铁' -> addView(Steel(x, y))
                    '草' -> addView(Grass(x, y))
                    '水' -> addView(Water(x, y))
                    '基' -> {
                        camp = Camp(x, y)
                        addView(camp)
                    }
                    'a' -> {
                        tankP1 = Tank(x, y)
                        addView(tankP1)
                    }
                    'b' -> {
                        tankP2 = Tank(x, y, true)
                        addView(tankP2)
                    }
                    '1', '2', '3', '4', '5', '6' -> {
                        val type = mapList[y][x].toString().toInt()
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
            2 -> tier2.remove(view)
            3 -> tier3.remove(view)
            4 -> tier4.remove(view)
        }
    }

    /**
     * 判断游戏是否结束
     */
    private fun isGameOver(): Boolean {
        //1.游戏结束，不再添加视图
        if (camp.isGameOver) {
            return true
        }
        //2.基地被销毁，游戏结束
        if (camp.isDestroyable()) {
            collection.add(GameOver())
            camp.isGameOver = true
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
                addView(tankP2.shootBullet())
                addMap()//重新添加地图
            }
            else -> {
                println("无操作...")
            }
        }
    }

}