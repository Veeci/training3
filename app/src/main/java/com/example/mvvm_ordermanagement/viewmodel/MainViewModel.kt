package com.example.mvvm_ordermanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.mvvm_ordermanagement.model.LoadingState
import com.example.mvvm_ordermanagement.model.Order
import com.example.mvvm_ordermanagement.model.OrderDataGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel()
{
    val ordersLiveData = MediatorLiveData<List<Order>>()
    private val _queryLiveData = MutableLiveData<String>()
    private val _allOrdersData = MutableLiveData<List<Order>>()
    private var _searchOrdersLiveData: LiveData<List<Order>>
    val loadingStateLiveData = MutableLiveData<LoadingState>()

    private var searchJob: Job? = null
    private val debouncePeriod = 1000L

    init{
        _searchOrdersLiveData = _queryLiveData.switchMap {
            fetchOrdersByQuery(it)
        }

        ordersLiveData.addSource(_allOrdersData){
            ordersLiveData.value = it
        }

        ordersLiveData.addSource(_searchOrdersLiveData){
            ordersLiveData.value = it
        }
    }

    fun onViewReady()
    {
        if(_allOrdersData.value.isNullOrEmpty())
        {
            fetchAllOrders()
        }

    }

    private fun fetchAllOrders()
    {
        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO){
            try {
                val orders = OrderDataGenerator.getAllOrders()
                _allOrdersData.postValue(orders)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            }catch (exception: Exception)
            {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }
    }

    fun onSearchQuery(query: String)
    {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debouncePeriod)
            if (query.isEmpty()) {
                fetchAllOrders()
            }
            else
            {
                _queryLiveData.postValue(query)
            }
        }
    }

    private fun fetchOrdersByQuery(query: String): LiveData<List<Order>>
    {
        val liveData = MutableLiveData<List<Order>>()
        loadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO){
            try {
                val orders = OrderDataGenerator.searchOrders(query)
                liveData.postValue(orders)
                loadingStateLiveData.postValue(LoadingState.LOADED)
            }
            catch (exception: Exception)
            {
                loadingStateLiveData.postValue(LoadingState.ERROR)
            }
        }

        return liveData
    }
}