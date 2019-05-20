/*
 * Copyright 2019 Wisekiddo by Ronald Garcia Bernardo. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wisekiddo.widgets.contracts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

abstract class CircularSticksAbstractView : View, LoaderContract {

    private var noOfSticks: Int = 80

    private var outerCircleRadius: Float = 200.0f
    private var innerCircleRadius: Float = 100.0f

    open var sticksColor = resources.getColor(android.R.color.darker_gray)
    private var viewBackgroundColor: Int = resources.getColor(android.R.color.white)

    private lateinit var sticksPaint: Paint
    private lateinit var innerCirclePaint: Paint

    private lateinit var outerCircleOval: RectF

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    open fun initPaints() {
        sticksPaint = Paint()
        sticksPaint.color = sticksColor
        sticksPaint.style = Paint.Style.FILL
        sticksPaint.isAntiAlias = true

        outerCircleOval = RectF().apply {
            left = 0f
            top = 0f
            right = 2 * outerCircleRadius
            bottom = 2 * outerCircleRadius
        }

        innerCirclePaint = Paint()
        innerCirclePaint.color = viewBackgroundColor
        innerCirclePaint.style = Paint.Style.FILL
        innerCirclePaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(2 * outerCircleRadius.toInt(), 2 * outerCircleRadius.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (noOfSticks < 1) {
            noOfSticks = 8
        }

        val sweepAngle: Float = (360f / (2 * noOfSticks))
        var startAngle: Float = (0 - (sweepAngle / 2))

        for (i in 0 until noOfSticks) {
            canvas.drawArc(outerCircleOval, startAngle, sweepAngle, true, sticksPaint)
            startAngle += (2 * sweepAngle)
        }

        canvas.drawCircle(outerCircleRadius, outerCircleRadius, innerCircleRadius, innerCirclePaint)
    }

}