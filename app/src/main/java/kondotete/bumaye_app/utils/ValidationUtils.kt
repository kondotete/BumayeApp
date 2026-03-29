package kondotete.bumaye_app.utils

import java.text.SimpleDateFormat
import java.util.*

object ValidationUtils {
    
    // Validation du nom et prénoms (champ unique dans l'interface)
    fun _v_validateNomPrenoms(nomPrenoms: String): String? {
        return if (nomPrenoms.isBlank()) {
            "Le nom et prénoms sont obligatoires"
        } else if (nomPrenoms.trim().length < 2) {
            "Le nom et prénoms doivent contenir au moins 2 caractères"
        } else null
    }
    
    fun _v_validateTelephone(telephone: String): String? {
        return when {
            telephone.isBlank() -> "Le numéro de téléphone est obligatoire"
            !telephone.matches(Regex("^[0-9]{8,12}$")) -> "Le numéro doit contenir 8 à 12 chiffres"
            else -> null
        }
    }
    
    // Validation du format de date
    fun _v_validateDateFormat(date: String): String? {
        return if (date.isBlank()) {
            "La date est obligatoire"
        } else if (!date.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$"))) {
            "Format de date invalide (dd/MM/yyyy)"
        } else null
    }
    
    //(Logique de validation des dates)
    fun _v_validateDates(dateCommande: String, dateLivraison: String): String? {
        // D'abord valider le format des dates
        val commandeError = _v_validateDateFormat(dateCommande)
        if (commandeError != null) return commandeError
        
        val livraisonError = _v_validateDateFormat(dateLivraison)
        if (livraisonError != null) return livraisonError
        
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val commandeDate = format.parse(dateCommande)
            val livraisonDate = format.parse(dateLivraison)
            
            if (commandeDate != null && livraisonDate != null) {
                if (livraisonDate.before(commandeDate)) {
                    "La date de livraison doit être ultérieure à la date de commande"
                } else null
            } else {
                "Format de date invalide (dd/MM/yyyy)"
            }
        } catch (e: Exception) {
            "Format de date invalide (dd/MM/yyyy)"
        }
    }
    
    fun _v_validateMontant(montant: String, fieldName: String): String? {
        return when {
            montant.isBlank() -> "$fieldName est obligatoire"
            else -> {
                try {
                    val value = montant.toDouble()
                    if (value < 0) {
                        "$fieldName ne peut pas être négatif"
                    } else null
                } catch (e: NumberFormatException) {
                    "$fieldName doit être un nombre valide"
                }
            }
        }
    }
    
    // Méthodes utilitaires pour compatibilité avec l'ancien code
    fun isValidPhoneNumber(phone: String): Boolean {
        return _v_validateTelephone(phone) == null
    }

    fun isValidName(name: String): Boolean {
        return _v_validateNomPrenoms(name) == null
    }

    fun isValidAmount(amount: String): Boolean {
        return _v_validateMontant(amount, "Le montant") == null
    }

    fun isValidDateFormat(date: String): Boolean {
        return _v_validateDateFormat(date) == null
    }

    fun isDateAfterOrEqual(dateToCheck: String, referenceDate: String): Boolean {
        return _v_validateDates(referenceDate, dateToCheck) == null
    }
}
