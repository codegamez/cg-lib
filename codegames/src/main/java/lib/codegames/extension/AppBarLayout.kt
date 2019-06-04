@file:Suppress("unused")

package lib.codegames.extension

import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.design.widget.AppBarLayout
import lib.codegames.graphics.ColorCG

fun AppBarLayout.setBackgroundColor(@ColorRes colorId: Int, duration: Long = 0) {

    val endColor = ColorCG.parseColorResource(context, colorId)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && duration > 0) {
        val startColor = (background as ColorDrawable).color
        ObjectAnimator.ofArgb(this, "backgroundColor", startColor, endColor).apply {
            this.duration = duration
            start()
        }
    }else {
        setBackgroundColor(endColor)
    }

}