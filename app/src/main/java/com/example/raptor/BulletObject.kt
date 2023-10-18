package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class BulletObject (
        var centerX : Float,
        var centerY : Float
) {

    fun draw(canvas : Canvas) {
        //Drawing the object
        val paint = Paint()
        paint.color = Color.GRAY
        canvas.drawCircle(centerX, centerY, 10f, paint)
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
}