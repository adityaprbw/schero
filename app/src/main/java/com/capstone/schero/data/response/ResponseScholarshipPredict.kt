package com.capstone.schero.data.response

import com.google.gson.annotations.SerializedName

data class ResponseScholarshipPredict(

	@field:SerializedName("recommendations")
	val recommendations: List<String>

)
