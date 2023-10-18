package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs

class ShipObject(
        var centerX : Float,
        var centerY : Float
) {
    private var speed : Float
    private var energy : Int
    private var size : Float

    init {
        speed = 5f
        energy = 50
        size = 150f
    }

    fun draw(canvas : Canvas) {
        //Drawing the object
        val paint = Paint()
        paint.color = Color.GREEN
        canvas.drawRect(centerX - size / 2, centerY - size / 2, centerX + size / 2, centerY + size / 2, paint)
    }

    fun addCoordinate(dX : Float, dY : Float) {
        //Function for updating object
        centerX += dX;
        centerY += dY;
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
}