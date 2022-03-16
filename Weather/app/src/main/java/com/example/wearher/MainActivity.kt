package com.example.wearher

import android.app.Activity
import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*
import com.example.wearher.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener
import java.io.InputStreamReader
import androidx.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var connectivityManager: ConnectivityManager
    lateinit var dataWeather: DataWeather
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        binding.apply {
            btnGetWeather.setOnClickListener {
                GlobalScope.launch (Dispatchers.IO) {
                    loadWeather()
                }
                val fr = supportFragmentManager.findFragmentById(R.id.fragment_weather)
                if (fr == null) {
                    displayFragment(BriefWeather.newInstance())
                }
            }
        }

        binding.btnDisplayDialog.setOnClickListener {
            MyDialog(this).show(supportFragmentManager, "customDialog")
        }

        dataModel.selectType.observe(this) {
            when(it) {
                "Brief" -> displayFragment(BriefWeather.newInstance())
                "Full" -> displayFragment(FullWeatherFragment.newInstance())
            }
        }
    }

    suspend fun loadWeather() {
        Log.d("WeatherConnection", connectivityManager.isActiveNetworkMetered.toString())
        if (connectivityManager.isActiveNetworkMetered) {
            val gson = Gson()

            val API_KEY = resources.getString(R.string.keyAPI)
            val city = binding.cityInp.text.toString()
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY&units=metric";
            val stream = URL(weatherURL).getContent() as InputStream

            // JSON отдаётся одной строкой,
            val data = Scanner(stream).nextLine()
            Log.d("data", data)
            dataWeather = gson.fromJson(data, DataWeather::class.java)

            GlobalScope.launch (Dispatchers.Main) {
                dataModel.data.value = dataWeather
            }
        } else {
            var toast = Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_weather, fragment)
            .commit()
    }
}