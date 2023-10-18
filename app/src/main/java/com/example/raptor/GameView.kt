package com.example.raptor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class GameView(context : Context) : View(context) {

    val paint : Paint
    init {
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
    }
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.RED)
        canvas?.drawCircle(200f, 200f, 50f, paint)
    }
}