package com.zaka.features.login.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaka.data.exceptions.APIException
import com.zaka.data.model.LoginParams
import com.zaka.data.repositories.LoginRepository
import com.zaka.domain.User
import com.zaka.domain.UserProfile
import com.zaka.domain.usecases.FetchProfileUseCase
import com.zaka.features.common.CommonState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository,private val profile: FetchProfileUseCase) :ViewModel() {

    private val _loginState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val loginState: StateFlow<CommonState<String>> = _loginState

    private val _otpState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val otpState: StateFlow<CommonState<String>> = _otpState


    private val _profileState = MutableStateFlow<CommonState<UserProfile>>(CommonState.UnInit)
    val profileState: StateFlow<CommonState<UserProfile>> = _profileState

    fun login(loginParams: LoginParams){
        viewModelScope.launch{
            _loginState.value = CommonState.LoadingShow
            loginRepository.login(loginParams)
                .map { profile.execute(false).onEach {
                    loginRepository.generateOtp("+96892026954").collect()
                }.collect{} }
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


    fun checkLoggedIn():Boolean{
        return loginRepository.checkLogin()
    }

     fun getUserData(){
         viewModelScope.launch{
             profile.execute(false).catch {
                 _profileState.value= CommonState.Error(it)
             }.onCompletion {
                 _profileState.value = CommonState.LoadingFinished
             }.collect {
                 _profileState.value= CommonState.Success(it)
                 Log.e("vvv",it.displayName.toString())
             }
         }

    }
}