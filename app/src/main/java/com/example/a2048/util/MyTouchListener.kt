package com.example.a2048.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.a2048.data.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val myGestureDetector = GestureDetector(context, MyGestureDetector())
    private var detectSideListener: ((SideEnum) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        myGestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (abs(e1.y - e2.y) < 100 && abs(e1.x - e2.x) < 100) return false
            if (abs(e1.y - e2.y) > abs(e1.x - e2.x)) {
                if (e1.y > e2.y) detectSideListener?.invoke(SideEnum.UP)
                else detectSideListener?.invoke(SideEnum.DOWN)
                // vertical
            } else {
                if (e1.x > e2.x) detectSideListener?.invoke(SideEnum.LEFT)
                else detectSideListener?.invoke(SideEnum.RIGHT)
                // horizontal
            }
            return true
        }
    }

    fun setDetectSideListener(block: (SideEnum) -> Unit) {
        detectSideListener = block
    }
}

