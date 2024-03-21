package com.interview.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.interview.weatherapp.databinding.FragmentWeatherListBinding
import com.interview.weatherapp.view.CityAdapter
import com.interview.weatherapp.viewmodel.DisplayViewModel

class CityListFragment: Fragment() {

    private lateinit var binding: FragmentWeatherListBinding
    private val displayViewModel: DisplayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = CityAdapter(displayViewModel::updateDisplay)
        return binding.root
    }
}