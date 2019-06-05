package com.llw.game.tank.tools

import com.llw.game.tank.config.Config
import org.itheima.kotlin.game.core.Composer

/**
 * 游戏音效
 */
object SoundTool {

    /**
     * 游戏开始音效
     */
    fun start() {
        try {
            Composer.play(Config.Sound.Sound_start)
        } catch (e: Exception) {
            println("Sound_start error=$e")
        }
    }

    fun fire() {
        try {
            Composer.play(Config.Sound.Sound_fire)
        } catch (e: Exception) {
            println("Sound_fire error=$e")
        }
    }

    /**
     * 爆炸音效
     */
    fun blast() {
        try {
            Composer.play(Config.Sound.Sound_blast)
        } catch (e: Exception) {
            println("Sound_blast error=$e")
        }
    }

    /**
     * 添加坦克音效
     */
    fun add() {
        try {
            Composer.play(Config.Sound.Sound_add)
        } catch (e: Exception) {
            println("Sound_add error=$e")
        }
    }

    /**
     * 被打击音效
     */
    fun hit() {
        try {
            Composer.play(Config.Sound.Sound_hit)
        } catch (e: Exception) {
            println("Sound_hit error=$e")
        }
    }
}