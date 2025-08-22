package kondotete.bumaye_app.extensions

import android.content.Context
import android.content.SharedPreferences
import kondotete.bumaye_app.utils.Constants.SHARED_PREFS_NAME

class SharedPrefsManager(context: Context) {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    // 保存字符串值 - Sauvegarder une valeur string
    fun saveString(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    // 获取字符串值 - Obtenir une valeur string
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPrefs.getString(key, defaultValue) ?: defaultValue
    }

    // 保存布尔值 - Sauvegarder une valeur boolean
    fun saveBoolean(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    // 获取布尔值 - Obtenir une valeur boolean
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    // 保存整数值 - Sauvegarder une valeur int
    fun saveInt(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }

    // 获取整数值 - Obtenir une valeur int
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    // 保存长整数值 - Sauvegarder une valeur long
    fun saveLong(key: String, value: Long) {
        sharedPrefs.edit().putLong(key, value).apply()
    }

    // 获取长整数值 - Obtenir une valeur long
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sharedPrefs.getLong(key, defaultValue)
    }

    // 删除特定键 - Supprimer une clé spécifique
    fun remove(key: String) {
        sharedPrefs.edit().remove(key).apply()
    }

    // 清除所有数据 - Effacer toutes les données
    fun clear() {
        sharedPrefs.edit().clear().apply()
    }

    // 检查键是否存在 - Vérifier si une clé existe
    fun contains(key: String): Boolean {
        return sharedPrefs.contains(key)
    }
}