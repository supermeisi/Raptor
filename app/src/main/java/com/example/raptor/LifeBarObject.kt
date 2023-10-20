package com.example.raptor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

class LifeBarObject {
    fun createLinearGradient() : LinearGradient {
        val colors = intArrayOf(Color.GREEN, Color.YELLOW, Color.RED)
        val positions = floatArrayOf(0.0f, 0.5f, 1.0f)

        return LinearGradient(0f, 0f, 200f, 0f, colors, positions, Shader.TileMode.CLAMP)
    }

    fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.shader = createLinearGradient() // Set the gradient shader

        canvas.drawRect(0f, 0f, canvas.width.toFloat(), 50f, paint) // Draw the life bar rectangle
    }
}