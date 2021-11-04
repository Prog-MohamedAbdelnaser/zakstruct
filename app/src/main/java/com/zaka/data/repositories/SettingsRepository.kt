package com.zaka.data.repositories

import com.zaka.data.model.AddDeviceIDParams

import com.zaka.data.sources.remote.api.SettingsAPI
import com.zaka.domain.APIResponse
import com.zaka.domain.AppSettings
import kotlinx.coroutines.flow.*


class SettingsRepository(private val  addDeviceIdAPI: SettingsAPI , private val localeRepository: LocaleRepository) {

     suspend fun addDeviceId(deviceIdParams: AddDeviceIDParams): Flow<APIResponse<String>> {
       return  flow {
           emit(addDeviceIdAPI.addDeviceId(deviceIdParams))
         }.onEach {
             localeRepository.setEnableFingerPrint(true)
       }
    }

    suspend fun fetchAppSettings():Flow<AppSettings>{
        return  flow {
            emit(localeRepository.getSettings())
        }
    }

    fun removeDeviceID(){
        localeRepository.setEnableFingerPrint(false)
    }
}