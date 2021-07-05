package top.derekdev.pin.ext

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout


/**
 * Created by  on 5/7/21.
 * Project: Pin Lock
 *
 */

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}
