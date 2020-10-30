package com.peter_majorosy.lifecure_allergycalendar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.data.FoodModel
import kotlinx.android.synthetic.main.rv_foodrow.view.*

class FoodAdapter(var context: Context) : ListAdapter<FoodModel, FoodAdapter.ViewHolder>(FoodDiffCallback()) {

    //A layouton milyen elemek található (id-k kivétele)
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvItem: TextView = itemView.tv_rvfood
        //Deletebutton
    }

    //Egy layoutelem (sor) felfújása, becsomagoljuk a view-t egy ViewHolder objektumba
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_foodrow, parent, false)
        return ViewHolder(view)
    }

    //Értékadás, mi jelenjen meg egy soron (sorok száma függvényében kerül meghívásra)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = getItem(position)

        holder.adapterPosition
        holder.tvItem.text = food.foodName

        //Gombokra állítható eseménykezelő

    }

    class FoodDiffCallback : DiffUtil.ItemCallback<FoodModel>(){
        override fun areItemsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
            return oldItem == newItem
        }
    }
}