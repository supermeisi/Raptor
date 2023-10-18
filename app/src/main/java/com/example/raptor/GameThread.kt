package com.example.raptor

import android.view.SurfaceHolder

class GameThread(private val surfaceHolder : SurfaceHolder, private val gameView: GameView) : Thread() {
    private val targetFPS = 60
    private val targetFrameTime = 1000 / targetFPS
    private var lastFrameTime = 0L
    private var running = true

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        while(running) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - lastFrameTime

            if(elapsedTime >= targetFrameTime) {
                lastFrameTime = currentTime
            }

            val sleepTime = targetFrameTime - elapsedTime

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime)
                } catch (e: InterruptedException) {
                    // Handle exceptions
                }
            }

            val canvas = surfaceHolder.lockCanvas()
            if(canvas != null) {
                gameView.update()
                gameView.draw(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}