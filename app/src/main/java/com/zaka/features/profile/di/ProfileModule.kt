package com.zaka.features.profile.di

import com.zaka.data.repositories.LoginRepository
import com.zaka.data.repositories.ProfileRepository
import com.zaka.data.repositories.UserRepository
import com.zaka.data.sources.remote.api.LoginAPI
import com.zaka.data.sources.remote.api.ProfileAPI
import com.zaka.domain.usecases.FetchProfileUseCase
import com.zaka.features.login.vm.LoginViewModel
import com.zaka.features.profile.vm.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val  profileModule = module {

    factory {  get<Retrofit>().create(ProfileAPI::class.java)}
    factory { ProfileRepository(get(),get()) }
    factory { FetchProfileUseCase(get()) }
    viewModel { ProfileViewModel(get()) }
}