package com.example.mvvm_ordermanagement.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_ordermanagement.R
import com.example.mvvm_ordermanagement.databinding.ActivityMainBinding
import com.example.mvvm_ordermanagement.model.LoadingState
import com.example.mvvm_ordermanagement.model.Order
import com.example.mvvm_ordermanagement.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = OrderAdapter(arrayListOf())

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

    override fun onResume() {
        super.onResume()

        initializeUI()
        initializeObservers()

        viewModel.onViewReady()
    }

    private fun initializeUI()
    {
        binding.ordersRV.adapter = adapter
        binding.ordersRV.layoutManager = LinearLayoutManager(this)

        binding.searchET.doOnTextChanged{text, start, before, count ->
            viewModel.onSearchQuery(text.toString())
        }
    }

    private fun initializeObservers()
    {
        viewModel.loadingStateLiveData.observe(this, Observer {
            onLoadingStateChanged(it)
        })

        viewModel.ordersLiveData.observe(this, Observer {
            adapter.updateOrders(it as ArrayList<Order>)
        })
    }

    private fun onLoadingStateChanged(state: LoadingState)
    {
        binding.searchET.visibility = if(state == LoadingState.LOADING) View.GONE else View.VISIBLE
        binding.ordersRV.visibility = if(state == LoadingState.LOADING) View.GONE else View.VISIBLE
        binding.errorTV.visibility = if(state == LoadingState.ERROR) View.VISIBLE else View.GONE
        binding.loadingPB.visibility = if(state == LoadingState.LOADING) View.VISIBLE else View.GONE
    }
}