package com.zaka.data.model

import com.google.gson.annotations.SerializedName

data class LoginParams(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
