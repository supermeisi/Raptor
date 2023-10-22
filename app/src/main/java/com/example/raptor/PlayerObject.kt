package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs

class PlayerObject(
    var centerX : Float,
    var centerY : Float
) {
    private var speed : Float
    private var energy : Int
    private var size : Float
    private var shootTime : Int

    init {
        speed = 5f
        energy = 1000
        size = 100f
        shootTime = 2000
    }

    fun draw(canvas : Canvas) {
        //Drawing the object
        val paint = Paint()
        paint.color = Color.YELLOW
        canvas.drawCircle(centerX, centerY, size / 2, paint)
    }

    fun addCoordinate(dx : Float, dy : Float) {
        //Function for updating object
        centerX += dx;
        centerY += dy;
    }

    fun setCoordinate(x : Float, y : Float) {
        centerX = x
        centerY = y
    }

    fun getCoordinate() : Pair<Float, Float> {
        return Pair<Float, Float> (centerX, centerY)
    }

    fun getX() : Float {
        return centerX
    }

    fun getY() : Float {
        return centerY
    }

    fun getSpeed() : Float {
        return speed
    }

    fun getEnergy() : Int {
        return energy
    }

    fun setDamage(damage : Int) {
        energy -= damage
    }

    fun isCollision(x : Float, y : Float) : Boolean {
        var dx = abs(x - centerX)
        var dy = abs(y - centerY)

        return abs(dx) <= 50 && abs(dy) <= 50
    }

    fun getShootTime() : Int {
        return shootTime
    }
}