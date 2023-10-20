package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

class LifeBarObject {
    var canvasWidth : Int

    init {
        canvasWidth = 0
    }

    fun createLinearGradient() : LinearGradient {
        val colors = intArrayOf(Color.RED, Color.YELLOW, Color.GREEN)

        return LinearGradient(0f, 0f, canvasWidth.toFloat(), 0f, colors, null, Shader.TileMode.CLAMP)
    }

    fun draw(canvas: Canvas) {
        canvasWidth = canvas.width

        val paint = Paint()
        paint.shader = createLinearGradient() // Set the gradient shader

        canvas.drawRect(0f, 0f, canvasWidth.toFloat(), 50f, paint) // Draw the life bar rectangle
    }
}