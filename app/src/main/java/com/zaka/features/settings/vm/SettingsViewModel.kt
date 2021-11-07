package com.zaka.features.settings.vm


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.model.AddDeviceIDParams
import com.zaka.data.repositories.DeviceInfoRepository
import com.zaka.data.repositories.SettingsRepository
import com.zaka.domain.AppSettings
import com.zaka.features.common.CommonState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val  settingsRepository: SettingsRepository,
                        private  val  deviceInfoRepository: DeviceInfoRepository) :ViewModel() {

    private val _addDeviceIdState = MutableLiveData<CommonState<String>>()
    val addDeviceIdState: LiveData<CommonState<String>> = _addDeviceIdState

    private val _settingsState = MutableLiveData<CommonState<AppSettings>>()
    val settingsState: LiveData<CommonState<AppSettings>> = _settingsState

    fun addDeviceId(){
        viewModelScope.launch{
            _addDeviceIdState.value = CommonState.LoadingShow
            settingsRepository
                .addDeviceId(AddDeviceIDParams(deviceId = deviceInfoRepository.getDeviceID()))
                .catch {it-> _addDeviceIdState.value = CommonState.Error(it) }
                .onCompletion { _addDeviceIdState.value = CommonState.LoadingFinished }
                .collect { _addDeviceIdState.value = CommonState.Success("it") }
        }
    }

    fun fetchAppSettings(){
        viewModelScope.launch{
            _settingsState.value = CommonState.Success(settingsRepository.fetchAppSettings())
        }
    }

    fun removeDeviceID() {
        settingsRepository.removeDeviceID()
    }

}