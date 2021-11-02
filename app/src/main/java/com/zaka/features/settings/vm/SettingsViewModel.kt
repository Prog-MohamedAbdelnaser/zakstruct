package com.zaka.features.settings.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.model.AddDeviceIDParams
import com.zaka.data.repositories.SettingsRepository
import com.zaka.features.common.CommonState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val  SettingsRepository: SettingsRepository) :ViewModel() {

    private val _addDeviceIdState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val addDeviceIdState: StateFlow<CommonState<String>> = _addDeviceIdState


    fun addDeviceId(deviceIdParams: AddDeviceIDParams){
        viewModelScope.launch{
            _addDeviceIdState.value = CommonState.LoadingShow
            SettingsRepository.addDeviceId(deviceIdParams)
                .catch {it-> _addDeviceIdState.value = CommonState.Error(it) }
                .onCompletion { _addDeviceIdState.value = CommonState.LoadingFinished }
                .collect {
                    _addDeviceIdState.value = CommonState.Success("it")
                }
        }
    }

}