package com.example.getphotofromgallerycamera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.getphotofromgallerycamera.databinding.FromGalleryBinding
import java.io.File
import java.io.FileOutputStream

class FromGallery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = FromGalleryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){
            it ?: return@registerForActivityResult
            binding.imageFilterView.setImageURI(it)
            val ins = contentResolver?.openInputStream(it)
            var file = File(filesDir , "image.jpg")
            var fileOutputStream = FileOutputStream(file)
            ins?.copyTo(fileOutputStream)
            ins?.close()
            fileOutputStream.close()
        }


        binding.getBtn.setOnClickListener {
            getImageContent.launch("image/*")
        }
    }
}