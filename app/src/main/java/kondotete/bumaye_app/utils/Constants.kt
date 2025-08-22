package kondotete.bumaye_app.utils

object Constants {

    // 数据库和存储常量 - Constantes de base de données et de stockage
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

    // 照片配置 - Configuration des photos
    const val MAX_PHOTO_SIZE = 1024 // pixels
    const val PHOTO_QUALITY = 90 // JPEG quality
    const val PHOTOS_DIRECTORY = "ClientPhotos"

    // 验证常量 - Constantes de validation
    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 100
    const val MIN_PHONE_LENGTH = 8
    const val MAX_PHONE_LENGTH = 12
    const val MIN_AMOUNT = 0.0
    const val MAX_AMOUNT = 999999999.0

    // 日期格式 - Formats de date
    const val DATE_FORMAT = "dd/MM/yyyy"
    const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm"
    const val FILE_DATE_FORMAT = "yyyyMMdd_HHmmss"

    // 默认值 - Valeurs par défaut
    const val DEFAULT_CURRENCY = "FCFA"
    const val DEFAULT_LOCALE = "fr_FR"

    // 错误消息 - Messages d'erreur
    const val ERROR_REQUIRED_FIELD = "此字段为必填项"
    const val ERROR_INVALID_PHONE = "电话号码格式不正确"
    const val ERROR_INVALID_AMOUNT = "金额格式不正确"
    const val ERROR_INVALID_DATE = "日期格式不正确"
    const val ERROR_DATE_LOGIC = "交货日期必须晚于或等于订单日期"
    const val ERROR_PHOTO_SAVE = "保存照片失败"
    const val ERROR_CAMERA_UNAVAILABLE = "相机不可用"

    // 成功消息 - Messages de succès
    const val SUCCESS_CLIENT_ADDED = "客户添加成功"
    const val SUCCESS_CLIENT_UPDATED = "客户信息更新成功"
    const val SUCCESS_CLIENT_DELETED = "客户删除成功"
    const val SUCCESS_PHOTO_SAVED = "照片保存成功"
}