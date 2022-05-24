package com.example.projectmeteo.ui.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmeteo.MainActivity
import com.example.projectmeteo.R
import com.example.projectmeteo.databinding.FragmentLastRecordBinding
import com.example.projectmeteo.databinding.FragmentParticularRecordBinding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class ParticularRecordFragment :
    Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener
{
    lateinit var binding: FragmentParticularRecordBinding
    val adapter = RecyclerViewAdapter()

    val mongo = ConnectMongo()
    val calendar: Calendar = Calendar.getInstance()
    var day = 0
    var month= 0
    var year = 0
    var hour = 0
    var minute = 0
    var myDay = 0
    var myMonth = 0
    var myYear = 0
    var myHour = 0
    var myMinute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticularRecordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            RView.layoutManager = LinearLayoutManager(requireActivity())
            RView.adapter = adapter
        }

        binding.getDateTime.setOnClickListener {
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this, year, month,day)
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        myDay = day
        myYear = year
        myMonth = month
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(requireActivity(), this, hour, minute, true)
        timePickerDialog.show()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        val myFormat = "dd/MM/yyyy-HH:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val dateTime = sdf.format(Date(myYear - 1900, myMonth, myDay, myHour, myMinute))
        Log.d("TAG", dateTime)

        runBlocking {
            adapter.addRecord(mongo.getParticularRecord(dateTime))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ParticularRecordFragment()
    }
}