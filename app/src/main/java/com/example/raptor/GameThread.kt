package com.example.raptor

import android.view.SurfaceHolder

class GameThread(private val surfaceHolder : SurfaceHolder, private val gameView: GameView) : Thread() {
    private var running = true

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        while(running) {
            val canvas = surfaceHolder.lockCanvas()
            if(canvas != null) {
                gameView.update()
                gameView.draw(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}