package com.example.ayushrajcelebrare

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class Canvas(context: Context, attrs: AttributeSet) : View(context, attrs), View.OnTouchListener {
    private val textList = mutableListOf<EditableText>()
    private var selectedText: EditableText? = null

    init {
        setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (text in textList) {
            text.draw(canvas)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> handleTouchDown(event.x, event.y)
                MotionEvent.ACTION_MOVE -> handleTouchMove(event.x, event.y)
            }
        }
        return true
    }

    fun handleTouchDown(x: Float, y: Float) {
        for (text in textList) {
            if (text.contains(x, y)) {
                selectedText = text
                return
            }
        }


    }

    private fun handleTouchMove(x: Float, y: Float) {
        selectedText?.let {
            it.x = x
            it.y = y
            invalidate()
        }
    }

    fun addNewText() {
        // Add a new text at a default position
        val newText = EditableText("New Text", 100f, 100f)
        textList.add(newText)
        selectedText = newText
        invalidate()
    }

    fun changeTextColor(color: Int) {
        selectedText?.let {
            it.textColor = color
            invalidate()
        }
    }

    fun changeTextSize(size: Float){
        selectedText?.textSize = size
    invalidate()
    }

    fun updateSelectedText(newText: String) {
        selectedText?.text = newText
        invalidate()
    }


    inner class EditableText(
        var text: String,
        var x: Float,
        var y: Float
    ) {
        private val textPaint: Paint = Paint()
        var textSize: Float = 50f  // Default text size
        var textColor: Int = Color.RED

        init {
            textPaint.color = textColor
            textPaint.textSize = textSize
            textPaint.textAlign = Paint.Align.LEFT
        }


        fun draw(canvas: Canvas) {
            textPaint.color = textColor
            textPaint.textSize = textSize
            textPaint.textAlign = Paint.Align.LEFT
            canvas.drawText(text, x, y, textPaint)
        }

        fun contains(x: Float, y: Float): Boolean {
            val bounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, bounds)
            return x >= this.x && x <= this.x + bounds.width() && y >= this.y - bounds.height() && y <= this.y
        }
    }
}
