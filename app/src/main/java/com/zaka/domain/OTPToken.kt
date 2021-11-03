package com.zaka.domain

import com.google.gson.annotations.SerializedName

data class OTPToken(

//	@field:SerializedName("otp")
//	val otp: String? = null,

	@field:SerializedName("validTo")
	val validTo: String? = null,

	@field:SerializedName("mfaToken")
	val token: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
