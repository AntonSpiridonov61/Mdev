package org.altbeacon.beaconreference

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.altbeacon.beacon.Beacon
import org.altbeacon.beaconreference.databinding.ItemBinding
import kotlin.math.roundToInt

class MyAdapter(private var allBeacon: ArrayList<Beacon>): RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var idx : ArrayList<String> = ArrayList()

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBinding.bind(itemView)

        fun bind(beacon: Beacon) = with(binding) {
            binding.beacon.text = "id: ${beacon.id3}\ndist: ${(beacon.distance * 100.0).roundToInt() / 100.0}m"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(allBeacon[position])
    }

    override fun getItemCount(): Int {
        return allBeacon.size
    }

    fun addBeacon(beacon: Beacon) {
        val id = beacon.id3.toString()
        if (!idx.contains(id)) {
            idx.add(id)
            allBeacon.add(idx.indexOf(id), beacon)
        } else {
            allBeacon.remove(beacon)
            allBeacon.add(idx.indexOf(id), beacon)
        }

        notifyDataSetChanged()
    }
}