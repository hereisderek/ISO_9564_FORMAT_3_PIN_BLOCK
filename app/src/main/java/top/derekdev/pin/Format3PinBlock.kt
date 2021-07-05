package top.derekdev.pin

import androidx.annotation.Size
import androidx.annotation.VisibleForTesting
import top.derekdev.pin.model.PinBlockResultData
import top.derekdev.pin.model.PinBlockResultData.Companion.debugInfo
import kotlin.experimental.xor
import kotlin.random.Random


/**
 * Created by  on 3/7/21.
 * Project: Pin Lock
 *
 */

class Format3PinBlock {

    fun encode(pinStr: String, panStr: String) : PinBlockResultData = encode(pinStr, panStr, getRandomFillByteArray(8))

    fun encode(
        pinStr: String,
        panStr: String,
        @Size(8) randomByteArray: ByteArray
    ) : PinBlockResultData {
        val pinIntArray = transformPin(pinStr)
        val pinBytes = getPinByteArray(pinIntArray, randomByteArray)
        val panBytes = preparePan(panStr)
        require(pinBytes.size == 8)
        require(panBytes.size == 8)
        val pinBlock = ByteArray(8) {
            pinBytes[it] xor panBytes[it]
        }
        return PinBlockResultData(
            pinStr,
            panStr,
            pinBytes,
            panBytes,
            randomByteArray,
            pinBlock
        ).also {
            println(it.debugInfo)
        }
    }

    @VisibleForTesting
    internal fun getPinByteArray(pinIntArray: IntArray, randomByteArray: ByteArray) : ByteArray = IntArray(16) {
        when(it)  {
            0 -> 3
            1 -> pinIntArray.size
            else -> if (it - 2 < pinIntArray.size) {
                pinIntArray[it - 2]
            } else {
                val high = it % 2 == 0
                randomByteArray[it % 2].run {
                    if (high) hiNibbleValue else lowNibbleValue
                }.toInt()
            }
        }
    }.run {
        ByteArray(size / 2) {
            val high = get(it * 2)
            val low = get(it * 2 + 1)
            toByte(high, low)
        }
    }

    @VisibleForTesting
    internal fun transformPin(pinStr: String) : IntArray {
        require(pinStr.length >= 4)

        val pin = pinStr.let {
            if (it.length > 12) it.substring(it.length - 12, it.length) else it
        }
        return IntArray(pin.length) {
            pin[it].digitToInt()
        }
    }

    internal fun preparePan(panStr: String) : ByteArray {
        require(panStr.isNotEmpty())
        val panIndexOffset = (panStr.length - 1) - 12
        val transformedPan = panStr.subSequence(maxOf(0, panIndexOffset), panStr.length - 1).reversed()
        val panBytes = transformedPan.windowed(2, 2, true) {
            val low = it.first().digitToInt()
            val high = it.getOrNull(1)?.digitToInt() ?: 0
            toByte(high, low)
        }.reversed().toByteArray()

        val prefixPaddingBytes = ByteArray(8 - panBytes.size)
        return (prefixPaddingBytes + panBytes).also {
            check(it.size == 8)
        }
    }



    companion object {
        @Suppress("SameParameterValue")
        fun getRandomFillByteArray(size: Int = 8) = ByteArray(size) {
            toByte(10, Random.nextInt(0, 0xF))
        }
    }

}
