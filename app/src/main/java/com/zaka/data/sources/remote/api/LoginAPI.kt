package com.zaka.data.sources.remote.api

import com.zaka.data.model.LoginParams
import com.zaka.domain.APIResponse
import com.zaka.domain.User
import retrofit2.http.*

interface LoginAPI {
    @POST("accounts/adlogin")
    suspend  fun login(@Body loginParams: LoginParams): APIResponse<User>

   @GET("accounts/confirmotp/{otp}")
    suspend  fun confirmOtp(@Path("otp") otp:String): APIResponse<String>

   @POST("accounts/generateotp")
    suspend  fun sendOtp(@Body phone:String): APIResponse<String>

}