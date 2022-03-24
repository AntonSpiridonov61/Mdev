package com.example.wearher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.wearher.databinding.FragmentFullWeatherBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FullWeatherFragment : Fragment() {
    lateinit var binding: FragmentFullWeatherBinding
    private val dataModel: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullWeatherBinding.inflate(inflater)
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

                wind.text = "${data.wind.speed} m/s"
                humdity.text = "${data.main.humidity}%"
                visibility.text = "${data.visibility / 1000} km"
                minTemp.text = "${data.main.temp_min}°C"
                maxTemp.text = "${data.main.temp_max}°C"
            }

            val imageURL = "http://openweathermap.org/img/wn/${data.weather[0].icon}@2x.png"
            Log.d("url", imageURL)
            Glide.with(this)
                .load(imageURL)
                .into(binding.iconWeather)

            val deg = data.wind.deg
            var idImg = 0
            if (135 > deg && deg >= 45) {
                idImg = R.drawable.east
            } else if (225 > deg && deg >= 135) {
                idImg = R.drawable.south
            } else if (315 > deg && deg >= 225) {
                idImg = R.drawable.west
            } else {
                idImg = R.drawable.north
            }
            GlobalScope.launch(Dispatchers.Main) {
                binding.iconWind.setImageResource(idImg)
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FullWeatherFragment()
    }
}