package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ShipObject(
        var centerX : Float,
        var centerY : Float
) {
    private var speed : Float
    private var energy : Int

    init {
        speed = 1f
        energy = 100
    }

    fun draw(canvas : Canvas) {
        //Drawing the object
        val paint = Paint()
        paint.color = Color.GREEN
        canvas.drawRect(centerX - 50f, centerY - 50f, centerX + 50f, centerY + 50f, paint)
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

    fun setDamage (damage : Int) {
        energy -= damage
    }
}