package com.example.tabbedapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tabbedapp.R
import com.example.tabbedapp.databinding.FragmentMainBinding


class PlaceholderFragment : Fragment() {

    private lateinit var model: PageViewModel
    private lateinit var binding: FragmentMainBinding
    private val imageIdCars = arrayOf(
        R.drawable.v2101,
        R.drawable.v2104,
        R.drawable.v2106,
        R.drawable.v2108,
        R.drawable.v2114
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val infoCar = resources.getStringArray(R.array.info_car)
        val idx = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
        model = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setCar(CarModel(imageIdCars[idx], infoCar[idx]))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        model.car.observe(viewLifecycleOwner) {
            binding.apply {
                imageView.setImageResource(it.imageId)
                info.text = it.info
            }
        }
        return binding.root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}