package lib.codegames.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout

import java.util.Arrays

import lib.codegames.R
import lib.codegames.graphics.ColorCG
import lib.codegames.graphics.SizeCG

class ButtonCG : AppCompatButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        CustomFontUtils.applyCustomFont(this, context, attrs)
    }

}
