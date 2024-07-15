package com.example.mvvm_savedstatehandle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SaveStateViewModel(private val state: SavedStateHandle): ViewModel()
{
    var counterLiveData = state.getLiveData<Int>("counter")

    fun increment()
    {
        counterLiveData.value = (counterLiveData.value?:0) + 1
    }
}

//This way cannot save user's state if the process is killed or destroyed

//class SaveStateViewModel: ViewModel()
//{
//    var counterLiveData = MutableLiveData<Int>()
//
//    fun increment()
//    {
//        counterLiveData.value = (counterLiveData.value?:0) + 1
//    }
//}