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

    private val _loginState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val loginState: StateFlow<CommonState<String>> = _loginState

    private val _otpState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val otpState: StateFlow<CommonState<String>> = _otpState

    fun login(loginParams: LoginParams){
        viewModelScope.launch{
            _loginState.value = CommonState.LoadingShow
            loginRepository.login(loginParams)
                .map { loginRepository.generateOtp("+96892026954").collect() }
                .catch {it-> _loginState.value = CommonState.Error(it) }
                .onCompletion { _loginState.value = CommonState.LoadingFinished }
                .collect {
                    _loginState.value = CommonState.Success("it")
                }
        }
    }

    fun confirmOtp(otp:String){
        viewModelScope.launch{
            _otpState.value = CommonState.LoadingShow
            loginRepository.confirmOtp(otp)
                .catch {it-> _otpState.value = CommonState.Error(it) }
                .onCompletion { _otpState.value = CommonState.LoadingFinished }
                .collect {
                    _otpState.value = CommonState.Success("it")
                }
        }
    }
}