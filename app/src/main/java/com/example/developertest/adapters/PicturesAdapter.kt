package com.example.developertest.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.developertest.R
import com.example.developertest.activities.FullScreenImageActivity
import com.example.developertest.models.Picture
import com.example.developertest.strings.PICTURE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_image.view.*

class PicturesAdapter (val context: Context,
                       private val pictureList: List<Picture>) : RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictureList[position]

        holder?.let {
            it.bindView(picture)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullScreenImageActivity::class.java)
            intent.putExtra(PICTURE_URL, picture.url)
            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(picture: Picture){

            Picasso.get()
                .load(picture.thumbnailUrl)
                .placeholder(R.drawable.ic_photo_library)
                .into(itemView.image)

        }
    }
}