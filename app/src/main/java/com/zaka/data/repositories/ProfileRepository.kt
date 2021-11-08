package com.zaka.data.repositories

import com.zaka.data.repositories.RepositoriesConstants.KEY_PREFRENCE_USER_PROFILE
import com.zaka.data.sources.local.AppPreference
import com.zaka.data.sources.remote.api.ProfileAPI
import com.zaka.domain.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class ProfileRepository(private val  profileAPI: ProfileAPI, private val appPreference: AppPreference) {

    suspend fun fetchRemote(): Flow<UserProfile> {
       return  flow {
          // emit(fetchProfileLocal()!!)
           emit(profileAPI.fetchProfile().payload!!)
         }.onEach {
           saveProfile(it)
       }
    }



    private fun saveProfile(userProfile: UserProfile){
        appPreference.saveObject(KEY_PREFRENCE_USER_PROFILE,userProfile)
    }

    fun isCashProfile():Boolean{
        return fetchProfileLocal()!=null
    }

     fun fetchProfileLocal():UserProfile?{
        return  appPreference.getObject(KEY_PREFRENCE_USER_PROFILE,UserProfile::class.java)
    }

}