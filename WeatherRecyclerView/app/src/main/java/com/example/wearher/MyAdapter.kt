package com.example.wearher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.wearher.databinding.ItemBinding

class MyAdapter(private var cityList: ArrayList<String>): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)

        fun bind(nameCity: String) = with(binding) {
            binding.nameCity.text = nameCity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    fun addName(nameCity: String) {
        cityList.add(nameCity)
        notifyDataSetChanged()
    }
}