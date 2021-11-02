package com.zaka.data.sources.remote.api

import com.zaka.data.model.AddDeviceIDParams
import com.zaka.domain.APIResponse
import com.zaka.domain.User
import retrofit2.http.*

interface SettingsAPI {
    @POST("userdevices/add")
    suspend  fun addDeviceId(@Body deviceIdParams: AddDeviceIDParams): APIResponse<String>
}