package com.example.wearher

data class DataWeather(
    val coord: Location,
    val weather: ArrayList<>
)
//    {"coord":{"lon":104.2964,"lat":52.2978},"weather":[{"id":620,"main":"Snow","description":"light shower snow","icon":"13n"}],"base":"stations","main":{"temp":-16.57,"feels_like":-23.57,"temp_min":-16.79,"temp_max":-16.57,"pressure":1020,"humidity":71},"visibility":4000,"wind":{"speed":3.13,"deg":190,"gust":0},"clouds":{"all":40},"dt":1645021507,"sys":{"type":1,"id":8891,"country":"RU","sunrise":1644970659,"sunset":1645006587},"timezone":28800,"id":2023469,"name":"Irkutsk","cod":200}
