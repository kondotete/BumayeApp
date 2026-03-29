package kondotete.bumaye_app.utils

object Constants {

    // Constantes de base de données et de stockage
    const val SHARED_PREFS_NAME = "BumayeAppPrefs"
    const val KEY_FIRST_LAUNCH = "first_launch"
    const val KEY_APP_VERSION = "app_version"
    const val KEY_LAST_BACKUP = "last_backup"

    // Intent extras
    const val EXTRA_CLIENT_ID = "client_id"
    const val EXTRA_CLIENT_DATA = "CLIENT_DATA"
    const val EXTRA_IS_EDITING = "is_editing"

    // Request codes
    const val REQUEST_CAMERA = 100
    const val REQUEST_GALLERY = 101
    const val REQUEST_PERMISSIONS = 102

    // Configuration des photos
    const val MAX_PHOTO_SIZE = 1024 // pixels
    const val PHOTO_QUALITY = 90 // JPEG quality
    const val PHOTOS_DIRECTORY = "ClientPhotos"

    // Constantes de validation
    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 100
    const val MIN_PHONE_LENGTH = 8
    const val MAX_PHONE_LENGTH = 12
    const val MIN_AMOUNT = 0.0
    const val MAX_AMOUNT = 999999999.0

    // Formats de date
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm"
    const val FILE_DATE_FORMAT = "yyyyMMdd_HHmmss"

    // Valeurs par défaut
    const val DEFAULT_CURRENCY = "FCFA"
    const val DEFAULT_LOCALE = "fr_FR"

    //  - Messages d'erreur
    const val ERROR_REQUIRED_FIELD = "Champs requis"
    const val ERROR_INVALID_PHONE = "Numéro invalide"
    const val ERROR_INVALID_AMOUNT = "Montant invalide"
    const val ERROR_INVALID_DATE = "Date invalide"
    const val ERROR_DATE_LOGIC = "Quel date!! elle n'est pas logique"
    const val ERROR_PHOTO_SAVE = "Photo non sauvergardée"
    const val ERROR_CAMERA_UNAVAILABLE = "Caméra non disponible"

    // Messages de succès
    const val SUCCESS_CLIENT_ADDED = "Client ajouté avec succès"
    const val SUCCESS_CLIENT_UPDATED = "Client modifié"
    const val SUCCESS_CLIENT_DELETED = "Client suprimé"
    const val SUCCESS_PHOTO_SAVED = "Photo enregistré"
}