package lib.codegames.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import lib.codegames.R
import lib.codegames.graphics.ColorCG
import lib.codegames.graphics.SizeCG

open class ImageViewCG : AppCompatImageView {

    private val mBounds = RectF()
    private val mBackgroundPaint = Paint()
    private val mBorderPaint = Paint()
    private val mTextPaint = Paint()
    private val mBitmapPaint = Paint()
    private val mTextBounds = Rect()
    private val mShaderMatrix = Matrix()
    private var mInitialised: Boolean = false
    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null

    var borderSize: Int = 0
        set(value) {
            field = value
            mBorderPaint.strokeWidth = value.toFloat()
            fixMainBounds()
        }

    var borderColor = Color.BLACK
        set(value) {
            field = value
            mBorderPaint.color = value
            if(mInitialised) invalidate()
        }

    var cgBackgroundColor = Color.WHITE
        set(value) {
            field = value
            mBackgroundPaint.color = value
            if(mInitialised) invalidate()
        }

    var textColor = Color.BLACK
        set(value) {
            field = value
            mTextPaint.color = value
            if(mInitialised) invalidate()
        }

    var textSize: Int = 0
        set(value) {
            field = SizeCG.dp2Px(value)
            mTextPaint.textSize = value.toFloat()
            mTextPaint.getTextBounds(this.text, 0, this.text.length, mTextBounds)
            if(mInitialised) invalidate()
        }

    var text: String = ""
        set(value) {
            field = value
            mTextPaint.getTextBounds(this.text, 0, text.length, mTextBounds)
            if(mInitialised) invalidate()
        }

    var mode: Mode? = null
        set(value) {
            field = value
            update()
        }

    enum class Mode {
        IMAGE_SQUARE,
        IMAGE_CIRCLE,
        TEXT_SQUARE,
        TEXT_CIRCLE,
        IMAGE_NORMAL
    }

    constructor(context: Context?)
            : super(context)

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs) {
        initialise(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initialise(context, attrs)
    }

    private fun initialise(context: Context?, attrs: AttributeSet?) {
        val attributeArray = context?.obtainStyledAttributes(
                attrs,
                R.styleable.ImageViewCG)

        val text = attributeArray?.getString(R.styleable.ImageViewCG_android_text) ?: ""
        val fontPath = attributeArray?.getString(R.styleable.ImageViewCG_cg_fontPath)
        val textColor = attributeArray?.getColor(R.styleable.ImageViewCG_android_textColor, defTextColor) ?: defTextColor
        val backgroundColor = attributeArray?.getColor(R.styleable.ImageViewCG_cg_backgroundColor, defBackgroundColor) ?: defBackgroundColor
        val borderColor = attributeArray?.getColor(R.styleable.ImageViewCG_cg_borderColor, defBorderColor) ?: defBorderColor
        val textSize = attributeArray?.getDimensionPixelSize(R.styleable.ImageViewCG_android_textSize, defTextSize) ?: defTextSize
        val borderSize = attributeArray?.getDimensionPixelSize(R.styleable.ImageViewCG_cg_borderSize, defBorderSize) ?: defBorderColor
        val mode = attributeArray?.getInt(R.styleable.ImageViewCG_cg_imageMode, -1) ?: -1

        attributeArray?.recycle()

        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.style = Paint.Style.FILL

        mBorderPaint.isAntiAlias = true
        mBorderPaint.style = Paint.Style.STROKE

        mTextPaint.isAntiAlias = true
        mTextPaint.isLinearText = true
        mTextPaint.textAlign = Paint.Align.CENTER

        this.mode = when (mode) {
            0 -> Mode.IMAGE_SQUARE
            1 -> Mode.IMAGE_CIRCLE
            2 -> Mode.TEXT_SQUARE
            3 -> Mode.TEXT_CIRCLE
            4 -> Mode.IMAGE_NORMAL
            else -> Mode.IMAGE_NORMAL
        }

        this.cgBackgroundColor = backgroundColor
        this.borderColor = borderColor
        this.borderSize = borderSize
        this.text = text
        this.textColor = textColor
        this.textSize = textSize

        setFont(fontPath)

        mInitialised = true
    }

    private fun update() {

        if (mode == Mode.IMAGE_CIRCLE) {
            if (drawable != null) {
                mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                mBitmapPaint.isAntiAlias = true
                mBitmapPaint.shader = mBitmapShader
            }
        }

        if(mInitialised)
            invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mode != Mode.IMAGE_NORMAL) {
            val measure = Squarizer.measure(widthMeasureSpec, heightMeasureSpec)
            super.onMeasure(measure.width, measure.height)
        }else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        fixMainBounds()
    }

    private fun fixMainBounds() {
        mBounds.set((borderSize / 2).toFloat(), (borderSize / 2).toFloat(),
                (measuredWidth - borderSize / 2).toFloat(),
                (measuredHeight - borderSize / 2).toFloat())
    }

    public override fun onDraw(canvas: Canvas) {

        if (mode == Mode.IMAGE_CIRCLE) {

            val maxPadding = Math.max(Math.max(paddingLeft, paddingRight), Math.max(paddingTop, paddingBottom))

            canvas.drawOval(mBounds, mBackgroundPaint)
            if (mBitmap != null) {
                val xscale = (mBounds.width() - this.borderSize.toFloat() - (2 * maxPadding).toFloat() + 2) / mBitmap!!.width.toFloat()
                val yscale = (mBounds.height() - this.borderSize.toFloat() - (2 * maxPadding).toFloat() + 2) / mBitmap!!.height.toFloat()
                mShaderMatrix.setScale(xscale, yscale)
                mShaderMatrix.postTranslate((this.borderSize + maxPadding - 1).toFloat(), (this.borderSize + maxPadding - 1).toFloat())
                mBitmapShader!!.setLocalMatrix(mShaderMatrix)

                canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), (mBounds.width().toInt() / 2 - maxPadding - this.borderSize / 2 + 1).toFloat(), mBitmapPaint)
            }

            if (this.borderSize > 0)
                canvas.drawOval(mBounds, mBorderPaint)

        } else if (mode == Mode.TEXT_CIRCLE) {

            canvas.drawOval(mBounds, mBackgroundPaint)
            val x = mBounds.centerX()
            val y = mBounds.centerY() - mTextBounds.centerY()
            canvas.drawText(text, x, y, mTextPaint)

            if (this.borderSize > 0)
                canvas.drawOval(mBounds, mBorderPaint)

        } else if (mode == Mode.IMAGE_SQUARE) {

            canvas.drawRect(mBounds, mBackgroundPaint)

            super.onDraw(canvas)

            if (this.borderSize > 0)
                canvas.drawRect(mBounds, mBorderPaint)

        } else if (mode == Mode.TEXT_SQUARE) {

            canvas.drawRect(mBounds, mBackgroundPaint)
            val x = mBounds.centerX()
            val y = mBounds.centerY() - mTextBounds.centerY()
            canvas.drawText(text, x, y, mTextPaint)
            canvas.drawRect(mBounds, mBorderPaint)

        } else if (mode == Mode.IMAGE_NORMAL) {
            super.onDraw(canvas)
        }

    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        update()
    }

    fun setTextColorHex(textColor: String) {
        this.textColor = ColorCG.parseColor(textColor, defTextColor)
    }

    fun setTextColorResource(resId: Int) {
        textColor = ColorCG.parseColorResource(context, resId)
    }

    fun setFont(fontPath: String?) {
        if(fontPath == null)
            return
        val typeface = CustomFontUtils.getCustomFont(context, fontPath) ?: return
        mTextPaint.typeface = typeface
        if(mInitialised)
            invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        val bitmap: Bitmap

        if (drawable == null)
            return null

        if (drawable is BitmapDrawable)
            bitmap = drawable.bitmap
        else {
            bitmap = try {

                if (drawable is ColorDrawable)
                    Bitmap.createBitmap(2, 2, BITMAP_CONFIG)
                else
                    Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        return bitmap
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        this.mBitmap = bm
        update()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        mBitmap = getBitmapFromDrawable(drawable)
        update()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        mBitmap = getBitmapFromDrawable(ContextCompat.getDrawable(context, resId))
        update()
    }

    companion object {

        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private val defBorderColor = Color.parseColor("#3F51B5")
        private val defBackgroundColor = Color.parseColor("#2196F3")
        private val defTextColor = Color.parseColor("#fdfdfd")
        private val defTextSize = SizeCG.dp2Px(15)
        private val defBorderSize = SizeCG.dp2Px(2)
    }

}