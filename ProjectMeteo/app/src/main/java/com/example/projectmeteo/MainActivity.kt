package com.example.projectmeteo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmeteo.databinding.ActivityMainBinding
import com.example.projectmeteo.ui.main.*
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentList = listOf(
        LastRecordFragment.newInstance(),
        ParticularRecordFragment.newInstance()
    )

    private val fragmentListTitle = listOf(
        "Last record",
        "Particular record"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SectionsPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {
            tab, pos -> tab.text = fragmentListTitle[pos]
        }.attach()
    }
}