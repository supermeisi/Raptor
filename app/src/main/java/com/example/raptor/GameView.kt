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

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        circleX = 100f
        circleY = 100f
        width = 100f
        height = 100f

        val bullet = BulletObject(100f, 100f)
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
        // Update the square's position here
        circleX += 5f

        for (bulletObject in bulletObjects) {
            bulletObject.update()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        circleX = event!!.x
        circleY = event!!.y

        invalidate()

        return super.onTouchEvent(event)
        //return true
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