package com.zaka.data.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenParams(

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
