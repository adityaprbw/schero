package com.capstone.schero.data.api

import com.capstone.schero.data.response.ResponseScholarshipItem
import com.capstone.schero.data.response.ResponseScholarshipPredict
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/predict")
    fun getRecommendation(
        @Query("benua") benua: String,
        @Query("jenjang_pendidikan") jenjang: String,
        @Query("pendanaan") pendanaan: String
    ): Call <ResponseScholarshipPredict>

    @GET("/scholarship_details")
    fun searchScholarship(
        @Query("scholarship_name") namaBeasiswa: String
    ): Call <List<ResponseScholarshipItem>>

}