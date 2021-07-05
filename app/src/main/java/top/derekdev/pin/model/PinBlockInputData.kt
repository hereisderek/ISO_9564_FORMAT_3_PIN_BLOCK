package top.derekdev.pin.model

import top.derekdev.pin.toDebugString


/**
 * Created by  on 5/7/21.
 * Project: Pin Lock
 *
 */

data class PinBlockInputData(
    val pinString: String,
    val panString: String,
    val randomBytes: ByteArray
) {
    val randomBytesString: String get() = randomBytes.toDebugString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PinBlockInputData

        if (pinString != other.pinString) return false
        if (panString != other.panString) return false
        if (!randomBytes.contentEquals(other.randomBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pinString.hashCode()
        result = 31 * result + panString.hashCode()
        result = 31 * result + randomBytes.contentHashCode()
        return result
    }
}
