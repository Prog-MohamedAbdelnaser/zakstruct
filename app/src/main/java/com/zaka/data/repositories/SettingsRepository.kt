package com.zaka.data.repositories

import com.zaka.data.model.AddDeviceIDParams

import com.zaka.data.sources.remote.api.SettingsAPI
import com.zaka.domain.APIResponse
import com.zaka.domain.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext


class SettingsRepository(private val  addDeviceIdAPI: SettingsAPI , private val localeRepository: LocaleRepository) {

     suspend fun addDeviceId(deviceIdParams: AddDeviceIDParams): Flow<APIResponse<String>> {
       return  flow {
           emit(addDeviceIdAPI.addDeviceId(deviceIdParams))
         }.onEach {
             localeRepository.setEnableFingerPrint(true)
       }
    }

    suspend fun fetchAppSettings():AppSettings{
        return withContext(Dispatchers.IO){
            localeRepository.getSettings()
        }
    }

    fun removeDeviceID(){
        localeRepository.setEnableFingerPrint(false)
    }
}