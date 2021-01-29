package com.example.customprogressbar

import android.graphics.*
import android.graphics.drawable.Drawable
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

class ProgressDrawable : Drawable() {
    var mPaint: Paint = Paint()
    var red = 15F
    var blue = 14F
    var green = 10F
    var yellow = 30F

    private val radius = 20F
    private val shift = 20F

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        val width: Float = bounds.width() * (level.toFloat() / 10000)
        val top = bounds.top.toFloat()
        val bottom = bounds.bottom.toFloat()

        val r = (red / sum()) * width
        val b = (blue / sum()) * width
        val g = (green / sum()) * width
        val y = (yellow / sum()) * width

        val left = -shift
        var right = r + b + g + y + if (level * yellow == 0F) 0F else shift
        mPaint.color = Color.YELLOW
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, mPaint)

        right = r + b + g + if (level * green == 0F) 0F else shift
        mPaint.color = Color.GREEN
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, mPaint)

        right = r + b + if (level * blue == 0F) 0F else shift
        mPaint.color = Color.BLUE
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, mPaint)

        right = r + if (level * red == 0F) 0F else shift
        mPaint.color = Color.RED
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, mPaint)

    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    fun setDims(red: Int, green: Int, blue: Int, yellow: Int) {
        this.red = red.toFloat()
        this.green = green.toFloat()
        this.blue = blue.toFloat()
        this.yellow = yellow.toFloat()
    }

    fun setProgress(l: Int) {
        onLevelChange(l * 100)
    }

    private fun sum(): Float {
        return red + green + blue + yellow + 0.0F
    }
}