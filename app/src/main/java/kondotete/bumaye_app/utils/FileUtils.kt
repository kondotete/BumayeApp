package kondotete.bumaye_app.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    //  Sauvegarder la photo du client
    fun saveClientPhoto(context: Context, bitmap: Bitmap, clientId: String): String? {
        return try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "CLIENT_${clientId}_$timeStamp.jpg"

            val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ClientPhotos")
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }

            val photoFile = File(storageDir, fileName)
            val fos = FileOutputStream(photoFile)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()

            photoFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    //  Supprimer la photo du client
    fun deleteClientPhoto(photoPath: String?): Boolean {
        return if (photoPath != null) {
            try {
                val file = File(photoPath)
                file.delete()
            } catch (e: Exception) {
                false
            }
        } else {
            true
        }
    }

    // Créer un fichier image temporaire
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}