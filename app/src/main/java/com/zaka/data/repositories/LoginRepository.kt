package com.zaka.data.repositories

import com.zaka.data.model.LoginParams
import com.zaka.data.model.RefreshTokenParams
import com.zaka.data.sources.local.AppPreference
import com.zaka.data.sources.remote.api.LoginAPI
import com.zaka.domain.APIResponse
import com.zaka.domain.UserToken
import com.zaka.domain.OTPToken
import kotlinx.coroutines.flow.*

class LoginRepository(private val  loginAPI: LoginAPI , private val userRepository: UserRepository,private val appPreference: AppPreference) {

     suspend fun login(loginParams: LoginParams): Flow<APIResponse<OTPToken>> {
       return  flow {
           emit(loginAPI.login(loginParams))
           println("AFETR EMIT LOGIN ")
         }.onEach {
             userRepository.saveUserData(it.payload!!)
       }
    }

    suspend fun generateOtp(phone:String): Flow<APIResponse<String>> {
       return  flow {
           emit(loginAPI.sendOtp(phone))
         }
    }
    suspend fun confirmOtp(otp:String): Flow<APIResponse<UserToken>> {
       return  flow {
           emit(loginAPI.confirmOtp(otp))
         }.onEach {
           userRepository.saveTokenAfterOTPVerification(it.payload!!)
       }
    }

     fun checkLogin(): Boolean {
        return  userRepository.isLoged()
    }

    suspend fun refreshToken(): Flow<APIResponse<UserToken>> {
        return  flow {
            emit(loginAPI.refreshToken(RefreshTokenParams(token = userRepository.getLogedInUser()?.token,
            refreshToken = userRepository.getLogedInUser()?.refreshToken)))
        }.onEach {
            userRepository.saveTokenAfterOTPVerification(it.payload!!)
        }
    }

}