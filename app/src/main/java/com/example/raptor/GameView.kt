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
import kotlin.math.abs
import kotlin.random.Random

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    // Important classes
    var gameThread: GameThread
    private val bulletObjects = ArrayList<BulletObject>()
    private val shipObjects = ArrayList<ShipObject>()
    private val projectileObjects = ArrayList<ProjectileObject>()

    // Important values
    val paint: Paint
    var circleX: Float
    var circleY: Float
    var prevX: Float
    var prevY: Float
    var canvasWidth: Int
    var canvasHeight: Int
    var prevBulletTime: Long
    var prevShipTime: Long

    val randCoordinates = Random(1)

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
        prevBulletTime = 0
        prevShipTime = 0
    }

    fun generateRandomPosition(): Pair<Float, Float> {
        val randomX = randCoordinates.nextFloat() * canvasWidth
        val randomY = randCoordinates.nextFloat() * canvasHeight - canvasHeight
        return Pair(randomX, randomY)
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
        if (currentTime - prevBulletTime >= 100) {
            val bullet1 = BulletObject(circleX - 20f, circleY)
            val bullet2 = BulletObject(circleX + 20f, circleY)
            bulletObjects.add(bullet1)
            bulletObjects.add(bullet2)
            prevBulletTime = currentTime
        }

        // Create new ships
        if (currentTime - prevShipTime >= 10000) {
            val coordinate = generateRandomPosition();
            val ship = ShipObject(coordinate.first, coordinate.second)
            shipObjects.add(ship)
            prevShipTime = currentTime
        }

        for (bulletObject in bulletObjects.reversed()) {
            //Check collision between bullets and ships
            for (shipObject in shipObjects.reversed()) {
                // Update ship position
                if (shipObject.isCollision(bulletObject.getX(), bulletObject.getY())) {
                    var damage = bulletObject.getDamage()
                    shipObject.setDamage(damage)
                    bulletObjects.remove(bulletObject)
                }
            }

            // Update the bullet position
            bulletObject.addCoordinate(0f, -10f)

            // Remove bullets when being outside the canvas
            if (bulletObject.getX() <= 0 || bulletObject.getX() >= canvasWidth ||
                bulletObject.getY() <= 0 || bulletObject.getY() >= canvasHeight
            ) {
                bulletObjects.remove(bulletObject)
            }
        }

        for (shipObject in shipObjects.reversed()) {
            // Update ship position
            val speed = shipObject.getSpeed()
            shipObject.addCoordinate(0f, speed)

            val dt = currentTime - shipObject.getShootTime()
            // Create projectile after given time
            if (currentTime.mod(shipObject.getShootTime()) <= 100) {
                val projectile = ProjectileObject(this, shipObject)
                projectileObjects.add(projectile)
            }

            //Check energy and destroy ship
            val energy = shipObject.getEnergy()

            if (energy <= 0) {
                shipObjects.remove(shipObject)
            }

            // Check collision with player
            if (shipObject.isCollision(circleX, circleY)) {
                gameThread.destroy()
            }
        }

        for (projectileObject in projectileObjects) {
            projectileObject.addCoordinate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event!!.y

        when (event.action) {
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
                if (newPosX >= 0 && newPosX <= canvasWidth &&
                    newPosY >= 0 && newPosY <= canvasHeight
                ) {

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

        canvas?.drawColor(Color.BLACK)

        //Draw the bullets
        for (bulletObject in bulletObjects) {
            bulletObject.draw(canvas)
        }

        for (shipObject in shipObjects) {
            shipObject.draw(canvas)
        }

        for (projectileObject in projectileObjects) {
            projectileObject.draw(canvas)
        }

        //Draw the player
        canvas?.drawCircle(circleX, circleY, 50f, paint)
    }
}