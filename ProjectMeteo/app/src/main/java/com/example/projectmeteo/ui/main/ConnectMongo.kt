package com.example.projectmeteo.ui.main

import android.util.Log
import org.bson.Document
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.regex

class ConnectMongo {
    val client = KMongo.createClient("mongodb://84.201.142.205:27017").coroutine
    val database = client.getDatabase("meteodata")
    val col = database.getCollection<TimeSeries>("timeseries")

    suspend fun getLastData(): TimeSeries {
        val data = col.find().sort(Document("_id", -1)).limit(1).toList()[0]
        Log.d("TAG", data.toString())
        return data
    }

    suspend fun getParticularRecord(dateTime: String): List<TimeSeries> {
        val data = col.find(TimeSeries::time regex "$dateTime:*").toList()
        Log.d("TAG", data.toString())
        return data
    }
}