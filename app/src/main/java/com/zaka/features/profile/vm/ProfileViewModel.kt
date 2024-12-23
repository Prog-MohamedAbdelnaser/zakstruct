package com.zaka.features.profile.vm

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.domain.UserProfile
import com.zaka.domain.usecases.FetchProfileUseCase
import com.zaka.features.common.CommonState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

class ProfileViewModel(private val profile: FetchProfileUseCase) :ViewModel() {

    private val _profileState = MutableLiveData<CommonState<UserProfile>>()
    val profileState: LiveData<CommonState<UserProfile>> = _profileState

    fun fetchProfile(cash :Boolean){
        viewModelScope.launch(context = Dispatchers.Main){
            _profileState.value = CommonState.LoadingShow
            profile.execute(cash)
                .catch { _profileState.value = CommonState.Error(it) }
                .onCompletion { _profileState.value = CommonState.LoadingFinished }
                .collect {
                    println("fetchProfile collect ${it}")
                    _profileState.value = CommonState.Success(it)
                }
        }
    }
}