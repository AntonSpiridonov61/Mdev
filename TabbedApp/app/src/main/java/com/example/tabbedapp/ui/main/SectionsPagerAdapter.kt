package com.example.tabbedapp.ui.main

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabbedapp.R

class SectionsPagerAdapter(private val context: Context, fa: FragmentActivity) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return context.resources.getStringArray(R.array.name_cities).size
    }

    override fun createFragment(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position)
    }
}