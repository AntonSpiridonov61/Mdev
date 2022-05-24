package com.example.serviceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var send: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        send = findViewById(R.id.send)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,RUB,EUR&api_key=86822e30f149c61d870a10e26d5e5db26594b1f73ab6d3dbce376ee3ffec0ee5")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: QuotesController = retrofit.create(QuotesController::class.java)

        intent = Intent(this, Quotes::class.java)
        startService(intent)

        send.setOnClickListener(View.OnClickListener {
            Thread() {
                run {
                    val call: Call<String> = service.list()
                    val userResponse: Response<String> = call.execute()
                    val result: String? = userResponse.body()
                    Log.d("RetrofitClient","Send data to server was: " + result)
                }
            }.start()
        })
    }

}