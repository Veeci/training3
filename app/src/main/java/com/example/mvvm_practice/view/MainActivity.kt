package com.example.mvvm_practice.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.mvvm_practice.R
import com.example.mvvm_practice.databinding.ActivityMainBinding
import com.example.mvvm_practice.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {
    private val model: CityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume()
    {
        super.onResume()

        model.getCityData().observe(this, Observer{city ->
            binding.cityImage.setImageResource(city.image)
            binding.cityNameTV.text = city.name
            binding.cityPopulationTV.text = city.population.toString()
        })
    }
}