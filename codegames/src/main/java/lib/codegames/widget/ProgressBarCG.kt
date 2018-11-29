package lib.codegames.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.SystemClock
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.ProgressBar
import lib.codegames.R
import lib.codegames.graphics.ColorCG
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
class ProgressBarCG : ProgressBar {

    private var circleBounds = RectF()
    private var barPaint: Paint? = null
    private var rectPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private val defProgress = 70
    private var progress: Int = 0
    private val defMax = 100
    private var max: Int = 0
    private val defBarWidth = 10
    private var barWidth: Int = 0
    private var loading = true
    private val defBarColor = -0xc0ae4b
    private val defBgColor = -0x1f1f20
    private var barColor = defBarColor

    private var mode: Mode = Mode.MODE_CIRCLE

    private var angleSpeed: Int = 0

    private var lastTimeAnimated: Long = 0
    private var angle = 0f
    private var startAngle = 0f

    private var rectMaxLength: Int = 0
    private var rectGap: Int = 0
    private var loopLength: Int = 0

    private var rq = ArrayList<Rect>()

    private var isLoading: Boolean
        get() = loading
        set(loading) = if (loading)
            startLoading()
        else
            stopLoading()

    enum class Mode {
        MODE_CIRCLE, MODE_LINEAR
    }

    constructor(context: Context) : super(context) {

        initialise()

        this.barColor = defBarColor
        barPaint!!.color = this.barColor
        this.barWidth = defBarWidth
        barPaint!!.strokeWidth = this.barWidth.toFloat()
        this.max = defMax
        this.progress = defProgress

        isLoading = loading
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        initialise()
        initialiseAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        initialise()
        initialiseAttributes(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

        initialise()
        initialiseAttributes(context, attrs)
    }

    private fun initialise() {
        circleBounds = RectF()

        barPaint = Paint()
        barPaint!!.color = barColor
        barPaint!!.isAntiAlias = true
        barPaint!!.style = Paint.Style.STROKE
        barPaint!!.strokeWidth = barWidth.toFloat()

        rectPaint = Paint()
        rectPaint!!.color = barColor
        rectPaint!!.isAntiAlias = true
        rectPaint!!.style = Paint.Style.FILL

        backgroundPaint = Paint()
        backgroundPaint!!.color = 0x00000000
        backgroundPaint!!.isAntiAlias = true
        backgroundPaint!!.style = Paint.Style.FILL

    }

    private fun initialiseAttributes(context: Context, attrs: AttributeSet) {
        val attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressBarCG)

        val bgColor = attributeArray.getColor(R.styleable.ProgressBarCG_backgroundColor, defBgColor)
        val barColor = attributeArray.getColor(R.styleable.ProgressBarCG_barColor, defBarColor)
        val barWidth = attributeArray.getDimensionPixelSize(R.styleable.ProgressBarCG_barWidth, defBarWidth)
        val max = attributeArray.getInt(R.styleable.ProgressBarCG_android_max, defMax)
        val progress = attributeArray.getInt(R.styleable.ProgressBarCG_android_progress, defProgress)
        val loading = attributeArray.getBoolean(R.styleable.ProgressBarCG_loading, true)
        val speed = attributeArray.getInt(R.styleable.ProgressBarCG_speed, 13)
        val mode = attributeArray.getInt(R.styleable.ProgressBarCG_progressMode, 0)
        attributeArray.recycle()

        this.angleSpeed = speed
        this.barColor = barColor
        this.barPaint!!.color = barColor
        this.rectPaint!!.color = barColor
        this.barWidth = barWidth
        this.backgroundPaint!!.color = bgColor
        this.barPaint!!.strokeWidth = barWidth.toFloat()
        this.max = max
        this.progress = progress
        this.mode = when(mode) {
            0 -> Mode.MODE_CIRCLE
            else -> Mode.MODE_LINEAR
        }
        this.backgroundPaint!!.color = bgColor

        isLoading = loading
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var radius = (Math.min(measuredWidth, measuredHeight) - barWidth / 2 - 1) / 2
        val maxPadding = Math.max(Math.max(paddingLeft, paddingRight), Math.max(paddingTop, paddingBottom))
        radius -= maxPadding / 2

        circleBounds.left = (barWidth / 2 + maxPadding + 1).toFloat()
        circleBounds.top = (barWidth / 2 + maxPadding + 1).toFloat()
        circleBounds.right = (radius * 2).toFloat()
        circleBounds.bottom = (radius * 2).toFloat()

        rectMaxLength = width / 4
        rectGap = width / 10
        loopLength = rectGap + rectMaxLength
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {

        if (mode == Mode.MODE_LINEAR)
            canvas.drawRect(paddingLeft.toFloat(), paddingTop.toFloat(), (canvas.width - paddingRight).toFloat(), (canvas.height - paddingBottom).toFloat(), backgroundPaint!!)

        if (loading && !isInEditMode) {

            var deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated).toFloat()

            if (deltaTime > 10000)
                deltaTime = 0f

            lastTimeAnimated = SystemClock.uptimeMillis()

            if (mode == Mode.MODE_CIRCLE) {

                val angleSpeed = deltaTime * this.angleSpeed / 100.0f
                val angleStartSpeed = deltaTime * 30 / 100.0f

                startAngle += angleStartSpeed
                startAngle %= 361f

                angle += angleSpeed

                val gap = 20f

                if (this.angle < gap && angleSpeed < 0) {
                    this.angle = gap
                    this.angleSpeed *= -1
                } else if (this.angle > 360 - gap && angleSpeed > 0) {
                    this.angle = 360 - gap
                    this.angleSpeed *= -1
                }

                canvas.drawArc(circleBounds, startAngle, angle, false, barPaint!!)

            } else if (mode == Mode.MODE_LINEAR) {

                val lineSpeed = deltaTime * this.angleSpeed.toFloat() * width.toFloat() / 13000.0f

                if (loopLength >= rectGap + rectMaxLength) {
                    loopLength = 0
                    rq.add(Rect(-rectMaxLength, 0, 0, height))
                }

                for (r in rq) {
                    if (r.left >= width) {
                        r.right = loopLength - (rectMaxLength + rectGap)
                        r.left = r.right - rectMaxLength
                        loopLength -= rectMaxLength + rectGap
                        break
                    }
                }

                for (r in rq) {
                    r.left += lineSpeed.toInt()
                    r.right += lineSpeed.toInt()

                    canvas.drawRect(r, rectPaint!!)
                }

                loopLength += lineSpeed.toInt()

                //                int center = getWidth() / 2;
                //
                //                if(rectStart >= rectGap) {
                //                    rectStart = 0;
                //                    rectLength = 0;
                //                }
                //
                //                if(rectLength == rectMaxLength) {
                //
                //                    rectStart += lineSpeed;
                //
                //                }else {
                //
                //                    rectLength += lineSpeed;
                //                    if(rectLength > rectMaxLength) {
                //                        rectLength = rectMaxLength;
                //                    }
                //
                //                }
                //
                //                if(rectStart > rectGap) {
                //                    canvas.drawRect(center, 0, center + rectStart - rectGap, getHeight(), rectPaint);
                //                    canvas.drawRect(center, 0, center - rectStart + rectGap, getHeight(), rectPaint);
                //                }
                //
                //                if(rectStart + rectLength < center - rectGap) {
                //                    canvas.drawRect(center + rectStart + rectLength + rectGap, 0, center + center, getHeight(), rectPaint);
                //                    canvas.drawRect(center - rectStart - rectLength - rectGap, 0, 0, getHeight(), rectPaint);
                //                }
                //
                //                canvas.drawRect(center + rectStart, 0, center + rectStart + rectLength, getHeight(), rectPaint);
                //                canvas.drawRect(center - rectStart, 0, center - rectStart - rectLength, getHeight(), rectPaint);

            }

            invalidate()
        } else {

            if (mode == Mode.MODE_CIRCLE) {
                val progress = (getProgress().toFloat() / getMax().toFloat() * 360f).toInt()
                canvas.drawArc(circleBounds, 0f, progress.toFloat(), false, barPaint!!)
            } else if (mode == Mode.MODE_LINEAR) {
                val progress = (getProgress().toFloat() / getMax().toFloat() * canvas.width.toFloat()).toInt()
                canvas.drawRect(0f, 0f, progress.toFloat(), canvas.height.toFloat(), rectPaint!!)
            }

        }

    }

    fun startLoading() {
        this.loading = true
        invalidate()
    }

    fun stopLoading() {
        this.loading = false
        invalidate()
    }

    override fun setBackgroundColor(color: Int) {
        backgroundPaint!!.color = color
        if (!loading)
            invalidate()
    }

    fun setBackgroundColorResources(@ColorRes resId: Int) {
        setBackgroundColor(ColorCG.parseColorResource(context, resId))
    }

    fun setBarColor(@ColorInt color: Int) {
        this.barColor = color
        barPaint!!.color = color
        rectPaint!!.color = color
        if (!loading)
            invalidate()
    }

    fun setBarColorResource(@ColorRes resId: Int) {
        setBarColor(ColorCG.parseColorResource(context, resId))
    }

    fun setProgressColor(@ColorInt color: Int) {
        setBarColor(color)
    }

    fun setProgressColorResource(@ColorRes resId: Int) {
        setBarColorResource(resId)
    }

    fun getBarColor(): Int {
        return barColor
    }

    fun getBarWidth(): Int {
        return barWidth
    }

    fun setBarWidth(barWidth: Int) {
        this.barWidth = barWidth
        barPaint!!.strokeWidth = barWidth.toFloat()
        if (!loading)
            invalidate()
    }

    override fun getProgress(): Int {
        return progress
    }

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        this.progress = progress
        if (!loading)
            invalidate()
    }

    override fun getMax(): Int {
        return max
    }

    override fun setMax(max: Int) {
        super.setMax(max)
        this.max = max
        if (!loading)
            invalidate()
    }

    fun setMode(mode: Mode) {
        this.mode = mode
        if (mode == Mode.MODE_CIRCLE)
            this.backgroundPaint!!.color = 0x00000000
        else if (mode == Mode.MODE_LINEAR)
            this.backgroundPaint!!.color = defBgColor
        if (!loading)
            invalidate()
    }

    fun getMode(): Mode {
        return mode
    }

}
