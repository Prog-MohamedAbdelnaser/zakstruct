package com.zaka.data.sources.remote.api

import com.zaka.domain.APIResponse
import com.zaka.domain.UserProfile
import retrofit2.http.*

interface ProfileAPI {
    @GET("users/me")
    suspend  fun fetchProfile(): APIResponse<UserProfile>

}