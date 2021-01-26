package com.example.androiderestaurant

import android.content.Intent
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiderestaurant.databinding.RecyclerViewBinding

class FoodAdapter(val items: Array<Food>, private val categoriesClickListener: (String) -> Unit) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.desc.text = items[position].desc
        holder.itemView.setOnClickListener {
            categoriesClickListener.invoke(items[position].toString())
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: RecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.listTitle
        val desc = binding.listPrice

    }
}


