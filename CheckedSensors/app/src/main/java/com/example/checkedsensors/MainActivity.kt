package com.example.checkedsensors

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkedsensors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var sm: SensorManager
    val adapter = RecyclerViewAdapter()

    val environmentType = listOf(2, 5, 6, 12, 13, 14)
    val humanConditionType = listOf(21, 31, 34)

    val environmentName = mutableListOf<String>()
    val humanConditionName = mutableListOf<String>()
    val physicalPosName = mutableListOf<String>()

    val sensors = mutableMapOf(
        0 to physicalPosName,
        1 to environmentName,
        2 to humanConditionName
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.nameItemSpinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener = this
        }
        binding.apply {
            RView.layoutManager = LinearLayoutManager(this@MainActivity)
            RView.adapter = adapter
        }

        sm = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList = sm.getSensorList(Sensor.TYPE_ALL)
        sensorList.forEach {
            when (it.type) {
                in environmentType -> environmentName.add(it.name)
                in humanConditionType -> humanConditionName.add(it.name)
                else -> physicalPosName.add(it.name)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        sensors[pos]?.let { adapter.addRecord(it) }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}