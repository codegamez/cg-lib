package lib.codegames.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import lib.codegames.R

class CheckableImageView: ImageViewCG, Checkable {

    private var onImage: Int = 0
    private var offImage: Int = 0

    private var mChecked = false

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        visibility = if(checked)
            if(onImage == 0) {
                View.GONE
            }else {
                setImageResource(onImage)
                View.VISIBLE
            }
        else
            if(offImage == 0) {
                View.GONE
            }else {
                setImageResource(offImage)
                View.VISIBLE
            }
        mChecked = checked
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    constructor(context: Context?)
            : super(context) {
        setupAttrs(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs) {
        setupAttrs(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        setupAttrs(context, attrs)
    }

    private fun setupAttrs(context: Context?, attrs: AttributeSet?) {

        val cAttr = context?.obtainStyledAttributes(attrs,
                R.styleable.CheckableImageView, 0, 0)

        val onImage = cAttr?.getResourceId(
                R.styleable.CheckableImageView_cg_image_on, 0) ?: 0

        val offImage = cAttr?.getResourceId(
                R.styleable.CheckableImageView_cg_image_off, 0) ?: 0

        val checked = cAttr?.getBoolean(
                R.styleable.CheckableImageView_android_checked, false) ?: false

        cAttr?.recycle()

        this.onImage = onImage
        this.offImage = offImage

        isChecked = checked

    }

}