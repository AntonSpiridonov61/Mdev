package com.example.wearher

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.wearher.databinding.FragmentBriefWeatherBinding
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class BriefWeather : Fragment() {
    lateinit var binding: FragmentBriefWeatherBinding
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBriefWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lateinit var data: DataWeather
        dataModel.data.observe(activity as LifecycleOwner) {
            data = it

            binding.apply {
                nameCity.text = "${data.name}, ${data.sys.country}"
                temperature.text = "${data.main.temp}°C"
                feelsLike.text = "${data.main.feels_like}°C"
                mainWeather.text = data.weather[0].description
            }

            val imageURL = "http://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png"
            Log.d("url", imageURL)
            Glide.with(this)
                .load(imageURL)
                .into(binding.iconWeather)
        }
    }

//    fun setImage(url: URL) {
//        var imageWeather : Bitmap? = null
//        val imgStream = URL(url).openStream() as InputStream
//        imageWeather = BitmapFactory.decodeStream(imgStream)
//
//        if (imageWeather !== null) {
//            GlobalScope.launch (Dispatchers.Main) {
//                binding.iconWeather.setImageBitmap(imageWeather)
//            }
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance() = BriefWeather()
    }
}