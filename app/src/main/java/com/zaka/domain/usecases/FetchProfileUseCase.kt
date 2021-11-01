package com.zaka.domain.usecases

import com.zaka.data.repositories.ProfileRepository
import com.zaka.domain.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FetchProfileUseCase(private val profileRepository: ProfileRepository):UseCase<Boolean , Flow<UserProfile>> {
    override suspend fun execute(fromCash: Boolean?): Flow<UserProfile>  {
        return fetchProfile(fromCash?:false)
    }

    private suspend fun fetchProfile(checkCash: Boolean):Flow<UserProfile> {
        return if (checkCash && profileRepository. isCashProfile() ){
          flow{emit(profileRepository. fetchProfileLocal()!!) }
        }else {
            return profileRepository.fetchRemote()
        }
    }
}