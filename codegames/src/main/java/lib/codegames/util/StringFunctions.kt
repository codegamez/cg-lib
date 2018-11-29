package lib.codegames.util

import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
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