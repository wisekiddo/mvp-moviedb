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

package com.wisekiddo.widgets.basicviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.wisekiddo.widgets.R

import java.util.EnumSet

class RoundedImageView : AppCompatImageView {

    private val ALL_ROUNDED_CORNERS_VALUE: Int
    private var paint: Paint? = null
    private var path: Path? = null
    private var pathWidth: Int = 0
    private var pathHeight: Int = 0
    private var cornerRadius: Int = 0
    private var roundedTopLeft: Boolean = false
    private var roundedBottomLeft: Boolean = false
    private var roundedTopRight: Boolean = false
    private var roundedBottomRight: Boolean = false

    constructor(context: Context) : super(context) {
        init()
        cornerRadius = 0
        ALL_ROUNDED_CORNERS_VALUE = 15
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedImageView, 0, 0)
        val cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_cornerRadius, 0)
        ALL_ROUNDED_CORNERS_VALUE = 15
        val roundedCorners = a.getInt(R.styleable.RoundedImageView_roundedCorners, ALL_ROUNDED_CORNERS_VALUE)
        a.recycle()
        init()
        setCornerRadius(cornerRadius)
        setRoundedCorners(roundedCorners)
        this.cornerRadius = 0
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyleAttr, 0)
        val cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_cornerRadius, 0)
        ALL_ROUNDED_CORNERS_VALUE = 15
        val roundedCorners = a.getInt(R.styleable.RoundedImageView_roundedCorners, ALL_ROUNDED_CORNERS_VALUE)
        a.recycle()
        init()
        setCornerRadius(cornerRadius)
        setRoundedCorners(roundedCorners)
        this.cornerRadius = 0
    }

    private fun setRoundedCorners(roundedCorners: Int) {
        val topLeft = 8 //1000 boolean
        val topRight = 4 //0100 boolean
        val bottomLeft = 2 //0010 boolean
        val bottomRight = 1 //0001 boolean

        roundedTopLeft = topLeft == roundedCorners and topLeft
        roundedTopRight = topRight == roundedCorners and topRight
        roundedBottomLeft = bottomLeft == roundedCorners and bottomLeft
        roundedBottomRight = bottomRight == roundedCorners and bottomRight
    }

    private fun init() {
        paint = Paint()
        path = Path()
        setupPaint()
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        if (pathWidth != newWidth || pathHeight != newHeight) {
            pathWidth = newWidth
            pathHeight = newHeight
            setupPath()
        }
    }

    /**
     * @param cornerRadius in pixels, default is 0
     */
    fun setCornerRadius(cornerRadius: Int) {
        if (this.cornerRadius != cornerRadius) {
            this.cornerRadius = cornerRadius
            setupPath()
        }
    }

    /**
     * @param corners, default is All rounded corners
     */
    fun setRoundCorners(corners: EnumSet<Corner>) {
        if (roundedBottomLeft != corners.contains(Corner.BOTTOM_LEFT)
            || roundedBottomRight != corners.contains(Corner.BOTTOM_RIGHT)
            || roundedTopLeft != corners.contains(Corner.TOP_LEFT)
            || roundedTopRight != corners.contains(Corner.TOP_RIGHT)
        ) {
            roundedBottomLeft = corners.contains(Corner.BOTTOM_LEFT)
            roundedBottomRight = corners.contains(Corner.BOTTOM_RIGHT)
            roundedTopLeft = corners.contains(Corner.TOP_LEFT)
            roundedTopRight = corners.contains(Corner.TOP_RIGHT)
            setupPath()
        }
    }

    private fun setupPaint(): Paint? {
        paint?.style = Paint.Style.FILL
        paint?.color = Color.TRANSPARENT
        paint?.isAntiAlias = true
        paint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        return paint

    }

    private fun setupPath() {
        path = roundedRect(
            path!!, 0f, 0f, pathWidth.toFloat(), pathHeight.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(),
            roundedTopLeft, roundedTopRight, roundedBottomRight, roundedBottomLeft
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInEditMode) {
            val saveCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
            super.onDraw(canvas)
            canvas.drawPath(path!!, paint!!)
            canvas.restoreToCount(saveCount)
        } else {
            super.onDraw(canvas)
        }
    }

    enum class Corner {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;


        companion object {

            val ALL = EnumSet.allOf(Corner::class.java)
            val TOP = EnumSet.of(TOP_LEFT, TOP_RIGHT)
        }
    }

    private fun roundedRect(
        path: Path,
        left: Float, top: Float, right: Float, bottom: Float,
        rx: Float, ry: Float,
        tl: Boolean, tr: Boolean, br: Boolean, bl: Boolean
    ): Path {
        var rx = rx
        var ry = ry
        path.reset()
        if (rx < 0) {
            rx = 0f
        }
        if (ry < 0) {
            ry = 0f
        }
        val width = right - left
        val height = bottom - top
        if (rx > width / 2) {
            rx = width / 2
        }
        if (ry > height / 2) {
            ry = height / 2
        }
        val widthMinusCorners = width - 2 * rx
        val heightMinusCorners = height - 2 * ry

        path.moveTo(right, top + ry)
        if (tr) {
            path.rQuadTo(0f, -ry, -rx, -ry)//top-right corner
        } else {
            path.rLineTo(0f, -ry)
            path.rLineTo(-rx, 0f)
        }
        path.rLineTo(-widthMinusCorners, 0f)
        if (tl) {
            path.rQuadTo(-rx, 0f, -rx, ry) //top-left corner
        } else {
            path.rLineTo(-rx, 0f)
            path.rLineTo(0f, ry)
        }
        path.rLineTo(0f, heightMinusCorners)

        if (bl) {
            path.rQuadTo(0f, ry, rx, ry)//bottom-left corner
        } else {
            path.rLineTo(0f, ry)
            path.rLineTo(rx, 0f)
        }

        path.rLineTo(widthMinusCorners, 0f)
        if (br) {
            path.rQuadTo(rx, 0f, rx, -ry)
        } else {
            path.rLineTo(rx, 0f)
            path.rLineTo(0f, -ry)
        }

        path.rLineTo(0f, -heightMinusCorners)

        path.close()

        path.fillType = Path.FillType.INVERSE_EVEN_ODD
        return path
    }
}