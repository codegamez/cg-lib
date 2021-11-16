package lib.codegames.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatRadioButton
import android.util.AttributeSet

class RadioButtonCG : AppCompatRadioButton {

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
