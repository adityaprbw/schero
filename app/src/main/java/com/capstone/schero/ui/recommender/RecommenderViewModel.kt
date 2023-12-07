package com.capstone.schero.ui.recommender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecommenderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Recommender Fragment"
    }
    val text: LiveData<String> = _text
}