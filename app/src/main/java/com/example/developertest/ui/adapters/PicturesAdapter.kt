package com.example.developertest.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.developertest.R
import com.example.developertest.models.Picture
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_image.view.*

class PicturesAdapter (val context: Context,
                       private val pictureList: List<Picture>,
                       private val onPictureListener: OnPictureListener
) : RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = pictureList[position]

        holder?.let {
            it.bindView(picture)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_image, parent, false)
        return ViewHolder(view, onPictureListener)
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    inner class ViewHolder(itemView: View, private val onPictureListener: OnPictureListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(picture: Picture){
            Picasso.get()
                .load(picture.thumbnailUrl)
                .placeholder(R.drawable.ic_photo_library)
                .into(itemView.image)
        }

        override fun onClick(v: View?) {
            onPictureListener.onPictureClick(pictureList[adapterPosition])
        }
    }

    interface OnPictureListener {
        fun onPictureClick(picture: Picture)
    }
}