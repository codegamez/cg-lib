package lib.codegames.ad

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import lib.codegames.R
import lib.codegames.debug.LogCG
import lib.codegames.market.MarketManager
import lib.codegames.widget.ImageViewCG
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class BannerAdCG : ImageViewCG, BannerAdCGClient {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?)
            : super(context, attrs!!) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs!!, defStyleAttr) {
        init()
    }

    private fun init() {

        scaleType = ImageView.ScaleType.FIT_XY
        setImageResource(R.drawable.banner_320x50)

        if (!isInEditMode) {

            if (activate) {
                visibility = View.VISIBLE
                isClickable = true

                BannerAdCGManager.setBannerClient(this)
                if (BannerAdCGManager.isBannerLoaded())
                    BannerAdCGManager.showRandomAd()
                else
                    BannerAdCGManager.getBanners()
            } else {
                visibility = View.GONE
                isClickable = false
            }

        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = width * 5 / 32
        setMeasuredDimension(width, height)
    }

    override fun showAd(banner: BannerAdCGModel) {
        val imageTask = GetImageTask(banner)
        imageTask.execute()
    }

    private inner class GetImageTask(private val banner: BannerAdCGModel?) : AsyncTask<Void, Bitmap, Bitmap>() {

        override fun onPreExecute() {
            super.onPreExecute()
            visibility = View.VISIBLE
            isClickable = true
        }

        override fun doInBackground(vararg voids: Void): Bitmap? {

            try {

                val url = URL(banner!!.image)
                val connection = url.openConnection() as HttpURLConnection
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) { // On Success
                    return BitmapFactory.decodeStream(connection.inputStream)
                } else { // On Failure
                    LogCG.d("Banner Ad CG (GetImage) Error Connect To Url: $responseCode Code")
                    return null
                }

            } catch (e: MalformedURLException) { // On Failure
                e.printStackTrace()
                LogCG.d("Banner Ad CG (GetImage) Error Malformed URL Exception")
                return null
            } catch (e: IOException) { // On Failure
                e.printStackTrace()
                LogCG.d("Banner Ad CG (GetImage) Error IO Exception")
                return null
            }

        }

        override fun onPostExecute(bitmap: Bitmap?) {
            super.onPostExecute(bitmap)

            if (bitmap != null) {

                val animOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                val animIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)

                animOut.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {

                        setImageBitmap(bitmap)

                        animIn.setAnimationListener(object : Animation.AnimationListener {

                            override fun onAnimationStart(animation: Animation) {}

                            override fun onAnimationRepeat(animation: Animation) {}

                            override fun onAnimationEnd(animation: Animation) {

                                setOnClickListener {
                                    if (banner!!.link.startsWith("http://") or banner.link.startsWith("https://")) {

                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(banner.link)
                                        context.startActivity(intent)

                                    } else {

                                        MarketManager.openAppPage(context, banner.link)

                                    }
                                }

                            }
                        })

                        startAnimation(animIn)

                    }

                    override fun onAnimationRepeat(animation: Animation) {}

                })

                startAnimation(animOut)

            }

        }
    }

    companion object {

        var activate = true
    }

}
