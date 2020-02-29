package com.example.developertest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.developertest.R
import com.example.developertest.strings.PICTURE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_full_screen_image)

        val imageUrl = intent.getStringExtra(PICTURE_URL) ?: ""

        val imageView = full_screen_image_view

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_photo_library)
            .resize(500, 800)
            .centerCrop()
            .into(imageView)

        imageView.setOnClickListener {
            finish()
        }
    }
}
