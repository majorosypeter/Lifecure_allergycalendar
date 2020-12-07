package com.peter_majorosy.lifecure_allergycalendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peter_majorosy.lifecure_allergycalendar.Firebase.ImageModel
import com.peter_majorosy.lifecure_allergycalendar.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_gallery_row.view.*

class RvGalleryAdapter : RecyclerView.Adapter<RvGalleryAdapter.ViewHolder> {
    private val list = mutableListOf<ImageModel>()
    private val context: Context

    constructor(context: Context, list: List<ImageModel>) : super() {
        this.context = context
        this.list.addAll(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvdate: TextView = itemView.tv_date
        var image: ImageView = itemView.image_picture
        var btndelete: ImageView = itemView.btn_delete
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_gallery_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvdate.text = list[position].date
        Picasso.get().load(list[position].url).fit().centerCrop().into(holder.image)

        //Elem törlése: Storage-ról, hivatkozás Firestore-ról,
        //a listából, adapter értesítése a változásokról
        holder.btndelete.setOnClickListener {
            val documentref = FirebaseFirestore.getInstance().collection("User").document(
                FirebaseAuth.getInstance().currentUser!!.uid)
            FirebaseStorage.getInstance().getReferenceFromUrl(list[position].url).delete()
            documentref.update("imageReferences", FieldValue.arrayRemove(list[position].url))
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //Új listaelem hozzáadása
    fun addItem(item: ImageModel) {
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }
}