package lib.codegames.widget

import android.util.Size
import android.view.View
import lib.codegames.graphics.SizeCG

internal object Squarizer {

    fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int): SizeCG {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        if(height == 0) {
            return SizeCG(widthMeasureSpec, widthMeasureSpec)
        }else if(width == 0) {
            return SizeCG(heightMeasureSpec, heightMeasureSpec)
        }else {
            if(width < height) {
                return SizeCG(widthMeasureSpec, widthMeasureSpec)
            }else {
                return SizeCG(heightMeasureSpec, heightMeasureSpec)
            }
        }
    }

}