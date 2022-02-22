package com.example.wearher

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var connectivityManager: ConnectivityManager
    lateinit var dataWeather: DataWeather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        binding.btnGetWeather.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO) {
                loadWeather()
            }
        }
    }

    suspend fun setImage(url: String) {
//        Glide.with(this)
//            .load(url)
//            .into(binding.ivDescriptions)
        var imageWeather : Bitmap? = null
        val imgStream = URL(url).openStream() as InputStream
        imageWeather = BitmapFactory.decodeStream(imgStream)

        if (imageWeather !== null) {
            GlobalScope.launch (Dispatchers.Main) {
                binding.ivDescriptions.setImageBitmap(imageWeather)
            }
        }
    }

    suspend fun loadWeather() {
        Log.d("WeatherConnection", connectivityManager.isActiveNetworkMetered.toString())
        if (true) {
            val gson = Gson()

            val API_KEY = resources.getString(R.string.keyAPI)
            val city = binding.cityInp.text.toString()
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY&units=metric";
            val stream = URL(weatherURL).getContent() as InputStream

            // JSON отдаётся одной строкой,
            val data = Scanner(stream).nextLine()
            Log.d("data", data)
            val dataWeather = gson.fromJson(data, DataWeather::class.java)
            binding.dataWeather = dataWeather


            val imageURL = "http://openweathermap.org/img/wn/${dataWeather.weather[0].icon}@2x.png"
            setImage(imageURL)

            val deg = dataWeather.wind.deg
            GlobalScope.launch (Dispatchers.Main) {
                if (135 > deg && deg >= 45) {
                    binding.ivWind.setImageResource(R.drawable.east)
                } else if (225 > deg && deg >= 135) {
                    binding.ivWind.setImageResource(R.drawable.south)
                } else if (315 > deg && deg >= 225) {
                    binding.ivWind.setImageResource(R.drawable.west)
                } else {
                    binding.ivWind.setImageResource(R.drawable.north)
                }
            }
        } else {
            var toast = Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }
    }
}