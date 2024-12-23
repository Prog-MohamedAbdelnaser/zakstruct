package com.zaka.features.login.vm

import android.util.Log
import androidx.lifecycle.*
import com.zaka.data.model.LoginParams
import com.zaka.data.model.RefreshTokenParams
import com.zaka.data.repositories.LoginRepository
import com.zaka.data.repositories.SettingsRepository
import com.zaka.domain.UserProfile
import com.zaka.domain.usecases.FetchProfileUseCase
import com.zaka.features.common.ActionLiveData
import com.zaka.features.common.CommonState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository,
                     private val profile: FetchProfileUseCase,
private  val settingsRepository: SettingsRepository) :ViewModel() {

    private val _loginState = ActionLiveData<CommonState<String>>()

    private val _refreshSessionState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val refreshSessionState: StateFlow<CommonState<String>> = _refreshSessionState

    private val _enableFingerprintState = MutableLiveData<CommonState<Boolean>>()
    val enableFingerprintState: LiveData<CommonState<Boolean>> = _enableFingerprintState

    private val _otpState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val otpState: StateFlow<CommonState<String>> = _otpState

    private val _profileState = MutableStateFlow<CommonState<UserProfile>>(CommonState.UnInit)
    val profileState: StateFlow<CommonState<UserProfile>> = _profileState


    private val _generateOtpState = MutableStateFlow<CommonState<String>>(CommonState.UnInit)
    val generateOtpState: StateFlow<CommonState<String>> = _generateOtpState

    fun login(loginParams: LoginParams){
        viewModelScope.launch{
            _loginState.value = CommonState.LoadingShow
            loginRepository.login(loginParams)
//                .map { profile.execute(false).onEach {
//                    loginRepository.generateOtp("+96892026954").collect()
//                }.collect{} }
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
         _profileState.value = CommonState.LoadingShow
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

    fun refreshToken(){
        viewModelScope.launch{
            _refreshSessionState.value = CommonState.LoadingShow
            loginRepository.refreshToken()
                .catch {it-> _refreshSessionState.value = CommonState.Error(it) }
                .onCompletion { _refreshSessionState.value = CommonState.LoadingFinished }
                .collect {
                    _refreshSessionState.value = CommonState.Success("it")
                }
        }
    }

    fun fetchAppSettings(){
        viewModelScope.launch{
            println("fetchAppSettings")
            _enableFingerprintState.value = CommonState.Success(settingsRepository.fetchAppSettings().enableBiometricManager)
        }
    }


    fun generateOtp(phone:String){
        viewModelScope.launch{
            _generateOtpState.value = CommonState.LoadingShow
            loginRepository.generateOtp(phone)
                .catch {it-> _generateOtpState.value = CommonState.Error(it) }
                .onCompletion { _generateOtpState.value = CommonState.LoadingFinished }
                .collect {
                    _generateOtpState.value = CommonState.Success("it")
                }
        }
    }

    fun  observeLogin(lifecycleOwner: LifecycleOwner, observer: Observer<CommonState<String>>){
      if (!_loginState.hasObservers())
        _loginState.observe(lifecycleOwner,observer = observer)
    }

    fun getAppLanguage():String{
        return  settingsRepository.getLanguage()
    }

}