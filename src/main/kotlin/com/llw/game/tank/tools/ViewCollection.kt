package com.llw.game.tank.tools

import com.llw.game.tank.GameWindow
import com.llw.game.tank.`interface`.BaseView

class ViewCollection {
    private var views = ArrayList<BaseView>()

    @Synchronized
    fun draw() {
        synchronized(ViewCollection::class.java) {
            views.forEach { it.draw() }
        }
    }

    @Synchronized
    fun filter(predicate: (BaseView) -> Boolean): List<BaseView> {
        synchronized(GameWindow::class.java) {
            return views.filter(predicate)
        }
    }

    @Synchronized
    fun add(view: BaseView) {
        synchronized(ViewCollection::class.java) {
            val it = views.listIterator()
            it.add(view)
            println("-------->add $view,    view(${view.x},${view.y})")
        }
    }

    @Synchronized
    fun remove(ifs: (view: BaseView) -> Boolean) {
        synchronized(ViewCollection::class.java) {
            val it = views.listIterator()
            while (it.hasNext()) {
                val v = it.next()
                if (ifs(v)) {
                    println("-------->remove $v,    view(${v.x},${v.y})")
                    it.remove()
                }
            }
        }
    }

    @Synchronized
    fun clear() {
        synchronized(ViewCollection::class.java) {
            if (views.isEmpty()) return
            views.clear()
        }
    }

}