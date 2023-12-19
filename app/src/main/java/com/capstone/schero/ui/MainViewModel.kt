package com.capstone.schero.ui

import androidx.lifecycle.ViewModel
import com.capstone.schero.data.repository.ScholarshipRepository

class MainViewModel(private val repository: ScholarshipRepository) : ViewModel() {

    val setRecomendation = repository.getRecommendation

    fun getRecomendation(benua: String, jenjang: String, biaya: String) = repository.getRecommendation(benua, jenjang, biaya)

    val search = repository.search

    fun searchScholarship(namaBeasiswa: String) = repository.searchScholarship(namaBeasiswa)

}