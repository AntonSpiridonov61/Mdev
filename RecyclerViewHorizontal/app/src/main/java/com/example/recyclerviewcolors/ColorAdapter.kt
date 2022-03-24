package com.example.recyclerviewcolors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewcolors.databinding.ItemBinding

class ColorAdapter: RecyclerView.Adapter<ColorAdapter.ColorHolder>() {

    var colorList = ArrayList<Int>()

    class ColorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)

        fun bind(color: Int) = with(binding) {
            colorBox.setBackgroundColor(color)
            colorCode.text = itemView.context.getString(R.string.template, color)
        }

        init {
            binding.apply {
                itemCard.setOnClickListener {
                    val toast = Toast.makeText(itemView.context, colorCode.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ColorHolder(view)
    }

    override fun onBindViewHolder(holder: ColorHolder, position: Int) {
        holder.bind(colorList[position])
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    fun addColor(color: Int) {
        colorList.add(color)
        notifyDataSetChanged()
    }
}