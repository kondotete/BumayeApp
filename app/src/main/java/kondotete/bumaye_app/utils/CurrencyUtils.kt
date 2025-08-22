package kondotete.bumaye_app.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object CurrencyUtils {
    private val currencyFormat = NumberFormat.getInstance(Locale.FRANCE).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }

    // 格式化金额为FCFA - Formater le montant en FCFA
    fun formatAmount(amount: Double): String {
        return "${currencyFormat.format(amount)} FCFA"
    }

    // 格式化金额字符串 - Formater une chaîne de montant
    fun formatAmount(amountString: String): String {
        val amount = amountString.toDoubleOrNull() ?: 0.0
        return formatAmount(amount)
    }

    // 解析金额字符串 - Analyser une chaîne de montant
    fun parseAmount(formattedAmount: String): Double {
        return try {
            val cleanAmount = formattedAmount.replace(" FCFA", "").replace(" ", "").replace(",", ".")
            cleanAmount.toDoubleOrNull() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    // 验证金额 - Valider un montant
    fun isValidAmount(amountString: String): Boolean {
        val amount = amountString.toDoubleOrNull()
        return amount != null && amount >= 0
    }

    // 计算百分比 - Calculer un pourcentage
    fun calculatePercentage(part: Double, total: Double): Double {
        return if (total > 0) {
            (part / total) * 100
        } else {
            0.0
        }
    }

    // 格式化百分比 - Formater un pourcentage
    fun formatPercentage(percentage: Double): String {
        return "${DecimalFormat("#.##").format(percentage)}%"
    }
}