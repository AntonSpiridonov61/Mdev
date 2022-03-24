package com.example.wearher

import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

data class DataWeather(
    val coord: Location,
    val weather: ArrayList<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class Location(val lon: Double, val lat: Double)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)

data class Main(val temp: Float, val feels_like: Float, val temp_min: Float, val temp_max: Float, val pressure: Int, val humidity: Int)

data class Wind(val speed: Float, val deg: Int, val gust: Float)

data class Clouds(val all: Int)

data class Sys(val type: Int, val id: Int, val country: String, val sunrise: Long, val sunset: Long)


//    {"coord":{"lon":104.2964,"lat":52.2978},
//     "weather":[{"id":620,"main":"Snow","description":"light shower snow","icon":"13n"}],
//     "base":"stations",
//     "main":{"temp":-16.57,"feels_like":-23.57,"temp_min":-16.79,"temp_max":-16.57,"pressure":1020,"humidity":71},
//     "visibility":4000,
//     "wind":{"speed":3.13,"deg":190,"gust":0},
//     "clouds":{"all":40},
//     "dt":1645021507,
//     "sys":{"type":1,"id":8891,"country":"RU","sunrise":1644970659,"sunset":1645006587},
//     "timezone":28800,
//     "id":2023469,
//     "name":"Irkutsk",
//     "cod":200}
