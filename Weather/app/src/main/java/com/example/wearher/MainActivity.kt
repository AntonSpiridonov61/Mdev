package com.example.wearher

import android.app.Activity
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

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetWeather.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO) {
                loadWeather()
            }
        }

        /*
        TODO: реализовать отображение погоды в текстовых полях и картинках
        картинками отобразить облачность и направление ветра
        использовать data binding, библиотеки уже подключены
         */
    }
    suspend fun loadWeather() {
        val API_KEY = resources.getString(R.string.keyAPI)
        // TODO: в строку подставлять API_KEY и город (выбирается из списка или вводится в поле)
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=Irkutsk&appid=$API_KEY&units=metric";
        val stream = URL(weatherURL).getContent() as InputStream
        // JSON отдаётся одной строкой,
        val data = Scanner(stream).nextLine()
        // TODO: предусмотреть обработку ошибок (нет сети, пустой ответ)
        Log.d("mytag", data)
//        val jsonObject = JSONTokener(data).nextValue() as JSONObject
//
//        val id = jsonObject.getString("coord")
//        Log.i("ID: ", id)

        var gson = Gson()
        var testModel = gson.fromJson(InputStreamReader(stream), DataWeather::class.java)
    }

}