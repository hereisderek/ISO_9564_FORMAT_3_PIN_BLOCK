package top.derekdev.pin

import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by  on 3/7/21.
 * Project: Pin Lock
 *
 */

class Format3PinBlockTest {

    private val format3Block = Format3PinBlock()



    @Test
    fun `transformPin success test`() {
        val input = "123456"
        val expected = intArrayOf(1,2,3,4,5,6)
        Assert.assertArrayEquals(expected, format3Block.transformPin(input))
    }

    @Test
    fun `transformPin should truncate pin that's longer than 12 digits`() {
        val originalIntArray = (0..9).toList().let {
            IntArray(10 * 2){ it % 10 }
        }
        val input = originalIntArray.joinToString(separator = "") { it.toString() }
        val firstIndex = originalIntArray.size - 12
        val expected = originalIntArray.slice(firstIndex until originalIntArray.size).toIntArray()
        Assert.assertArrayEquals(expected, format3Block.transformPin(input))
    }

    @Test
    fun `test getPinByteArray`() {
        val randomByteArray = ByteArray(8) { toByte(0, 0) }
        val pinIntArray = intArrayOf(1, 1, 1, 1)
        val actual = format3Block.getPinByteArray(pinIntArray, randomByteArray)

        val expected = ByteArray(8) {
            when(it) {
                0 -> toByte(3, pinIntArray.size)
                in 1 .. 2 -> toByte(1, 1)
                else -> toByte(0, 0)
            }
        }
        assertArrayEquals(actual, expected)
    }

    @Test
    fun `test preparePan`(){
        val expected = "0000576000821952".hexDigitByteArray
        verifyPan("6225760008219524", expected, "normal pan")
    }

    private fun verifyPan(panString: String, expected: ByteArray, msg: String = "testing pan:$panString") {
        val actual = format3Block.preparePan(panString)
        println("expected:${expected.toDebugString(16)} actual:${actual.toDebugString(16)}")
        assertArrayEquals(msg, expected, actual)
    }


    @Test
    fun `test encode`() {
        val pinStr = "1234"
        val pan = "43219876543210987"
        val randomFill = ByteArray(8)
        val actual = format3Block.encode(pinStr, pan, randomFill)
        assertEquals("3412AC7654321098", actual)
    }


}
