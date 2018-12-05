package com.codegames.codegameslibrarytest

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animatable = imageView.drawable as Animatable

        imageView.setOnClickListener {
            animatable.start()
        }

    }

}
