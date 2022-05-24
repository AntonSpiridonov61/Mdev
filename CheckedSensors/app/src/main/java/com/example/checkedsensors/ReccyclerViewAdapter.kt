package com.example.checkedsensors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.checkedsensors.databinding.ItemBinding

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    var records = ArrayList<String>()

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(record: String) = with(binding) {
            resultStr.text = record
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun addRecord(record: List<String>) {
        records = ArrayList()
        for (item in record) {
            records.add(item)
        }
        notifyDataSetChanged()
    }
}