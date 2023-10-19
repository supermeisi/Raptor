package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.pow
import kotlin.math.sqrt

class ProjectileObject (
    gameView: GameView,
    shipObject: ShipObject
) {

    private var damage : Int
    private var centerX : Float
    private var centerY : Float
    private val dirX : Float
    private val dirY : Float
    private var speed : Float

    init {
        damage = 5
        centerX = shipObject.getX()
        centerY = shipObject.getY()
        speed = 5f
        dirX = gameView.circleX - centerX
        dirY = gameView.circleY - centerY
    }

    fun draw(canvas : Canvas) {
        //Drawing the object
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(centerX, centerY, 20f, paint)
    }

    fun addCoordinate() {
        //Function for updating object
        centerX += speed * dirX / sqrt(dirX.pow(2) + dirY.pow(2))
        centerY += speed * dirY / sqrt(dirX.pow(2) + dirY.pow(2))
    }

    fun getX() : Float {
        return centerX
    }

    fun getY() : Float {
        return centerY
    }

    fun getDamage() : Int {
        return damage
    }
}