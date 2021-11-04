package com.zaka.features.login.di


import com.zaka.data.repositories.LoginRepository
import com.zaka.data.repositories.UserRepository
import com.zaka.data.sources.remote.api.LoginAPI
import com.zaka.features.login.vm.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val loginModule = module {
    factory {  get<Retrofit>().create(LoginAPI::class.java)}
    factory { LoginRepository(get(),get(),get()) }
    factory { UserRepository(get()) }
    viewModel { LoginViewModel(get(),get(),get()) }
}