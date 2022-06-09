package com.example.stayfit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stayfit.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(var items:ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder = ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model:ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() -> {
                holder.binding.tvItem.background = ContextCompat.getDrawable(holder.tvItem.context,R.drawable.item_circular_color_accent_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted() ->{
                holder.binding.tvItem.background = ContextCompat.getDrawable(holder.tvItem.context,R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            else -> {
            holder.binding.tvItem.background = ContextCompat.getDrawable(holder.tvItem.context,R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}