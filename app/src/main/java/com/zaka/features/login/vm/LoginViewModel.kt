package com.zaka.features.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.exceptions.APIException
import com.zaka.data.model.LoginParams
import com.zaka.data.repositories.LoginRepository
import com.zaka.domain.User
import com.zaka.features.common.CommonState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) :ViewModel() {

    private val _loginState = MutableStateFlow<CommonState<User>>(CommonState.UnInit)
    val loginState: StateFlow<CommonState<User>> = _loginState

    fun login(loginParams: LoginParams){
        viewModelScope.launch{
            _loginState.value = CommonState.LoadingShow
            loginRepository.login(loginParams)
                .catch {it-> _loginState.value = CommonState.Error(it) }
                .onCompletion { _loginState.value = CommonState.LoadingFinished }
                .collect { _loginState.value = CommonState.Success(it.payload!!) }
        }
    }
}