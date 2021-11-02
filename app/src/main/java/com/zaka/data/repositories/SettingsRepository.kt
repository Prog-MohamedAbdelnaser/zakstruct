package com.zaka.data.repositories

import com.zaka.data.model.AddDeviceIDParams

import com.zaka.data.sources.remote.api.SettingsAPI
import com.zaka.domain.APIResponse
import kotlinx.coroutines.flow.*


class SettingsRepository(private val  addDeviceIdAPI: SettingsAPI) {

     suspend fun addDeviceId(deviceIdParams: AddDeviceIDParams): Flow<APIResponse<String>> {
       return  flow {
           emit(addDeviceIdAPI.addDeviceId(deviceIdParams))
         }
    }
}