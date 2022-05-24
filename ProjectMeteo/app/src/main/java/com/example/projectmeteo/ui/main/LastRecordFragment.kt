package com.example.projectmeteo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectmeteo.R
import com.example.projectmeteo.databinding.FragmentLastRecordBinding
import kotlinx.coroutines.runBlocking

class LastRecordFragment : Fragment() {
    lateinit var binding: FragmentLastRecordBinding

    val mongo = ConnectMongo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLastRecordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecord()
        binding.update.setOnClickListener {
            setRecord()
        }
    }

    fun setRecord() {
        runBlocking {
            val record = mongo.getLastData()
            binding.apply {
                time.text = record.time
                temperature.text = "°C\n${record.temperature}"
                moisture.text = "%\n${record.moisture}"
                pressure.text = "mm ${record.pressure}"
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LastRecordFragment()
    }
}