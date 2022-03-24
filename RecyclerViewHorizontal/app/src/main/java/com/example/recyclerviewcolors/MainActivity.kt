package com.example.recyclerviewcolors

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewcolors.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val rnd = Random.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val adapter = ColorAdapter()
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rcView.adapter = adapter
            buttonAdd.setOnClickListener {
                val color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                adapter.addColor(color)
            }
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rcView)
    }
}