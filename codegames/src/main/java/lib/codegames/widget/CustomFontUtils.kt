package lib.codegames.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import lib.codegames.R
import java.util.*

internal object CustomFontUtils {

    // Example xml
    // app:fontPath="fonts/Aban.ttf"

    fun applyCustomFont(textView: TextView, context: Context, attrs: AttributeSet) {
        val attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.TextViewCG)

        var fontPath = attributeArray.getString(R.styleable.TextViewCG_cg_fontPath)

        if (fontPath != null) {

            if (fontPath.startsWith("/"))
                fontPath = fontPath.substring(1, fontPath.length)

            val typeface = FontCache.getTypeface(context, fontPath)
            textView.typeface = typeface

        }

        attributeArray.recycle()
    }

    fun getCustomFont(context: Context, fontPath: String): Typeface? {
        var fp = fontPath

        if (fontPath.startsWith("/"))
            fp = fontPath.substring(1, fontPath.length)

        return FontCache.getTypeface(context, fp)
    }

    private object FontCache {

        private val fontCache = HashMap<String, Typeface>()

        fun getTypeface(context: Context, fontname: String): Typeface? {
            var typeface: Typeface? = fontCache[fontname]

            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.assets, fontname)
                } catch (e: Exception) {
                    return null
                }

                fontCache[fontname] = typeface
            }

            return typeface
        }

    }

}
