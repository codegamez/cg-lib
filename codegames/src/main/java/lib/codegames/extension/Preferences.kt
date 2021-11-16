@file:Suppress("unused")

package lib.codegames.extension

import android.content.SharedPreferences

inline fun <reified T: Any> SharedPreferences.get(key: String, default: T): T = when(default) {
    is Int -> getInt(key, default) as T
    is String -> getString(key, default) as T
    is Boolean -> getBoolean(key, default) as T
    is Float -> getFloat(key, default) as T
    is Long -> getLong(key, default) as T
    else -> throw Throwable("Wrong Type ${T::class.java.name}")
}

fun <T: Any> SharedPreferences.put(key: String, value: T): SharedPreferences.Editor = when(value) {
    is Int -> edit().putInt(key, value)
    is String -> edit().putString(key, value)
    is Boolean -> edit().putBoolean(key, value)
    is Float -> edit().putFloat(key, value)
    is Long -> edit().putLong(key, value)
    else -> throw Throwable("Wrong Type ${value::class.java.name}")
}