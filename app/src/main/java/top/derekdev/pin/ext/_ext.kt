package top.derekdev.pin

import top.derekdev.pin.model.PinBlockResultData
import kotlin.math.ceil
import kotlin.random.Random


/**
 * Created by  on 3/7/21.
 * Project: Pin Lock
 *
 */



val Byte.hiNibbleValue get() = (((0xF0 and this.toInt()) shr 4)).toByte()
val Byte.lowNibbleValue get() = (0x0F and this.toInt()).toByte()




fun toByte(highNibble: Int = 0, lowNibble: Int = 0) : Byte {
    require(highNibble in Byte.MIN_VALUE .. Byte.MAX_VALUE)
    require(lowNibble in Byte.MIN_VALUE .. Byte.MAX_VALUE)
    return ((highNibble shl 4) or (lowNibble)).toByte()
}

val Float.roundUpInt get() = ceil(this).toInt()
val Double.roundUpInt get() = ceil(this).toInt()

fun getRandomIntArray(size: Int, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE) : IntArray = IntArray(size) {
    Random.nextInt(min, max)
}

val String.hexDigitByteArray : ByteArray get() {
    return reversed().windowed(2, 2, true) {
        val low = it.first().digitToInt()
        val high = it.getOrNull(1)?.digitToInt() ?: 0
        toByte(high, low)
    }.reversed().toByteArray()
}

fun ByteArray.toDebugString(radix: Int = 16, capitalized: Boolean = true, separator: CharSequence = "") : String {
    val hexStr = this.joinToString(separator = separator) { "${it.hiNibbleValue.toString(radix)}${it.lowNibbleValue.toString(radix)}" }
    return if (capitalized) hexStr.uppercase() else hexStr
}




