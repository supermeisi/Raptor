package com.example.raptor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
                prevX = x
                prevY = y
            }
            MotionEvent.ACTION_MOVE -> {
                // Calculate the relative movement
                val dx = x - prevX
                val dy = y - prevY

                // Update the object's position based on relative movement
                circleX += x
                circleY += y
            }
        }

        // Invalidate the view to trigger a redraw
        invalidate()

        return super.onTouchEvent(event)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas?.drawColor(Color.RED)

        //Draw the bullets
        for (bulletObject in bulletObjects) {
            bulletObject.draw(canvas)
        }

        //Draw the player
        canvas?.drawCircle(circleX, circleY, 50f, paint)
    }
}