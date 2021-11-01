package com.zaka.domain

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("refreshExpirationDate")
	val refreshExpirationDate: String? = null,

	@field:SerializedName("tokenExpirationDate")
	val tokenExpirationDate: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
