package top.derekdev.pin

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by  on 4/7/21.
 * Project: Pin Lock
 *
 */

class extKtTest {

    @Test
    fun testToByte() {
        val high = 0b1111
        val low = 0b0000
        val expected = 0b11110000.toByte()
        val actual = toByte(high, low)
        assertEquals(expected, actual)
    }
}
