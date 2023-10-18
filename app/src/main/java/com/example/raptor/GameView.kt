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
    var width : Float
    var height: Float
    var prevX : Float
    var prevY : Float
    var canvasWidth : Int
    var canvasHeight : Int

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        circleX = 100f
        circleY = 1500f
        width = 100f
        height = 100f
        prevX = 0f
        prevY = 0f
        canvasWidth = 0
        canvasHeight = 0

        val bullet = BulletObject(100f, 1500f)
        bulletObjects.add(bullet)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Handle surface changes (if needed)
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
        //Update the bullet position
        for (bulletObject in bulletObjects) {
            bulletObject.addCoordinate(0f, -5f)
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