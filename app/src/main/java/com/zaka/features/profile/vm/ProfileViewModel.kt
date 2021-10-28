package com.zaka.features.profile.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.model.LoginParams
import com.zaka.domain.User
import com.zaka.domain.UserProfile
import com.zaka.domain.usecases.FetchProfileUseCase
import com.zaka.features.common.CommonState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(private val profile: FetchProfileUseCase) :ViewModel() {

    private val _profileState = MutableStateFlow<CommonState<UserProfile>>(CommonState.UnInit)
    val profileState: StateFlow<CommonState<UserProfile>> = _profileState

    fun fetchProfile(cash :Boolean){
        viewModelScope.launch{
            _profileState.value = CommonState.LoadingShow
            profile.execute(cash)
                .catch { _profileState.value = CommonState.Error(it) }
                .onCompletion { _profileState.value = CommonState.LoadingFinished }
                .collect { _profileState.value = CommonState.Success(it) }
        }
    }
}