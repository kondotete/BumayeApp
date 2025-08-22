package kondotete.bumaye_app.utils

object ValidationUtils {

    // Validation du numéro de téléphone
    fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^[0-9]{8,12}$"))
    }

    // Validation du nom
    fun isValidName(name: String): Boolean {
        return name.trim().isNotEmpty() && name.trim().length >= 2
    }

    // Validation du montant
    fun isValidAmount(amount: String): Boolean {
        val value = amount.toDoubleOrNull()
        return value != null && value >= 0
    }

    // Validation du format de date
    fun isValidDateFormat(date: String): Boolean {
        return date.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$"))
    }

    // Comparaison de dates
    fun isDateAfterOrEqual(dateToCheck: String, referenceDate: String): Boolean {
        try {
            val format = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val checkDate = format.parse(dateToCheck)
            val refDate = format.parse(referenceDate)
            return checkDate != null && refDate != null && !checkDate.before(refDate)
        } catch (e: Exception) {
            return false
        }
    }
}
