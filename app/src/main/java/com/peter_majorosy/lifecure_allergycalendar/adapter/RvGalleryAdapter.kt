package com.peter_majorosy.lifecure_allergycalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter_majorosy.lifecure_allergycalendar.Firebase.ImageModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_gallery_row.view.*

class RvGalleryAdapter :
    ListAdapter<ImageModel, RvGalleryAdapter.ViewHolder>(ImageDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvdate: TextView = itemView.tv_date
        var image: ImageView = itemView.image_picture
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_gallery_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.tvdate.text = data.date
        Picasso.get().load(data.url).into(holder.image)
    }


    class ImageDiffCallback : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.url == newItem.url
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem == newItem
        }
    }
}