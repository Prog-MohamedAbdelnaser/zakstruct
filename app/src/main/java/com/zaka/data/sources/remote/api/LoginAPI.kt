package com.zaka.data.sources.remote.api

import com.zaka.data.model.LoginParams
import com.zaka.domain.APIResponse
import com.zaka.domain.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginAPI {
    @POST("accounts/adlogin")
    suspend  fun login(@Body loginParams: LoginParams): APIResponse<User>

}