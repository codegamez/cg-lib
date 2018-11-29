package lib.codegames.graphics

import android.content.res.Resources
import android.util.DisplayMetrics

class SizeCG(val width: Int, val height: Int) {

    companion object {

        private val displayMetrics = Resources.getSystem().displayMetrics
        private val density = displayMetrics.density

        val screenWidthPX: Int
            get() = displayMetrics.widthPixels

        val screenHeightPX: Int
            get() = displayMetrics.heightPixels

        fun dp2Px(dp: Int): Int {
            return (dp * density).toInt()
        }

        fun px2Dp(px: Int): Int {
            return (px / density).toInt()
        }

    }

}
