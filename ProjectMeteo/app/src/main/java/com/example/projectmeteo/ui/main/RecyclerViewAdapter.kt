package com.example.projectmeteo.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmeteo.R
import com.example.projectmeteo.databinding.ItemBinding

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    var records = ArrayList<TimeSeries>()

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(record: TimeSeries) = with(binding) {
            resultStr.text = "${record.time}" +
                    "\ntemperature: ${record.temperature}\nmoisture: ${record.moisture}\npressure: ${record.pressure}"
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

    fun addRecord(record: List<TimeSeries>) {
        records = ArrayList()
        for (item in record) {
            records.add(item)
//            Log.d("TAG", item.toString())
        }
        notifyDataSetChanged()
    }
}