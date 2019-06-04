package com.llw.game.tank.`interface`

interface Attack : Destroyable {

    val attack: Int

    fun onAttacking(block: Blockade):Boolean


}