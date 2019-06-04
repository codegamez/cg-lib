@file:Suppress("unused")

package lib.codegames.extension

fun String.fanum(): String {
    val arr = toCharArray()

    arr.forEachIndexed { i, c ->
        when (c) {
            '0' -> arr[i] = '۰'
            '1' -> arr[i] = '۱'
            '2' -> arr[i] = '۲'
            '3' -> arr[i] = '۳'
            '4' -> arr[i] = '۴'
            '5' -> arr[i] = '۵'
            '6' -> arr[i] = '۶'
            '7' -> arr[i] = '۷'
            '8' -> arr[i] = '۸'
            '9' -> arr[i] = '۹'
        }
    }
    return String(arr)
}

fun Int.fanum(): String {
    return toString().fanum()
}

fun Float.fanum(): String {
    return toString().fanum()
}

fun String.etc(length: Int): String {
    return if(this.length <= length)
        this
    else
        this.substring(0, length) + "..."
}