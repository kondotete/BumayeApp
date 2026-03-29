package kondotete.bumaye_app.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Obtenir la date actuelle sous forme de chaîne
    fun getCurrentDateString(): String {
        return dateFormat.format(Date())
    }

    //- Analyser une chaîne de date
    fun parseDate(dateString: String): Date? {
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    // Formater une date
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    // Comparer deux dates
    fun compareDates(date1: String, date2: String): Int {
        val d1 = parseDate(date1)
        val d2 = parseDate(date2)

        return when {
            d1 == null || d2 == null -> 0
            d1.before(d2) -> -1
            d1.after(d2) -> 1
            else -> 0
        }
    }

    // Valider le format de date
    fun isValidDateFormat(dateString: String): Boolean {
        return try {
            dateFormat.parse(dateString) != null
        } catch (e: Exception) {
            false
        }
    }

    //Ajouter des jours à une date
    fun addDaysToDate(dateString: String, days: Int): String? {
        val date = parseDate(dateString) ?: return null
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return formatDate(calendar.time)
    }
}