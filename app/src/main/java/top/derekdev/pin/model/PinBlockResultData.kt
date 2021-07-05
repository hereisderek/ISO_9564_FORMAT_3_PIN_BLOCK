package top.derekdev.pin.model

import top.derekdev.pin.toDebugString


/**
 * Created by  on 5/7/21.
 * Project: Pin Lock
 *
 */

data class PinBlockResultData(
    val pinString: String,
    val panString: String,
    val pinBytes: ByteArray,
    val panBytes: ByteArray,
    val randomBytes: ByteArray,
    val pinBlock: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PinBlockResultData

        if (pinString != other.pinString) return false
        if (panString != other.panString) return false
        if (!pinBytes.contentEquals(other.pinBytes)) return false
        if (!panBytes.contentEquals(other.panBytes)) return false
        if (!randomBytes.contentEquals(other.randomBytes)) return false
        if (!pinBlock.contentEquals(other.pinBlock)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pinString.hashCode()
        result = 31 * result + panString.hashCode()
        result = 31 * result + pinBytes.contentHashCode()
        result = 31 * result + panBytes.contentHashCode()
        result = 31 * result + randomBytes.contentHashCode()
        result = 31 * result + pinBlock.contentHashCode()
        return result
    }

    companion object {
        private const val SPACE_CHAR = ' '
        private fun StringBuilder.appendValue(item: String, value: String, padTo: Int = 14, newLine: Boolean = true) = this.apply {
            append(item.padEnd(padTo, SPACE_CHAR)).append(value)
            if (newLine) append('\n')
        }

        val PinBlockResultData.debugInfo: String get() = StringBuilder().apply {
            appendValue("pinString:", pinString)
            appendValue("panString:", panString)
            appendValue("pinBytes:", pinBytes.toDebugString(separator = " "))
            appendValue("panBytes:", panBytes.toDebugString(separator = " "))
            appendValue("random:", randomBytes.toDebugString(separator = " "))
            appendValue("pinBlock:", pinBlock.toDebugString(separator = " "))
        }.toString()

    }
}


