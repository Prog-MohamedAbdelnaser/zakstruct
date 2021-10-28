package com.zaka.data.sources.remote.api

import com.zaka.data.model.LoginParams
import com.zaka.domain.APIResponse
import com.zaka.domain.User
import com.zaka.domain.UserProfile
import retrofit2.http.*

interface ProfileAPI {
    @GET("users/me")
    suspend  fun fetchProfile(): APIResponse<UserProfile>

}