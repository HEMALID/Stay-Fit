package com.example.stayfit.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stayfit.R
import com.example.stayfit.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root) {
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date:String =items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date


        if (position % 2 == 0){
            holder.llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.colorAccent))
        }else{
            holder.llHistoryItemMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}