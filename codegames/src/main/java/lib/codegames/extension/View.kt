@file:Suppress("unused")

package lib.codegames.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun <T: View> T.runOnUiThread(action: T.() -> Unit) =
        context?.runOnUiThread { action.invoke(this) }

fun View.show(duration: Long = 0) = runOnUiThread {
    if(duration > 0) {
        visibility = View.VISIBLE
        animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        scaleX = 1F
                        scaleY = 1F
                        alpha = 1F
                        visibility = View.VISIBLE
                    }
                }).start()
    }else
        visibility = View.VISIBLE

}

fun View.hide(duration: Long = 0) = runOnUiThread {
    if(duration > 0) {
        animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        alpha = 0F
                        scaleX = 0F
                        scaleY = 0F
                        visibility = View.INVISIBLE
                    }
                }).start()
    }else
        visibility = View.INVISIBLE
}

fun View.gone(duration: Long = 0) = runOnUiThread {
    if(duration > 0) {
        animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(duration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        alpha = 0F
                        scaleX = 0F
                        scaleY = 0F
                        visibility = View.GONE
                    }
                }).start()
    }else
        visibility = View.GONE
}