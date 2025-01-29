package com.rtl.customview.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

class MyCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    val CHILD1 = 0
    val CHILD2 = 1
    val CHILD3 = 2

    private var fullWidth: Int = 0
    private var fullHeight:Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        fullWidth = MeasureSpec.getSize(widthMeasureSpec)
        fullHeight = paddingTop + paddingBottom

        var child = getChildAt(CHILD1)

        if (child.isVisible){
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
            fullHeight += child.measuredHeight
        }

       child = getChildAt(CHILD2)
        if (child.isVisible){
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
            fullHeight += child.measuredHeight
        }

        child = getChildAt(CHILD3)
        if (child.isVisible){
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, fullHeight)
            fullHeight += child.measuredHeight
        }

        setMeasuredDimension(fullWidth-200, fullHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        Log.d("Sathish_SSS", "onLayout: $fullWidth $measuredWidth")
        var y = paddingTop
        var left = 0
        var right = 0

        var child = getChildAt(CHILD1)

        if (child.isVisible){
           child.placeView(measuredWidth,left,right,child.measuredWidth,child.measuredHeight)
            left += child.measuredWidth
            right += child.measuredHeight
        }

        child = getChildAt(CHILD2)
        if (child.isVisible){
            child.placeView(measuredWidth,left,right,left+child.measuredWidth,right+child.measuredHeight)
            left += child.measuredWidth
            right += child.measuredHeight
        }

        child = getChildAt(CHILD3)
        if (child.isVisible){
            child.placeView(measuredWidth,left,right,left+child.measuredWidth,right+child.measuredHeight)
        }

    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams
    {
        return ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams
    {
        return ViewGroup.MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams
    {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }


    override fun measureChildWithMargins(child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int)
    {
        val lp = child.layoutParams as MarginLayoutParams

        val childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, widthUsed + lp.leftMargin + lp.rightMargin, lp.width)

        val childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, heightUsed + lp.topMargin + lp.bottomMargin, lp.height)
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }
}


 fun View.placeView(fullWidth:Int,l: Int, t: Int, r: Int, b: Int) {
     when(layoutDirection){
         View.LAYOUT_DIRECTION_RTL ->{
             val left = fullWidth - r
             val right = fullWidth - l
             layout(left,t,right,b)
         }
         else ->{
             layout(l,t,r,b)
         }
     }
}

