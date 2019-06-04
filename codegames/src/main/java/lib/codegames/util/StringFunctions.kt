@file:Suppress("unused")

package lib.codegames.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.util.Base64
import java.util.*

fun String.sha1(): String {
    var sb:String
    try {
        val mDigest = MessageDigest.getInstance("SHA1")
        val result = mDigest.digest(this.toByteArray())
        val formatter = Formatter()
        for (b in result) {
            formatter.format("%02x", b)
        }
        sb = formatter.toString()
        formatter.close()
    }catch (e: NoSuchAlgorithmException) {
        sb = Random().nextInt().toString()
    }
    return sb
}


fun String.sha2(): String {
    var sb:String
    try {
        val mDigest = MessageDigest.getInstance("SHA2")
        val result = mDigest.digest(this.toByteArray())
        val formatter = Formatter()
        for (b in result) {
            formatter.format("%02x", b)
        }
        sb = formatter.toString()
        formatter.close()
    }catch (e: NoSuchAlgorithmException) {
        sb = Random().nextInt().toString()
    }
    return sb
}

fun String.getFormat() :String? {
    val index = this.lastIndexOf('.')
    return if(index <= 0 || index == this.length)
        null
    else
        return this.substring(index+1, this.length)
}

fun String.getNameNoFormat() :String {
    val index = this.lastIndexOf('.')
    return if(index <= 0)
        this
    else
        this.substring(0, index)
}

fun String.decodeBase64(): String {
    val data = Base64.decode(this, Base64.DEFAULT)
    return String(data, Charsets.UTF_8)
}

fun String.encodeBase64(): String {
    val data = toByteArray(Charsets.UTF_8)
    return Base64.encodeToString(data, Base64.DEFAULT)
}