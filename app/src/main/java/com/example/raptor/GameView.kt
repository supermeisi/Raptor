package com.example.raptor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(context : Context) : View(context) {

    val paint : Paint
    var circleX : Float
    var circleY : Float

    init {
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        circleX = 100f
        circleY = 100f
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

        canvas.drawColor(Color.RED)
        canvas?.drawCircle(circleX, circleY, 50f, paint)
    }
}