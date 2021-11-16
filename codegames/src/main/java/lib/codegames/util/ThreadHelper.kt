@file:Suppress("unused")

package lib.codegames.util

import android.content.Context
import android.os.AsyncTask

fun Context.doAsync(action: () -> Unit): AsyncTask<Unit, Unit, Unit> {
    val task = object : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            return action.invoke()
        }
    }
    task.execute()
    return task
}