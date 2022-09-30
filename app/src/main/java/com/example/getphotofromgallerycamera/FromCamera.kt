package com.example.getphotofromgallerycamera

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.getphotofromgallerycamera.databinding.FromCameraBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FromCamera : AppCompatActivity() {
    lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FromCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var imageFile = createImageFile()
        photoUri = FileProvider.getUriForFile(this , BuildConfig.APPLICATION_ID , imageFile)

        var getImageContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
            if (it) {
                binding.imageFilterView.setImageURI(photoUri)
                val ins = contentResolver?.openInputStream(photoUri)
                var file = File(filesDir, "image.jpg")
                var fileOutputStream = FileOutputStream(file)
                ins?.copyTo(fileOutputStream)
                ins?.close()
                fileOutputStream.close()
            }
        }


        binding.takeBtn.setOnClickListener {
            getImageContent.launch(photoUri)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile() : File {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss" , Locale.US).format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}",
            ".jpg",
            storageDir
        )
    }
}
