@file:Suppress("unused")

package lib.codegames.extension

import android.animation.ObjectAnimator
import android.os.Build
import android.support.annotation.ColorRes
import android.view.Window
import lib.codegames.graphics.ColorCG

fun Window.setStatusBarColor(@ColorRes colorId: Int, duration: Long = 0) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if(duration > 0) {
            val startColor = statusBarColor
            val endColor = ColorCG.parseColorResource(context, colorId)
            ObjectAnimator.ofArgb(this, "statusBarColor", startColor, endColor).apply {
                this.duration = duration
                start()
            }
        }else {
            statusBarColor = ColorCG.parseColorResource(context, colorId)
        }
    }

}