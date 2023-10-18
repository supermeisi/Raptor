package com.example.raptor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback {

    // Important classes
    var gameThread : GameThread
    private val bulletObjects = ArrayList<BulletObject>()

    // Important values
    val paint : Paint
    var circleX : Float
    var circleY : Float
    var prevX : Float
    var prevY : Float
    var canvasWidth : Int
    var canvasHeight : Int
    var prevTime : Long

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        circleX = 0f
        circleY = 0f
        prevX = 0f
        prevY = 0f
        canvasWidth = 0
        canvasHeight = 0
        prevTime = 0
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Handle surface changes (if needed)
        canvasWidth = width
        canvasHeight = height

        circleX = canvasWidth.toFloat() / 2
        circleY = canvasHeight.toFloat() / 2 + 800
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        gameThread.setRunning(false)
        while (retry) {
            try {
                gameThread.join()
                retry = false
            } catch (e: InterruptedException) {
                // Handle exceptions
            }
        }
    }

    fun update() {
        //Create new bullet
        var currentTime = System.currentTimeMillis()

        // Create new bullets
        if(currentTime - prevTime >= 250) {
            val bullet = BulletObject(circleX, circleY)
            bulletObjects.add(bullet)
            prevTime = currentTime
        }

        for ((id, bulletObject) in bulletObjects.withIndex()) {
            // Update the bullet position
            bulletObject.addCoordinate(0f, -10f)

            // Remove bullets when being outside the canvas
            if(bulletObject.getX() <= 0 || bulletObject.getX() >= canvasWidth ||
               bulletObject.getY() <= 0 || bulletObject.getY() >= canvasHeight) {
                bulletObjects.drop(id)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event!!.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Save the initial touch position
                prevX = x
                prevY = y
            }
            MotionEvent.ACTION_MOVE -> {
                // Calculate the relative movement
                val dx = x - prevX
                val dy = y - prevY

                val newPosX = circleX + dx
                val newPosY = circleY + dy

                // Check if object is within canvas boundaries
                if(newPosX >= 0 && newPosX <= canvasWidth &&
                   newPosY >= 0 && newPosY <= canvasHeight) {

                    // Update the object's position based on relative movement
                    circleX = newPosX
                    circleY = newPosY
                }
            }
        }

        // Update the previous touch position
        prevX = x
        prevY = y

        // Invalidate the view to trigger a redraw
        invalidate()

        return true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvasWidth = canvas.width
        canvasHeight = canvas.height

        canvas?.drawColor(Color.RED)

        //Draw the bullets
        for (bulletObject in bulletObjects) {
            bulletObject.draw(canvas)
        }

        //Draw the player
        canvas?.drawCircle(circleX, circleY, 50f, paint)
    }
}