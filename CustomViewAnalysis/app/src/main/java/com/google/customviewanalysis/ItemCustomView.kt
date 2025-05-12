package com.google.customviewanalysis

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

class ItemCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    val GOKU_IMAGE = 0
    val NAME = 1
    val IMG_1 = 2
    val IMG_2 = 3
    val DUMMY_TV = 4
    val BUTTON = 5
    val NEUTRAL = 6


    private var fullWidth: Int = 0
    private var fullHeight:Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        fullWidth = MeasureSpec.getSize(widthMeasureSpec)
        fullHeight = paddingTop + paddingBottom

        var child = getChildAt(GOKU_IMAGE)
        if (child.isVisible)
        {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
            fullHeight += child.measuredHeight
        }

        child = getChildAt(NAME)
        if (child.isVisible)
        {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
        }
        child = getChildAt(IMG_1)
        if (child.isVisible)
        {
            val widthUsed =(getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(NAME).measuredWidth+paddingLeft+paddingRight)
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, fullHeight)
        }
        child = getChildAt(IMG_2)
        if (child.isVisible)
        {
            val widthUsed =(getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(NAME).measuredWidth+paddingLeft+paddingRight)
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, fullHeight)
        }

        child = getChildAt(DUMMY_TV)
        if (child.isVisible)
        {
            val widthUsed =(getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(NAME).measuredWidth+paddingLeft+paddingRight)
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, fullHeight)
        }
        child = getChildAt(BUTTON)
        if (child.isVisible)
        {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
        }

        child = getChildAt(NEUTRAL)
        if (child.isVisible)
        {
            val widthUsed =(getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(BUTTON).measuredWidth+paddingLeft+paddingRight)
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, fullHeight)
        }

        setMeasuredDimension(fullWidth,fullHeight)

    }


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        var x = paddingLeft
        var y = paddingTop

        var child = getChildAt(GOKU_IMAGE)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)

            x += childWidth
        }

        child = getChildAt(NAME)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)
            y += childHeight
        }

        child = getChildAt(BUTTON)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)
            x += childWidth
        }

        child = getChildAt(NEUTRAL)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)
        }

        x = getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(NAME).measuredWidth+paddingLeft
        y = paddingTop


        child = getChildAt(IMG_1)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth/2
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)
            x += childWidth
        }
        child = getChildAt(IMG_2)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth/2
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)

            y += childHeight
        }

        x = getChildAt(GOKU_IMAGE).measuredWidth+getChildAt(NAME).measuredWidth

        child = getChildAt(DUMMY_TV)
        if (child.isVisible)
        {
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            child.layout(x,y,x+childWidth,y+childHeight)

        }

    }


    override fun generateDefaultLayoutParams(): LayoutParams
    {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams
    {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams
    {
        return MarginLayoutParams(context, attrs)
    }


    override fun measureChildWithMargins(child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int)
    {
        val lp = child.layoutParams as MarginLayoutParams

        val childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, widthUsed + lp.leftMargin + lp.rightMargin, lp.width)

        val childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, heightUsed + lp.topMargin + lp.bottomMargin, lp.height)

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

}