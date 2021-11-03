package com.zaka.data.sources.remote.api

import com.zaka.data.model.LoginParams
import com.zaka.data.model.RefreshTokenParams
import com.zaka.domain.APIResponse
import com.zaka.domain.UserToken
import com.zaka.domain.OTPToken
import retrofit2.http.*

interface LoginAPI {
    @POST("accounts/adlogin")
    suspend  fun login(@Body loginParams: LoginParams): APIResponse<OTPToken>

   @GET("accounts/confirmotp/{otp}")
    suspend  fun confirmOtp(@Path("otp") otp:String): APIResponse<UserToken>

   @POST("accounts/generateotp")
    suspend  fun sendOtp(@Body phone:String): APIResponse<String>

    @POST("accounts/refreshToken")
    suspend  fun refreshToken(@Body refreshTokenParams: RefreshTokenParams): APIResponse<String>

}