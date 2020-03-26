@file:Suppress("unused")

package lib.codegames.extension

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.widget.Toast

fun Context.toastLong(resId: Int) = runOnUiThread {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(text: String) = runOnUiThread {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.toast(resId: Int) = runOnUiThread {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.toast(text: String) = runOnUiThread {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.runOnUiThread(action: () -> Unit) {
    if(this is Activity) runOnUiThread { action.invoke() }
}

val Context.preferences: SharedPreferences
    get() = getSharedPreferences(applicationContext.packageName, Context.MODE_PRIVATE)

fun Context.getPermissionIfNotGranted(permission: String): String? {
    return if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
        permission
    else
        null
}