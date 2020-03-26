package com.codegames.codegameslibrarytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*
import lib.codegames.extension.simpleSetup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayOf("name 1" to 1, "name 2" to 2)

        spinner.simpleSetup(data) { it.first }

    }

}