package com.zaka.data.model

import com.google.gson.annotations.SerializedName

data class AddDeviceIDParams(

	@field:SerializedName("deviceId")
	val deviceId: String? = null,
)
