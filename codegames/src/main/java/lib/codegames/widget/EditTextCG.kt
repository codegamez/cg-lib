package lib.codegames.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class EditTextCG : EditText {

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
