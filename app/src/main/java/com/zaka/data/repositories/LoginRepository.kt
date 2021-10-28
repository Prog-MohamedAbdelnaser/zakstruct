package com.zaka.data.repositories

import com.zaka.data.model.LoginParams
import com.zaka.data.sources.remote.api.LoginAPI
import com.zaka.domain.APIResponse
import com.zaka.domain.User
import kotlinx.coroutines.flow.*
import java.lang.Exception

class LoginRepository(private val  loginAPI: LoginAPI , private val userRepository: UserRepository) {

     suspend fun login(loginParams: LoginParams): Flow<APIResponse<User>> {
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
    suspend fun confirmOtp(otp:String): Flow<APIResponse<String>> {
       return  flow {
           emit(loginAPI.confirmOtp(otp))
         }
    }


}