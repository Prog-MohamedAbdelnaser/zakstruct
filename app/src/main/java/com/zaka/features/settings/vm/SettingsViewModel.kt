package com.zaka.features.settings.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.model.AddDeviceIDParams
import com.zaka.data.repositories.SettingsRepository
import com.zaka.domain.AppSettings
import com.zaka.features.common.CommonState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(private val  settingsRepository: SettingsRepository) :ViewModel() {

    private val _addDeviceIdState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)

    val addDeviceIdState: StateFlow<CommonState<String>> = _addDeviceIdState

    private val _settingsState = MutableStateFlow<CommonState<AppSettings>>(CommonState.UnInit)

    val settingsState: StateFlow<CommonState<AppSettings>> = _settingsState

    fun addDeviceId(deviceIdParams: AddDeviceIDParams){
        viewModelScope.launch{
            _addDeviceIdState.value = CommonState.LoadingShow
            settingsRepository.addDeviceId(deviceIdParams)
                .catch {it-> _addDeviceIdState.value = CommonState.Error(it) }
                .onCompletion { _addDeviceIdState.value = CommonState.LoadingFinished }
                .collect {
                    _addDeviceIdState.value = CommonState.Success("it")
                }
        }
    }

    fun fetchAppSettings(){
        viewModelScope.launch{
            _settingsState.value = CommonState.LoadingShow
            settingsRepository.fetchAppSettings()
                .catch {it-> _settingsState.value = CommonState.Error(it) }
                .onCompletion { _settingsState.value = CommonState.LoadingFinished }
                .collect {
                    _settingsState.value = CommonState.Success(it)
                }
        }
    }

}