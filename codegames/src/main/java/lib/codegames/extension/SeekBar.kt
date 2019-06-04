@file:Suppress("unused")

package lib.codegames.extension

import android.widget.SeekBar

fun SeekBar.setOnSeekBarChangeListener(
        onProgressChanged: (seekBar: SeekBar?, progress: Int, fromUser: Boolean) -> Unit) {

    setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChanged.invoke(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    })

}