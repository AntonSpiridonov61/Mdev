package com.example.tabbedapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.tabbedapp.ui.main.SectionsPagerAdapter
import com.example.tabbedapp.databinding.ActivityMainBinding
import com.example.tabbedapp.ui.main.CarModel
import com.example.tabbedapp.ui.main.PageViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {
            tab, pos ->
            run {
                tab.text = this.resources.getStringArray(R.array.name_cities)[pos].toString()
            }
        }.attach()
    }
}