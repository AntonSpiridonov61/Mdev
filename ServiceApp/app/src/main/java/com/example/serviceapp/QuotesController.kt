package com.example.serviceapp

import retrofit2.Call
import retrofit2.http.GET

interface QuotesController {
    @GET("/list")
    fun list(): Call<String>
}