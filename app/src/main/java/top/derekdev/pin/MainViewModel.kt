package top.derekdev.pin

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.derekdev.pin.model.PinBlockInputData
import top.derekdev.pin.model.PinBlockResultData.Companion.debugInfo


/**
 * Created by  on 5/7/21.
 * Project: Pin Lock
 *
 */

class MainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val format3PinBlock = Format3PinBlock()

    val pinString = MutableLiveData<String>()
    val panString = MutableLiveData<String>()
    val randomBytes = MutableLiveData<ByteArray>()
    val randomBytesStr = randomBytes.map { it.toDebugString() }
    val consoleText = MutableLiveData<String>("")
    val errorText = MutableLiveData<String?>(null)

    init {
        log("initial")
        _reset()

        pinString.observeForever {
            if (errorText.value != null) {
                errorText.value = null
            }
        }
    }

    fun refreshRandomBytes() {
        val newRandomByteValue = Format3PinBlock.getRandomFillByteArray()
        logOperation("refreshRandomBytes: ${newRandomByteValue.toDebugString()}")
        randomBytes.value = newRandomByteValue
    }

    fun calculate() {

        viewModelScope.launch(Dispatchers.Default) {
            val pinString = pinString.value ?: return@launch
            val panString = panString.value ?: return@launch
            val randomBytes = randomBytes.value ?: return@launch

            if (validatePin(pinString)) {
                val result = format3PinBlock.encode(pinString, panString, randomBytes)
                withContext(Dispatchers.Main) {
                    errorText.value = null
                    logOperation("calculate")
                    log("$CALCULATION_SPLITTER\n${result.debugInfo}\n$CALCULATION_SPLITTER")
                }
            } else withContext(Dispatchers.Main) {
                errorText.value = "invalid pin:$pinString"
                logOperation("calculate error, invalid pin:$pinString")
            }
        }
    }

    fun clearConsole() {
        consoleText.value = ""
        logOperation("clearConsole")
    }

    fun reset() {
        _reset()
        logOperation("reset")
    }

    private fun _reset() {
        pinString.value = DEFAULT_PIN_STR
        panString.value = DEFAULT_PAN_STR
        randomBytes.value = Format3PinBlock.getRandomFillByteArray()
        consoleText.value = ""
        errorText.value = null
    }

    private fun validatePin(pin: String) = pin.length in 4 .. 12

    private fun logOperation(log: String, newLine: Boolean = true) = log(OPERATION_PREFIX + log, newLine)

    private fun log(log: String, newLine: Boolean = true) {
        consoleText.value += log
        if (newLine) consoleText.value += '\n'

    }



    companion object {
        private const val DEFAULT_PIN_STR = "1234"
        private const val DEFAULT_PAN_STR = "1111222233334444"
        private const val OPERATION_PREFIX = "* "
        private val CALCULATION_SPLITTER = "-".repeat(18)
    }

}
