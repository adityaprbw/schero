package com.capstone.schero.data.response

import com.google.gson.annotations.SerializedName

data class ResponseScholarshipPredict(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("data")
	val data: List<String>
)
