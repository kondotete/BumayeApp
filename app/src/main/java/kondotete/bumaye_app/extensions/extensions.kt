package kondotete.bumaye_app.extensions

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

// EditText - Extensions pour EditText
fun EditText.textValue(): String = this.text.toString().trim()

fun TextInputLayout.showError(message: String) {
    this.error = message
    this.isErrorEnabled = true
}

fun TextInputLayout.hideError() {
    this.error = null
    this.isErrorEnabled = false
}

// Effacer toutes les erreurs
fun clearAllErrors(vararg layouts: TextInputLayout) {
    layouts.forEach { it.hideError() }
}
