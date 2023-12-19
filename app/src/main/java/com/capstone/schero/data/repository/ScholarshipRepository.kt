package com.capstone.schero.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.schero.data.api.ApiConfig
import com.capstone.schero.data.response.ResponseScholarshipItem
import com.capstone.schero.data.response.ResponseScholarshipPredict
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScholarshipRepository {

    private val _getRecommendation = MutableLiveData<List<String>>()
    val getRecommendation: LiveData<List<String>> get() = _getRecommendation

    private val _search = MutableLiveData<List<ResponseScholarshipItem>>()
    val search: LiveData<List<ResponseScholarshipItem>> get() = _search

    fun getRecommendation(benua: String, jenjang: String, biaya: String) {
        val call = ApiConfig.getApiService().getRecommendation(benua, jenjang, biaya)
        call.enqueue(object : Callback<ResponseScholarshipPredict> {
            override fun onResponse(
                call: Call<ResponseScholarshipPredict>,
                response: Response<ResponseScholarshipPredict>
            ) {
                if (response.isSuccessful) {
                    _getRecommendation.value = response.body()?.recommendations
                } else {
                    Log.e("Repository", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseScholarshipPredict>, t: Throwable) {
                Log.e("Repository", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchScholarship(namaBeasiswa: String) {
        val call = ApiConfig.getApiService().searchScholarship(namaBeasiswa)
        call.enqueue(object : Callback<List<ResponseScholarshipItem>> {
            override fun onResponse(
                call: Call<List<ResponseScholarshipItem>>,
                response: Response<List<ResponseScholarshipItem>>
            ) {
                if (response.isSuccessful) {
                    _search.value = response.body()
                } else {
                    Log.e("Repository", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ResponseScholarshipItem>>, t: Throwable) {
                Log.e("Repository", "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        @Volatile
        private var instance: ScholarshipRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ScholarshipRepository()
            }.also { instance = it }
    }
}
