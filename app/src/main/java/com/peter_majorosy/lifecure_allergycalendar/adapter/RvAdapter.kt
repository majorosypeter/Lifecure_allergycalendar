package com.peter_majorosy.lifecure_allergycalendar.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import com.peter_majorosy.lifecure_allergycalendar.data.DataModel
import kotlinx.android.synthetic.main.rv_row.view.*
import kotlin.concurrent.thread

class RvAdapter(var context: Context) :
    ListAdapter<DataModel, RvAdapter.ViewHolder>(FoodDiffCallback()) {

    //A layouton milyen elemek található (id-k kivétele)
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.tv_rvdata
        var emoticon: ImageView = itemView.emoticon

        //Deletebutton
        val btnDelete: ImageView = itemView.btn_delete
    }

    //Egy layoutelem (sor) felfújása, becsomagoljuk a view-t egy ViewHolder objektumba
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_row, parent, false)
        return ViewHolder(view)
    }

    //Értékadás, mi jelenjen meg egy soron (sorok száma függvényében kerül meghívásra)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.adapterPosition
        holder.tvItem.text = data.dataName

        if (data.isFood) {
            holder.emoticon.setImageResource(R.drawable.ic_food_emoticon)
        } else {
            holder.emoticon.setImageResource(R.drawable.ic_symptom_emoticon)
        }

        //Gombokra állítható eseménykezelő
        holder.btnDelete.setOnClickListener {
            thread {
                AppDatabase.getInstance(context).dataDAO().deleteData(data)
            }
        }

    }

    class FoodDiffCallback : DiffUtil.ItemCallback<DataModel>() {
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem.dataId == newItem.dataId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }
}